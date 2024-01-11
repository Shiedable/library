package eu.deltasource.training.library.service;

import com.jayway.jsonpath.JsonPath;
import eu.deltasource.training.library.exceptions.EntityNotFoundException;
import eu.deltasource.training.library.exceptions.InvalidBookException;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.repository.BookRepository;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.springframework.util.StringUtils.capitalize;
import static org.springframework.util.StringUtils.hasLength;

/**
 * This is a service handling crud operations for {@link Book}
 */
@Service
public class BookService {

    private BookRepository bookRepository;
    private AuthorService authorService;
    private RestTemplate restTemplate;
    @Value("${author.url}")
    private String authorApiUrl;
    @Value("${book.url}")
    private String bookApiUrl;

    public BookService(BookRepository bookRepository, AuthorService authorService, RestTemplate restTemplate) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.restTemplate = restTemplate;
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

    public ResponseEntity<String> addBookByIsbn(String isbn) {
        if (!hasLength(isbn)) {
            throw new InvalidBookException("Book isbn cannot be empty");
        }
        ResponseEntity<String> bookResponse = restTemplate
                .exchange(bookApiUrl + "isbn=" + isbn, HttpMethod.GET, null, String.class);
        if (bookResponse.getStatusCode().is4xxClientError()) {
            return bookResponse;
        }
        Book book = setBook(isbn, bookResponse.getBody());
        bookRepository.save(book);
        ResponseEntity<String> authorResponse = addBookAuthors(bookResponse.getBody());
        return new ResponseEntity<>(book + " " + authorResponse.getBody(), HttpStatus.OK);
    }

    private ResponseEntity<String> addBookAuthors(String response) {
        JSONArray authorsJsonArray = JsonPath.parse(response).read("$.authors[*]");
        for (int i = 0; i < authorsJsonArray.size(); i++) {
            String[] fullName = authorsJsonArray.get(i).toString().split(" ");
            String firstName = fullName[0];
            String lastName = fullName[1];
            String url = authorApiUrl + "firstName=" + firstName + "&lastName=" + lastName;
            ResponseEntity<String> birthdate = restTemplate.
                    exchange(url, HttpMethod.GET, null, String.class);
            authorService.addAuthor(firstName, lastName, birthdate.getBody());
        }
        return new ResponseEntity<>(authorService.getAllAuthors().toString(), HttpStatus.OK);
    }

    private void setIfPresent(String value, Consumer<String> consumer) {
        if (hasLength(value)) {
            consumer.accept(value);
        }
    }

    private void setIfPresent(Optional<Double> value, Consumer<Double> consumer) {
        value.ifPresent(consumer);
    }

    private Book setBook(String isbn, String body) {
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(JsonPath.parse(body).read("$.title"));
        book.setPublicationDate(JsonPath.parse(body).read("$.publicationDate"));
        book.setPrice(JsonPath.parse(body).read("$.price"));
        return book;
    }
}
