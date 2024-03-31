package com.example.SecuredLibrarySystem.controllers;

import com.example.SecuredLibrarySystem.dtos.CreateStudentRequest;
import com.example.SecuredLibrarySystem.models.Student;
import com.example.SecuredLibrarySystem.models.Transactions;
import com.example.SecuredLibrarySystem.repositories.StudentRepository;
import com.example.SecuredLibrarySystem.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@RestController
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @PostMapping("/student")
    public void create(@RequestBody CreateStudentRequest createStudentRequest) {
        logger.info("REST request to create a student, {},{},{}", createStudentRequest.getName(),createStudentRequest.getEmail(),createStudentRequest.getAge());
        studentRepository.save(createStudentRequest.to());
    }

    @GetMapping("/student")
    public Student find(@RequestParam("id") int studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

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

