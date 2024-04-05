package com.example.SecuredLibrarySystem;

import com.example.SecuredLibrarySystem.models.Admin;
import com.example.SecuredLibrarySystem.models.SecuredUser;
import com.example.SecuredLibrarySystem.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SecuredLibrarySystemApplication implements CommandLineRunner {

//	@Autowired
//	AdminService adminService;

	public static void main(String[] args) {
		SpringApplication.run(SecuredLibrarySystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		SecuredUser securedUser = SecuredUser
//				.builder()
//				.username("ram@gfg")
//				.password("ram123").build();
//		Admin admin = Admin.builder()
//				.name("ramAdmin")
//				.email("admin@gfg.org")
//				.securedUser(securedUser)
//				.build();
//
//		adminService.create(admin);
	}
}
