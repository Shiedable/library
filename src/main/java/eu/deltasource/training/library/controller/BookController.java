package eu.deltasource.training.library.controller;

import eu.deltasource.training.library.exceptions.*;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/book/add")
    public ResponseEntity<String> addBook(@RequestParam int authorId,
                                  @RequestParam String title,
                                  @RequestParam String pd,
                                  @RequestParam String isbn,
                                  @RequestParam double price) {
        try {
            bookService.addBook(authorId, title, pd, isbn, price);
        } catch ( NegativeBookPriceException | EmptyBookTitleException | EmptyIsbnException | NullDateException |
        NegativeIdException exception) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, exception.getMessage(), exception);
        } catch (IdNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/book/delete/{id}")
    public void deleteBookById(@PathVariable int id) {
        try {
            bookService.deleteBookById(id);
        } catch (IdNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @PutMapping("/book/update/{id}")
    public ResponseEntity<String> updateBookById(@PathVariable int id,
                                 @RequestParam(required = false) int authorId,
                                 @RequestParam(required = false) String title,
                                 @RequestParam(required = false) String pd,
                                 @RequestParam(required = false) String isbn,
                                 @RequestParam(required = false) double price) {
        try {
            bookService.updateBookById(id, authorId, title, pd, isbn, price);
        } catch ( NegativeBookPriceException | EmptyBookTitleException | EmptyIsbnException | NullDateException |
                  NegativeIdException exception) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, exception.getMessage(), exception);
        } catch (IdNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/book/get/{id}")
    public Book getBookById(@PathVariable int id) {
        try {
            return bookService.getBookById(id);
        } catch (IdNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @GetMapping("/book/get/all")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
}
