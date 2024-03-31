package com.example.SecuredLibrarySystem.dtos;

import com.example.SecuredLibrarySystem.models.Author;
import com.example.SecuredLibrarySystem.models.Book;
import com.example.SecuredLibrarySystem.models.Genre;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequest {

    @NotBlank
    private String name;

    @NotNull
    private Genre genre;

    @NotBlank
    private String authorName;

    @NotBlank
    private String authorEmail;


    public Book to(){

        Author authorObj = Author.builder()
                .name(this.authorName)
                .email(this.authorEmail)
                .build();
        return Book.builder()
                        .name(this.name)
                        .genre(this.genre)
                        .my_author(authorObj).
                        build();
    }
}
