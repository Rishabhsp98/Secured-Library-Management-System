package com.example.SecuredLibrarySystem.controllers;

import com.example.SecuredLibrarySystem.dtos.CreateBookRequest;
import com.example.SecuredLibrarySystem.dtos.SearchBookRequest;
import com.example.SecuredLibrarySystem.models.Book;
import com.example.SecuredLibrarySystem.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
public class BookController {
    private static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService bookService;

    @PostMapping("/book")
    public void createBook(@RequestBody CreateBookRequest createBookRequest) {
        logger.info("REST request to Add BookRequest - {}",createBookRequest);
        bookService.createOrUpdate(createBookRequest.to());
    }

    // method to get books on basis of different types of entity fields instead of creating n number of GET apis for each
    @GetMapping("/book")
    public List<Book> getBooks(@RequestBody @Valid SearchBookRequest searchBookRequest) throws Exception {
        // two methods
        return bookService.find(searchBookRequest.getSearchKey(),searchBookRequest.getSearchValue());
    }
}
