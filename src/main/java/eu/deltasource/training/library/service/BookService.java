package eu.deltasource.training.library.service;

import eu.deltasource.training.library.exceptions.*;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BooksRepository books;
    @Autowired
    private AuthorService authorService;


    public void addBook(int authorId, String title, String publicationDate, String isbn, double price)
            throws NegativeIdException, EmptyStringException, NegativeNumberException,
            NullDateException, IdNotFoundException {
        validateAuthorID(authorId);
        Book book = new Book(authorId, title, LocalDate.parse(publicationDate), isbn, price);
        books.addBook(book);
    }

    public void deleteBookById(int id) throws IdNotFoundException {
        validateBookID(id);
        books.deleteBookById(id);
    }

    public void updateBookById(int id, int authorId, String title, String publicationDateString, String isbn, double price)
            throws NegativeIdException, EmptyStringException, NegativeNumberException,
            NullDateException, IdNotFoundException {
        validateBookID(id);
        validateAuthorID(authorId);
        LocalDate publicationDate = validateAndParseDate(publicationDateString);
        books.updateBookById(id, authorId, title, publicationDate, isbn, price);
    }

    public Book getBookById(int id) throws IdNotFoundException {
        validateBookID(id);
        return books.getBookById(id);
    }

    public List<Book> getAllBooks() {
        return books.getAllBooks();
    }

    private void validateAuthorID(int id) throws IdNotFoundException {
        if (id >= authorService.getAllAuthors().size()) {
            throw new IdNotFoundException("Author with such ID does not exist");
        }
    }

    private void validateBookID(int id) throws IdNotFoundException {
        if (id >= books.getAllBooks().size()) {
            throw new IdNotFoundException("Book with such ID does not exist");
        }
    }

    private LocalDate validateAndParseDate(String date) {
        if (date == null) {
            return null;
        }
        return LocalDate.parse(date);
    }
}
