package com.example.webflux.controller;

import com.example.webflux.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    final private BookService bookService;
    @PostMapping
    public void postBooks(@RequestParam("text") String text) throws JsonProcessingException, ParseException {
        bookService.postBooks(text);
    }
}
