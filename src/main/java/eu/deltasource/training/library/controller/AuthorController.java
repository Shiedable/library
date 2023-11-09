package eu.deltasource.training.library.controller;

import eu.deltasource.training.library.exceptions.EmptyAuthorNameException;
import eu.deltasource.training.library.exceptions.IdNotFoundException;
import eu.deltasource.training.library.exceptions.NullDateException;
import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping(value = "/author/add")
    public ResponseEntity<String> addAuthor(@RequestParam("firstName") String firstName,
                                    @RequestParam("lastName") String lastName,
                                    @RequestParam("birthDate") String birthDate) {
        try {
            authorService.addAuthor(firstName, lastName, birthDate);
        } catch (EmptyAuthorNameException | NullDateException exception) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, exception.getMessage(), exception);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/author/delete/{id}")
    public ResponseEntity<String> deleteAuthorById(@PathVariable int id) {
        try {
            authorService.deleteAuthorById(id);
        } catch (IdNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/author/update/{id}")
    public ResponseEntity<String> updateAuthorById(@PathVariable int id,
                                  @RequestParam(name = "firstName", required = false) String firstName,
                                  @RequestParam(name = "lastName", required = false) String lastName,
                                  @RequestParam(name = "birthDate", required = false) String birthDate) {
        try {
            authorService.updateAuthorById(id, firstName, lastName, birthDate);
        } catch (EmptyAuthorNameException | NullDateException exception) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, exception.getMessage(), exception);
        } catch (IdNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/author/get/{id}")
    @ResponseBody
    public Author getAuthorById(@PathVariable int id) {
        Author author;
        try {
            author = authorService.getAuthorById(id);
        } catch (IdNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
        return author;
    }

    @GetMapping("/author/get/all")
    @ResponseBody
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }
}
