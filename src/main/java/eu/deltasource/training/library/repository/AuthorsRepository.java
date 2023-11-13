package eu.deltasource.training.library.repository;

import eu.deltasource.training.library.exceptions.EmptyStringException;
import eu.deltasource.training.library.exceptions.NullDateException;
import eu.deltasource.training.library.model.Author;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class AuthorsRepository {

    private List<Author> authors;

    public AuthorsRepository() {
        authors = new ArrayList<>();
    }

    public void addAuthor(Author newAuthor) {
        authors.add(newAuthor);
    }

    public Author getAuthorById(int id) {
        return authors.get(id);
    }

    public void updateAuthorById(int id, String firstName, String lastName, LocalDate birthDate)
            throws EmptyStringException, NullDateException {
        Author author = authors.get(id);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setBirthday(birthDate);
    }

    public void deleteAuthorById(int id) {
        authors.remove(id);
    }

    public List<Author> getAllAuthors() {
        return Collections.unmodifiableList(authors);
    }
}
