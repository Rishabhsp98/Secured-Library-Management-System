package com.example.SecuredLibrarySystem.services;

import com.example.SecuredLibrarySystem.dtos.CreateAdminRequest;
import com.example.SecuredLibrarySystem.models.Admin;
import com.example.SecuredLibrarySystem.repositories.AdminRepository;
import com.example.SecuredLibrarySystem.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public void create(Admin admin) {
        if(admin != null) {
            adminRepository.save(admin);
        }
    }

    public Admin find(Integer adminId){

        if(adminId != null) {
            return  adminRepository.findById(adminId).orElse(null);
        }
        return null;
    }

}
