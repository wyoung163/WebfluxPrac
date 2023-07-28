package com.example.webflux.repository;

import com.example.webflux.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRespository extends JpaRepository<Book, Long> {
    List<Book> findByTitleLike(String text);
}
