package com.example.webflux.controller;

import com.example.webflux.entity.Book;
import com.example.webflux.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    final private BookService bookService;

    @PostMapping
    public void postBooks(@RequestParam("text") String text) throws JsonProcessingException, ParseException {
        bookService.postBooks(text);
    }

    @GetMapping
    public void getBooks(@RequestParam("text") String text) {
        List<Book> books = bookService.searchBooks(text);

        for(Book book: books) {
            System.out.println(book.getTitle());
        }
    }
}
