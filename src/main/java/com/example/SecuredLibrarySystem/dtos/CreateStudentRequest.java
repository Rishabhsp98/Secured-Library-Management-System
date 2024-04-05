package com.example.SecuredLibrarySystem.dtos;


import com.example.SecuredLibrarySystem.models.SecuredUser;
import com.example.SecuredLibrarySystem.models.Student;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStudentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private Integer age;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public Student to(){

        SecuredUser securedUser = SecuredUser.builder().username(this.username)
                .password(this.password).build();
        return Student.builder()
                .name(this.name)
                .email(this.email)
                .age(this.age)
                .securedUser(securedUser)
                .build();
    }
}
