package com.example.SecuredLibrarySystem.repositories;


import com.example.SecuredLibrarySystem.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {

}
