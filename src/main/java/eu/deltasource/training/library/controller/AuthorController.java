package eu.deltasource.training.library.controller;

import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that has endpoints for managing {@link Author}
 */
@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping(value = "/author")
    public ResponseEntity<String> addAuthor(@RequestParam("firstName") String firstName,
                                    @RequestParam("lastName") String lastName,
                                    @RequestParam("birthDate") String birthDate) {
        authorService.addAuthor(firstName, lastName, birthDate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity<String> deleteAuthorById(@PathVariable long id) {
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/author/{id}")
    public ResponseEntity<String> updateAuthorById(@PathVariable long id,
                                  @RequestParam(name = "firstName", required = false) String firstName,
                                  @RequestParam(name = "lastName", required = false) String lastName,
                                  @RequestParam(name = "birthDate", required = false) String birthDate) {
        authorService.updateAuthorById(id, firstName, lastName, birthDate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable long id) {
        Author author = authorService.getAuthorById(id).get();
        return new ResponseEntity<Author>(author, HttpStatus.OK);
    }

    @GetMapping("/author/get")
    public ResponseEntity<List<Author>> getAllAuthors() {
        return new ResponseEntity<>(authorService.getAllAuthors(), HttpStatus.OK);
    }
}
