package eu.deltasource.training.library.model;

import eu.deltasource.training.library.exceptions.InvalidAuthorException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.springframework.util.StringUtils.hasLength;

/**
 * This is an entity representing an Author in our database
 */
@Entity
@Table(name="authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", nullable = false)
    private long authorId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthDate;

    @ManyToMany
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books;

    public Author() {

    }

    public Author(String firstName, String lastName, String birthDate) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
    }

    public void setFirstName(String firstName) {
        if (hasLength(firstName)) {
            this.firstName = firstName;
        } else {
            throw new InvalidAuthorException("Author first name cannot be empty");
        }
    }

    public void setLastName(String lastName) {
        if (hasLength(lastName)) {
            this.lastName = lastName;
        } else {
            throw new InvalidAuthorException("Author last name cannot be empty");
        }
    }

    public void setBirthDate(String birthDate) {
        if (!hasLength(birthDate)) {
            throw new InvalidAuthorException("Author birth date cannot be empty");
        }
        parseOrThrowDate(birthDate);
    }

    public long getAuthorId() {
        return authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", birthday=" + birthDate;
    }

    private void parseOrThrowDate(String date) {
        try {
            this.birthDate = LocalDate.parse(date);
        } catch (DateTimeParseException exception) {
            throw new InvalidAuthorException("Could not parse Birth date: " + date + " reason: "
                    + exception.getMessage());
        }
    }
}
