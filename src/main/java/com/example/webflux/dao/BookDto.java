package com.example.webflux.dao;

import com.example.webflux.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@RequiredArgsConstructor
public class BookDto {
    public Book insertBook(String title, String author, int price, String publisher, String link, String image, Date date) {
        Book bookEntity = Book.builder()
                .title(title)
                .author(author)
                .price(price)
                .publisher(publisher)
                .link(link)
                .image(image)
                .putDate(date)
                .build();
        return bookEntity;
    }
}
