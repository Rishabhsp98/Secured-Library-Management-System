package com.example.SecuredLibrarySystem.controllers;

import com.example.SecuredLibrarySystem.Utils.Constants;
import com.example.SecuredLibrarySystem.dtos.CreateStudentRequest;
import com.example.SecuredLibrarySystem.models.SecuredUser;
import com.example.SecuredLibrarySystem.models.Student;
import com.example.SecuredLibrarySystem.models.Transactions;
import com.example.SecuredLibrarySystem.repositories.StudentRepository;
import com.example.SecuredLibrarySystem.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;

@RestController
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;


    //Unsecured - Acting as Sign-Up API
    @PostMapping("/student/create")
    public void create(@RequestBody CreateStudentRequest createStudentRequest) {
        logger.info("REST request to create a student, {},{},{}", createStudentRequest.getName(),createStudentRequest.getEmail(),createStudentRequest.getAge());
        studentService.create(createStudentRequest.to());
    }

    //only for admins( to see anyone's data)
    @GetMapping("/student-by-id/{id}")
    public Student findById(@PathVariable("id") int studentId) throws Exception {
       logger.info("Student Id- {}", studentId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();

       //check the authorities of the user fetched from securityContext(based on cookie (created using username and password))
        for(GrantedAuthority grantedAuthority : securedUser.getAuthorities()){
            String[] authorities =grantedAuthority.getAuthority().split(Constants.DELIMITER);
            boolean isAPICalledByAdmin = Arrays.stream(authorities).anyMatch(x-> Constants.STUDENT_INFO_AUTHORITY.equals(x));
            logger.info("Is API Called by Admin(True/False) - {}", isAPICalledByAdmin);
            if(isAPICalledByAdmin)
                return studentService.find(studentId);
        }
        throw new Exception("User is not ADMIN!");
    }

    //only for students ( to see only there particular data)
    @GetMapping("/student")
    public Student findStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId =securedUser.getStudent().getId();

        return studentService.find(studentId);
    }


    //get list of transactions done by a student(pending api)
//    @GetMapping("/student/transactions")
//    public Transactions getTransactions(@RequestBody GetTransactionRequest getTransactionRequest){
//
//    }

    @PutMapping("/student/{id}")
    public void updateStudent(@RequestBody CreateStudentRequest studentRequest,@PathVariable Integer id)
    {
        studentService.update(studentRequest.to(),id);
    }

    @DeleteMapping("/student/{id}")
    public void deleteStudent(@PathVariable Integer id)
    {
        studentService.delete(id);
    }


}

