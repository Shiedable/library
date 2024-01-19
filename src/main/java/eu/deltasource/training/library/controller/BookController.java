package eu.deltasource.training.library.controller;

import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/book")
    public ResponseEntity<String> addBook(@RequestParam String title,
                                  @RequestParam String publicationDate,
                                  @RequestParam String isbn,
                                  @RequestParam double price) {
        bookService.addBook(title, publicationDate, isbn, price);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<String> updateBookById(@PathVariable long id,
                                 @RequestParam(required = false) String title,
                                 @RequestParam(required = false) String publicationDate,
                                 @RequestParam(required = false) String isbn,
                                 @RequestParam(required = false) Optional<Double> price) {
        bookService.updateBookById(id, title, publicationDate, isbn, price);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<String> getBookById(@PathVariable long id) {
        Book book = bookService.getBookById(id).get();
        return new ResponseEntity<String>(book.toString(), HttpStatus.OK);
    }

    @GetMapping("/book/get")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<List<Book>>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @PostMapping("/bookByIsbn")
    public ResponseEntity<String> addBookByIsbn(@RequestParam String isbn) {
        return bookService.addBookByIsbn(isbn);
    }
}
