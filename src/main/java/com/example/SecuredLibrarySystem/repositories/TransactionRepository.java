package com.example.SecuredLibrarySystem.repositories;


import com.example.SecuredLibrarySystem.models.Book;
import com.example.SecuredLibrarySystem.models.Student;
import com.example.SecuredLibrarySystem.models.TransactionType;
import com.example.SecuredLibrarySystem.models.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transactions,Integer> {

//    @Query("select * from Transactions where student_id = ?1 and book_id = ?2 and transactionType = ?3 order by id desc limit 1")
    Transactions findTopByStudentAndBookAndTransactionTypeOrderByIdDesc(Student student, Book book, TransactionType transactionType);


    Transactions findByTxnId(String txnId);
}
