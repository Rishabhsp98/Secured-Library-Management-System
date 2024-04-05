package com.example.SecuredLibrarySystem.services;

import com.example.SecuredLibrarySystem.Utils.Constants;
import com.example.SecuredLibrarySystem.dtos.CreateAdminRequest;
import com.example.SecuredLibrarySystem.models.Admin;
import com.example.SecuredLibrarySystem.models.SecuredUser;
import com.example.SecuredLibrarySystem.repositories.AdminRepository;
import com.example.SecuredLibrarySystem.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    UserService userService;
    @Autowired
    AdminRepository adminRepository;

    public void create(Admin admin) {

        //1.get secured user out from requestBuilder and first save it
        SecuredUser securedUser = admin.getSecuredUser();
        securedUser = userService.saveUser(securedUser, Constants.ADMIN_USER);

        // 2. map that secured user to admin
        admin.setSecuredUser(securedUser);

        //3. save the admin
        adminRepository.save(admin);
    }

    public Admin find(Integer adminId){

        if(adminId != null) {
            return  adminRepository.findById(adminId).orElse(null);
        }
        return null;
    }

}
