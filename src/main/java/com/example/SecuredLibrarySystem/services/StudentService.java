package com.example.SecuredLibrarySystem.services;

import com.example.SecuredLibrarySystem.Utils.Constants;
import com.example.SecuredLibrarySystem.dtos.CreateStudentRequest;
import com.example.SecuredLibrarySystem.models.SecuredUser;
import com.example.SecuredLibrarySystem.models.Student;
import com.example.SecuredLibrarySystem.repositories.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {


    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserService userService;

    public void create(Student student) {
        logger.info("Student object, {}",student);
        SecuredUser securedUser = student.getSecuredUser();

        //first save the secured user in securedUser db
        securedUser = userService.saveUser(securedUser, Constants.STUDENT_USER);

        // In student table , student having foreign key set to user (which comes from frontend, above one(line 27))
        student.setSecuredUser(securedUser);

        studentRepository.save(student);
    }

    public Student find(int studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    public void update(Student student, int id) {
        Student studentfromdb = studentRepository.findById(id).orElse(null);
        if(studentfromdb != null)
        {
            studentfromdb.setName(student.getName());
            studentfromdb.setEmail(student.getEmail());
            studentfromdb.setAge(student.getAge());

            logger.info("Data update for the student {}",student);
        }
        else
            logger.info("Entry does not exists in the Database");
    }


    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }
}
