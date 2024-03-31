package com.example.SecuredLibrarySystem.dtos;

import com.example.SecuredLibrarySystem.models.Admin;
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

    public Admin to(){
        return Admin.builder().
                name(this.name).
                email(this.email)
                .build();
    }
}
