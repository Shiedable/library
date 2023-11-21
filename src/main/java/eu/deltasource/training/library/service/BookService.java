package eu.deltasource.training.library.service;

import eu.deltasource.training.library.exceptions.InvalidDateException;
import eu.deltasource.training.library.exceptions.NegativeNumberException;
import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import static eu.deltasource.training.library.validator.Validator.*;
import static org.springframework.util.StringUtils.hasLength;


@Service
public class BookService {

    private BooksRepository books;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        books = booksRepository;
    }

    public void addBook(String title, String publicationDate, String isbn, double price) {
        validateString(title);
        validateDate(publicationDate);
        validateString(isbn);
        validateNumber(price);
        Book book = new Book(title, LocalDate.parse(publicationDate), isbn, price);
        books.save(book);
    }

    public void deleteBookById(long id) {
        validateId(id, books);
        books.deleteById(id);
    }

    public void updateBookById(long id, String title, String publicationDate, String isbn, double price) {
        validateId(id, books);
        Book book = books.findById(id).get();
        Book updatedBook = setUpdatedAuthor(id, book, title, publicationDate, isbn, price);
        books.save(updatedBook);
    }

    public Optional<Book> getBookById(long id) {
        validateId(id, books);
        return books.findById(id);
    }

    public List<Book> getAllBooks() {
        return (List<Book>) books.findAll();
    }

    private Book setUpdatedAuthor(long id, Book book, String title, String publicationDateString, String isbn, double price) {
        LocalDate publicationDate;
        if (!hasLength(title)) {
            title = book.getTitle();
        }
        if (!hasLength(isbn)) {
            isbn = book.getIsbn();
        }
        if (!hasLength(publicationDateString)) {
            publicationDate = book.getPublicationDate();
        } else {
            try {
                publicationDate = LocalDate.parse(publicationDateString);
            } catch (DateTimeParseException exception) {
                throw new InvalidDateException("Date format is invalid");
            }
        }
        if (price < 0) {
            throw new NegativeNumberException("price should be positive");
        } else if (price == 0) {
            price = book.getPrice();
        }
        return new Book(id, title, publicationDate, isbn, price);
    }
}
