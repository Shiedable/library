package eu.deltasource.training.library.controller;

import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/book/add")
    public ResponseEntity addBook(@RequestParam int authorId,
                                  @RequestParam String title,
                                  @RequestParam String pd,
                                  @RequestParam String isbn,
                                  @RequestParam double price) {
        bookService.addBook(authorId, title, pd, isbn, price);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/book/delete/{id}")
    public void deleteBookById(@PathVariable int id) {
        bookService.deleteBookById(id);
    }

    @PutMapping("/book/update/{id}")
    public ResponseEntity updateBookById(@PathVariable int id,
                                 @RequestParam(required = false) int authorId,
                                 @RequestParam(required = false) String title,
                                 @RequestParam(required = false) String pd,
                                 @RequestParam(required = false) String isbn,
                                 @RequestParam(required = false) double price) {
        bookService.updateBookById(id, authorId, title, pd, isbn, price);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/book/get/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/book/get/all")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
}
