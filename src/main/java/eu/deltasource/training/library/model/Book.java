package eu.deltasource.training.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.deltasource.training.library.exceptions.InvalidBookException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.springframework.util.StringUtils.hasLength;

/**
 * This is an entity representing a Book in our database
 */
@Entity
@Table(name="books")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private long bookId;
    @JsonProperty
    @Column(name = "title", nullable = false)
    private String title;
    @JsonProperty
    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;
    @JsonProperty
    @Column(name = "ISBN", nullable = false)
    private String isbn;
    @JsonProperty
    @Column(name = "price", nullable = false)
    private double price;

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<Sale> sales;

    @ManyToMany
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    public Book() {
    }

    public Book(String title, String publicationDate, String isbn, double price) {
        setTitle(title);
        setPublicationDate(publicationDate);
        setIsbn(isbn);
        setPrice(price);
    }

    public void setTitle(String title)   {
        if (hasLength(title)) {
            this.title = title;
        } else {
            throw new InvalidBookException("Book title cannot be empty");
        }
    }

    public void setPublicationDate(String publicationDate) {
        if (!hasLength(publicationDate)) {
            throw new InvalidBookException("Book publication date cannot be empty");
        }
        parseOrThrowDate(publicationDate);
    }

    public void setIsbn(String isbn) {
        if (hasLength(isbn)) {
            this.isbn = isbn;
        } else {
            throw new InvalidBookException("Book ISBN cannot be empty");
        }
    }

    public void setPrice(double price) {
        if (price >= 0.0) {
            this.price = price;
        } else {
            throw new InvalidBookException("Book price cannot be negative");
        }
    }

    public long getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public double getPrice() {
        return price;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return title + ", "  + publicationDate + " | " + isbn + " | " + price + "$";
    }

    private void parseOrThrowDate(String date) {
        try {
            this.publicationDate = LocalDate.parse(date);
        } catch (DateTimeParseException exception) {
            throw new InvalidBookException("Could not parse Publication date: " + date + " reason: "
                    + exception.getMessage());
        }
    }
}
