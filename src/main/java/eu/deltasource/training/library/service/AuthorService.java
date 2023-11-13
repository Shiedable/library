package eu.deltasource.training.library.service;

import eu.deltasource.training.library.exceptions.EmptyStringException;
import eu.deltasource.training.library.exceptions.IdNotFoundException;
import eu.deltasource.training.library.exceptions.NullDateException;
import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.repository.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorsRepository authors;

    public void addAuthor(String firstName, String lastName, String birthDate)
            throws EmptyStringException, NullDateException {
        LocalDate birthday = validateAndParseDate(birthDate);
        Author author = new Author(firstName, lastName, birthday);
        authors.addAuthor(author);
    }

    public void deleteAuthorById(int id) throws IdNotFoundException {
        validateID(id);
        authors.deleteAuthorById(id);
    }

    public void updateAuthorById(int id, String firstName, String lastName, String birthDate)
            throws EmptyStringException, NullDateException, IdNotFoundException {
        validateID(id);
        LocalDate birthday = validateAndParseDate(birthDate);
        authors.updateAuthorById(id, firstName, lastName, birthday);
    }

    public Author getAuthorById(int id) throws IdNotFoundException {
        validateID(id);
        return authors.getAuthorById(id);
    }

    public List<Author> getAllAuthors() {
        return authors.getAllAuthors();
    }

    private void validateID(int id) throws IdNotFoundException {
        if (id >= authors.getAllAuthors().size()) {
            throw new IdNotFoundException("Author with such ID does not exist");
        }
    }

    private LocalDate validateAndParseDate(String date) {
        if (date == null) {
            return null;
        }
        if (!StringUtils.hasLength(date)) {
            throw new EmptyStringException("Birth date is empty!");
        }
            return LocalDate.parse(date);
    }
}
