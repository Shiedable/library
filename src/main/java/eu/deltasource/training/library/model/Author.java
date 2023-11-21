package eu.deltasource.training.library.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

//pojo that represents an author in our system
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

    public Author(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Author(long authorId, String firstName, String lastName, LocalDate birthDate) {
        this(firstName, lastName, birthDate);
        this.authorId = authorId;
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
}
