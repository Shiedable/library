package eu.deltasource.training.library.service;

import eu.deltasource.training.library.exceptions.EntityNotFoundException;
import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.springframework.util.StringUtils.hasLength;

/**
 * This is a service handling crud operations for {@link Author}
 */
@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorsRepository) {
        authorRepository = authorsRepository;
    }

    public void addAuthor(String firstName, String lastName, String birthDate) {
        Author author = new Author(firstName, lastName, birthDate);
        authorRepository.save(author);
    }

    public void deleteAuthorById(long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Entity with such ID does not exist");
        }
    }

    public void updateAuthorById(long id, String firstName, String lastName, String birthDate) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with such ID does not exist"));
        setIfPresent(firstName, author::setFirstName);
        setIfPresent(lastName, author::setLastName);
        setIfPresent(birthDate, author::setBirthDate);
        authorRepository.save(author);
    }

    public Optional<Author> getAuthorById(long id) {
        if (authorRepository.existsById(id)) {
            return authorRepository.findById(id);
        } else {
            throw new EntityNotFoundException("Entity with such ID does not exist");
        }
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    private void setIfPresent(String value, Consumer<String> consumer) {
        if (hasLength(value)) {
            consumer.accept(value);
        }
    }
}
