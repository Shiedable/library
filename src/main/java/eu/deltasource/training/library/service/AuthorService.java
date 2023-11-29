package eu.deltasource.training.library.service;

import eu.deltasource.training.library.exceptions.InvalidDateException;
import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.repository.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import static eu.deltasource.training.library.validator.Validator.*;
import static org.springframework.util.StringUtils.hasLength;


@Service
public class AuthorService {

    private AuthorsRepository authors;

    @Autowired
    public AuthorService(AuthorsRepository authorsRepository) {
        authors = authorsRepository;
    }

    public void addAuthor(String firstName, String lastName, String birthDate) {
        validateString(firstName);
        validateString(lastName);
        validateDate(birthDate);
        Author author = new Author(firstName, lastName, LocalDate.parse(birthDate));
        authors.save(author);
    }

    public void deleteAuthorById(long id) {
        validateEntityExistence(id, authors);
        authors.deleteById(id);
    }

    public void updateAuthorById(long id, String firstName, String lastName, String birthDate) {
        validateEntityExistence(id, authors);
        Author author = authors.findById(id).get();
        Author updatedAuthor = setUpdatedAuthor(id, author, firstName, lastName, birthDate);
        authors.save(updatedAuthor);
    }

    public Optional<Author> getAuthorById(long id) {
        validateEntityExistence(id, authors);
        return authors.findById(id);
    }

    public List<Author> getAllAuthors() {
        return (List<Author>) authors.findAll();
    }

    // update author
    // see if you can refactor this
    private Author setUpdatedAuthor(long id, Author author, String firstName, String lastName, String birthDate) {
        LocalDate birthday;
        if (!hasLength(firstName)) {
            firstName = author.getFirstName();
        }
        if(!hasLength(lastName)) {
            lastName = author.getLastName();
        }
        if (!hasLength(birthDate)) {
            birthday = author.getBirthDate();
        } else {
            try {
                birthday = LocalDate.parse(birthDate);
            } catch (DateTimeParseException exception) {
                throw new InvalidDateException("Date format is invalid");
            }
        }
        return new Author(id, firstName, lastName, birthday);
    }
}
