package com.myprojects.biblioManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private int publishedYear;
    private double price;

    public Book(String title, String author, String isbn, int publishedYear, double price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.price = price;
    }

    public Book() {
        // Constructeur par défaut requis pour Jackson
    }


    @Override
    public String toString() {
        return "Book [id=" + id + ", title=" + title + ", author=" + author + ", isbn=" + isbn + ", publishedYear=" + publishedYear + ", price=" + price + "]";
    }
}
