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
    public List<Book> getBooks(@RequestParam("text") String text) {
        List<Book> books = bookService.searchBooks(text);

        for(Book book: books) {
            System.out.println(book.getTitle());
        }

        return books;
    }


    @RequestMapping("/look")
    public String lookAround() throws InterruptedException {
        Thread.sleep(100);  // 입장 후 0.1초후에 인사한다.
        return "책 둘러보는 중";
    }
}
