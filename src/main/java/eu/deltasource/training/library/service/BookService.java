package eu.deltasource.training.library.service;

import eu.deltasource.training.library.exceptions.EntityNotFoundException;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.springframework.util.StringUtils.hasLength;

/**
 * This is a service handling crud operations for {@link Book}
 */
@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(String title, String publicationDate, String isbn, Double price) {
        Book book = new Book(title, publicationDate, isbn, price);
        bookRepository.save(book);
    }


    public void deleteBookById(long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Entity with such ID does not exist");
        }
    }

    public void updateBookById(long id, String title, String publicationDate, String isbn, Optional<Double> price) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with such ID does not exist"));
        setIfPresent(title, book::setTitle);
        setIfPresent(publicationDate, book::setPublicationDate);
        setIfPresent(isbn, book::setIsbn);
        setIfPresent(price, book::setPrice);
        bookRepository.save(book);
    }

    public Optional<Book> getBookById(long id) {
        if (bookRepository.existsById(id)) {
            return bookRepository.findById(id);
        } else {
            throw new EntityNotFoundException("Entity with such ID does not exist");
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    private void setIfPresent(String value, Consumer<String> consumer) {
        if (hasLength(value)) {
            consumer.accept(value);
        }
    }

    private void setIfPresent(Optional<Double> value, Consumer<Double> consumer) {
        value.ifPresent(consumer);
    }
}
