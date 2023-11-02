package eu.deltasource.training.library.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Book {

    private int author_id;
    private String title;
    private LocalDate publication_date;
    private String isbn;
    private double price;

    public Book() {
    }

    public Book(int author_id, String title, LocalDate publication_date, String isbn, double price) {
        this.author_id = author_id;
        this.title = title;
        this.publication_date = publication_date;
        this.isbn = isbn;
        this.price = price;
    }

    public void setAuthor_id(int author_id) {
        if (author_id > 0) {
            this.author_id = author_id;
        }
    }

    public void setTitle(String title) {
        if(title != null) {
            this.title = title;
        }
    }

    public void setPublication_date(LocalDate publication_date) {
        if(publication_date != null) {
            this.publication_date = publication_date;
        }
    }

    public void setIsbn(String isbn) {
        if(isbn != null) {
            this.isbn = isbn;
        }
    }

    public void setPrice(double price) {
        if(price > 0.0) {
            this.price = price;
        }
    }

    @Override
    public String toString() {
        return author_id + " - " + title + ", "  + publication_date + " | " + isbn + " | " + price + "$";
    }
}
