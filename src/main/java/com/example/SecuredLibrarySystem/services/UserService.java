package com.example.SecuredLibrarySystem.services;

import com.example.SecuredLibrarySystem.Utils.Utils;
import com.example.SecuredLibrarySystem.models.SecuredUser;
import com.example.SecuredLibrarySystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByusername(username);
    }

    public SecuredUser saveUser(SecuredUser securedUser,String userType){
        // 1. encode the password
        // 2. assign authorities
        String encryptedPassword = passwordEncoder.encode(securedUser.getPassword());
        String authorities = Utils.getAuthoritiesForUser().get(userType);

        securedUser.setPassword(encryptedPassword);
        securedUser.setAuthorities(authorities);
        return this.userRepository.save(securedUser);
    }
}
