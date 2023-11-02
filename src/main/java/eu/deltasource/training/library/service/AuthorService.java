package eu.deltasource.training.library.service;

import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.repository.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorsRepository authors;

    public void addAuthor(String first_name, String last_name, String birthdate) {
        Author author = new Author(first_name, last_name, LocalDate.parse(birthdate));
        authors.addAuthor(author);
    }

    public void deleteAuthorById(int id) {
        authors.deleteAuthorById(id);
    }

    public void updateAuthorById(int id, String first_name, String last_name, String birthdate) {
        LocalDate birthday = birthdate == null ? null : LocalDate.parse(birthdate);
        authors.updateAuthorById(id, first_name, last_name, birthday);
    }

    public Author getAuthorById(int id) {
        return authors.getAuthorById(id);
    }

    public List<Author> getAllAuthors() {
        return authors.getAllAuthors();
    }
}
