package eu.deltasource.training.library.repository;

import eu.deltasource.training.library.exceptions.*;
import eu.deltasource.training.library.model.Book;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class BooksRepository {

    private List<Book> books;

    public BooksRepository() {
        books = new ArrayList<>();
    }

    public void addBook(Book newBook) {
        books.add(newBook);
    }

    public Book getBookById(int id) {
        return books.get(id);
    }

    public void deleteBookById(int id) {
        books.remove(id);
    }

    public void updateBookById(int id, int authorId, String title, LocalDate publicationDate, String isbn, double price)
            throws NegativeIdException, NegativeNumberException, EmptyStringException,
            NullDateException {
        Book book = books.get(id);
        book.setAuthorId(authorId);
        book.setIsbn(isbn);
        book.setPrice(price);
        book.setTitle(title);
        book.setPublicationDate(publicationDate);
    }

    public List<Book> getAllBooks() {
        return Collections.unmodifiableList(books);
    }
}
