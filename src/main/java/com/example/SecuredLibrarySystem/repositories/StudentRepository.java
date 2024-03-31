package com.example.SecuredLibrarySystem.repositories;


import com.example.SecuredLibrarySystem.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {

}
