package eu.deltasource.training.library.model;

import eu.deltasource.training.library.exceptions.*;

import static org.springframework.util.StringUtils.hasLength;

import java.time.LocalDate;

public class Book {

    private int authorId;
    private String title;
    private LocalDate publicationDate;
    private String isbn;
    private double price;

    public Book() {
    }

    public Book(int authorId, String title, LocalDate publicationDate, String isbn, double price)
            throws NullDateException, NegativeIdException, EmptyStringException,
            NegativeNumberException {
        setAuthorId(authorId);
        setTitle(title);
        setPublicationDate(publicationDate);
        setIsbn(isbn);
        setPrice(price);
    }

    public void setAuthorId(int authorId) throws NegativeIdException {
        if (authorId >= 0) {
            this.authorId = authorId;
        } else {
           throw new NegativeIdException("Author ID cannot be negative");
        }
    }

    public void setTitle(String title) throws EmptyStringException {
        if (hasLength(title)) {
            this.title = title;
        } else {
            throw new EmptyStringException("Book title cannot be empty");
        }
    }

    public void setPublicationDate(LocalDate publicationDate) throws NullDateException {
        if (publicationDate != null) {
            this.publicationDate = publicationDate;
        } else {
            throw new NullDateException("Publication date is null");
        }
    }

    public void setIsbn(String isbn) throws EmptyStringException {
        if (hasLength(isbn)) {
            this.isbn = isbn;
        } else {
            throw new EmptyStringException("Book ISBN cannot be empty");
        }
    }

    public void setPrice(double price) throws NegativeNumberException {
        if (price > 0.0) {
            this.price = price;
        } else {
            throw new NegativeNumberException("Book price cannot be negative");
        }
    }

    @Override
    public String toString() {
        return authorId + " - " + title + ", "  + publicationDate + " | " + isbn + " | " + price + "$";
    }
}
