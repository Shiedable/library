package eu.deltasource.training.library.controller;

import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @RequestMapping(value = "/author/add", method = RequestMethod.POST)
    public ResponseEntity addAuthor(@RequestParam("fname") String fname,
                                    @RequestParam("lname") String lname,
                                    @RequestParam("bd") String bd) {
        authorService.addAuthor(fname, lname, bd);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/author/delete/{id}")
    @ResponseBody
    public ResponseEntity deleteAuthorById(@PathVariable int id) {
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/author/update/{id}")
    @ResponseBody
    public ResponseEntity updateAuthorById(@PathVariable int id,
                                  @RequestParam(name = "fname", required = false) String fname,
                                  @RequestParam(name = "lname", required = false) String lname,
                                  @RequestParam(name = "bd", required = false) String bd) {
        authorService.updateAuthorById(id, fname, lname, bd);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/author/get/{id}")
    @ResponseBody
    public Author getAuthorById(@PathVariable int id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/author/get/all")
    @ResponseBody
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }
}
