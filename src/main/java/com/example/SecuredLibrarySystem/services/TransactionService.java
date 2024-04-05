package com.example.SecuredLibrarySystem.services;

import com.example.SecuredLibrarySystem.dtos.InitiateTransactionRequest;
import com.example.SecuredLibrarySystem.dtos.MakePaymentRequest;
import com.example.SecuredLibrarySystem.models.*;
import com.example.SecuredLibrarySystem.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {


    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    BookService bookService;

    @Autowired
    AdminService adminService;


    @Value("${student.allowed.max-books}")
    Integer maxBooksAllowed;


    @Value("${student.allowed.duration}")
    Integer duration;

    public String InitiateTxn(InitiateTransactionRequest initiateTransactionRequest,Integer adminId) throws Exception {
        /**
         * Issuance
         * 1. If the book is available or not and student is valid or not
         * 2. entry in the Txn
         * 3. we need to check if student has reached the maximum limit of issuance
         * 4. book to be assigned to a student ==> update in book table
         *
         */

        /**
         * Return
         * 1. If the book is valid or not and student is valid or not
         * 2. entry in the Txn table
         * 3. due date check and fine calculation
         * 4. if there is no fine, then de-allocate the book from student's name ==> book table
         */
        // based on type of transaction we route to either issue or return
        return initiateTransactionRequest.getTransactionType() == TransactionType.ISSUE ?
                issuance(initiateTransactionRequest,adminId) : returnBook(initiateTransactionRequest,adminId);

    }

    private String issuance(InitiateTransactionRequest initiateTransactionRequest,Integer adminId) throws Exception {
        Student student = studentService.find(initiateTransactionRequest.getStudentId());
        Admin admin = adminService.find(adminId);
        List<Book> bookList = bookService.find("id", String.valueOf(initiateTransactionRequest.getBookId()));

        Book book = bookList != null && bookList.size() > 0 ? bookList.get(0) : null;

        if (student == null
                || admin == null
                || book == null
                || book.getStudent() != null
                || student.getBookList().size() >= maxBooksAllowed) {
            throw new Exception("Invalid request");
        }

        Transactions transaction = null;

        try {
            transaction = Transactions.builder()
                    .txnId(UUID.randomUUID().toString())
                    .student(student)
                    .book(book)
                    .admin(admin)
                    .transactionType(initiateTransactionRequest.getTransactionType())
                    .transactionStatus(TransactionStatus.PENDING)
                    .build();

            transactionRepository.save(transaction);

            book.setStudent(student);

            bookService.createOrUpdate(book);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        }catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
        }finally {
            transactionRepository.save(transaction);
        }

        return transaction.getTxnId();
    }

    private String returnBook(InitiateTransactionRequest initiateTransactionRequest,Integer adminId) throws Exception {

        /**
         * Return
         * 1. If the book is valid or not and student is valid or not
         * 2. entry in the Txn table
         * 3. due date check and fine calculation
         * 4. if there is no fine, then de-allocate the book from student's name ==> book table
         */

        Student student = studentService.find(initiateTransactionRequest.getStudentId());
        Admin admin = adminService.find(adminId);
        List<Book> bookList = bookService.find("id", String.valueOf(initiateTransactionRequest.getBookId()));

        Book book = bookList != null && bookList.size() > 0 ? bookList.get(0) : null;

        if (student == null
                || admin == null // admin is null
                || book == null
                || book.getStudent() == null  // if the book is assigned to someone or not
                || !book.getStudent().getId().equals( student.getId())) { // if the book is assigned to the same student
            // which is requesting to return or not
            throw new Exception("Invalid request");
        }

        // Getting the corresponding issuance txn
        Transactions issuanceTxn = transactionRepository.findTopByStudentAndBookAndTransactionTypeOrderByIdDesc(
                student, book, TransactionType.ISSUE);
        if(issuanceTxn == null){
            throw new Exception("Invalid request");
        }

        Transactions transaction = null;
        try {
            Integer fine = calculateFine(issuanceTxn.getCreatedOn());
            transaction = Transactions.builder()
                    .txnId(UUID.randomUUID().toString())
                    .student(student)
                    .book(book)
                    .admin(admin)
                    .transactionType(initiateTransactionRequest.getTransactionType())
                    .transactionStatus(TransactionStatus.PENDING)
                    .fine(fine)
                    .build();

            transactionRepository.save(transaction);

            if (fine == 0) {
                book.setStudent(null);
                bookService.createOrUpdate(book);
                transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            }
        }catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
        }finally {
            transactionRepository.save(transaction);
        }

        return transaction.getTxnId();
    }

    // Here we calculate on the basis of the last transaction issued to this student
    private Integer calculateFine(Date issuanceTime){
        long issueTimeInMillis = issuanceTime.getTime(); // epoch time in millis , last issuance Date
        long currentTime = System.currentTimeMillis();

        // got the difference in MilliSeconds
        long diffInMillis =  currentTime-issueTimeInMillis;

        // convert the time into days
        long daysPassed = TimeUnit.DAYS.convert(diffInMillis,TimeUnit.MILLISECONDS);

        if(daysPassed > duration){
            long overDays = daysPassed-duration;
            if(overDays < 5)
            {
                return (int) overDays * 10;
            }
            else if(overDays < 21){
                return (int)overDays * 20;
            }else if(overDays > 21){
                return (int)overDays * 50;
            }
        }

        // if daysPassed are fine , not crossed fine limit
        return 0;
    }

    public void PayFine(MakePaymentRequest makePaymentRequest,Integer studentId) throws Exception {
        Transactions returntransactions = transactionRepository.findByTxnId(String.valueOf(makePaymentRequest.getTransactionId()));

        Book book = returntransactions.getBook();   // extract the book mapped to this transaction
        if(Objects.equals(returntransactions.getFine(), makePaymentRequest.getAmount()) && book.getStudent() != null && Objects.equals(book.getStudent().getId(), studentId)){
            returntransactions.setTransactionStatus(TransactionStatus.SUCCESS);
            book.setStudent(null);
            bookService.createOrUpdate(book);
            transactionRepository.save(returntransactions);
        }
        else{
            throw new Exception("Invalid Request!");
        }
    }

}
