package com.example.SecuredLibrarySystem.dtos;

import com.example.SecuredLibrarySystem.models.Admin;
import com.example.SecuredLibrarySystem.models.SecuredUser;
import lombok.*;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAdminRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public Admin to(){

        SecuredUser user = SecuredUser.builder()
                .username(this.username)
                .password(this.password).
                build();
        return Admin.builder().
                name(this.name).
                email(this.email)
                .securedUser(user)
                .build();
    }
}
