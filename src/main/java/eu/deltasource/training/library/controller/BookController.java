package eu.deltasource.training.library.controller;

import eu.deltasource.training.library.exceptions.*;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/book/add")
    public ResponseEntity<String> addBook(@RequestParam String title,
                                  @RequestParam String pd,
                                  @RequestParam String isbn,
                                  @RequestParam double price) {
        try {
            bookService.addBook(title, pd, isbn, price);
        } catch (NegativeNumberException | InvalidStringException | InvalidDateException | NegativeIdException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IdNotFoundException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/book/delete/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable long id) {
        try {
            bookService.deleteBookById(id);
        } catch (IdNotFoundException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/book/update/{id}")
    public ResponseEntity<String> updateBookById(@PathVariable long id,
                                 @RequestParam(required = false) String title,
                                 @RequestParam(required = false) String pd,
                                 @RequestParam(required = false) String isbn,
                                 @RequestParam(required = false) double price) {
        try {
            bookService.updateBookById(id, title, pd, isbn, price);
        } catch (NegativeNumberException | InvalidStringException | InvalidDateException |
                 NegativeIdException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IdNotFoundException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/book/get/{id}")
    public ResponseEntity<String> getBookById(@PathVariable long id) {
        Optional<Book> book;
        try {
            book = bookService.getBookById(id);
        } catch (IdNotFoundException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(book.toString(), HttpStatus.OK);
    }

    @GetMapping("/book/get/all")
    public ResponseEntity<String> getAllBooks() {
        return new ResponseEntity<String>(bookService.getAllBooks().toString(), HttpStatus.NOT_FOUND);
    }
}
