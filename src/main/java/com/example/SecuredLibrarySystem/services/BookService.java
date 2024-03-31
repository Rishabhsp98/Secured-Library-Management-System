package com.example.SecuredLibrarySystem.services;

import com.example.SecuredLibrarySystem.models.Author;
import com.example.SecuredLibrarySystem.models.Book;
import com.example.SecuredLibrarySystem.models.Genre;
import com.example.SecuredLibrarySystem.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {


    @Autowired
    AuthorService authorService;

    @Autowired
    BookRepository bookRepository;

    public void createOrUpdate(Book book) {

        Author bookauthor = book.getMy_author(); // here this author doesn't have primary key , so in next step
        Author savedauthor = authorService.getOrCreate(bookauthor); // here we are retrieving the complete author details having primary key

        // map the author to the book
        book.setMy_author(savedauthor);

        // now after mapping the foreign key above ,safely we save the book
        bookRepository.save(book);
    }

    public List<Book> find(String searchKey,String searchValue) throws Exception {

        switch (searchKey)
        {
            case "id": {
                Optional<Book> book = bookRepository.findById(Integer.parseInt(searchValue));
                if (book.isPresent()) {
                    return Arrays.asList(book.get());
                } else {
                    return new ArrayList<>();
                }

            }
            case "genre":
                return bookRepository.findByGenre(Genre.valueOf(searchValue));
            case "author_name":
                return bookRepository.findByMy_author_name(searchValue);
            case "bookName":
                return bookRepository.findByName(searchValue);
            default:
                throw new Exception("Search Key not Valid" + searchKey);
        }

    }
}
