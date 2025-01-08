package com.readnspeak.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @Column(nullable = false, unique = true, length = 50)
    private String isbn;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 100)
    private String author;

    @Column(length = 100)
    private String publisher;

    private LocalDate publicationDate;

    private String coverImage;

    private String category;

    private String language;

    private int pageCount;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
}
