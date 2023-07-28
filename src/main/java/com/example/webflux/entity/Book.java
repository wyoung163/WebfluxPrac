package com.example.webflux.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = true)
    private int price;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Date putDate;
}
