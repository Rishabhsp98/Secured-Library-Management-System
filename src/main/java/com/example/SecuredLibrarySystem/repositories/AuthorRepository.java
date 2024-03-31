package com.example.SecuredLibrarySystem.repositories;

import com.example.SecuredLibrarySystem.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    //    @Query("select a from Author a from a.email = :email")
    Author findByEmail(String email);
}
