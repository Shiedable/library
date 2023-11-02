package eu.deltasource.training.library.service;

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

    public void addBook(int author_id, String title, String publication_date, String isbn, double price) {
        Book book = new Book(author_id, title, LocalDate.parse(publication_date), isbn, price);
        books.addBook(book);
    }

    public void deleteBookById(int id) {
        books.deleteBookById(id);
    }

    public void updateBookById(int id, int author_id, String title, String publication_date, String isbn, double price) {
        books.updateBookById(id, author_id, title, LocalDate.parse(publication_date), isbn, price);
    }

    public Book getBookById(int id) {
        return books.getBookById(id);
    }

    public List<Book> getAllBooks() {
        return books.getAllBooks();
    }
}
