package eu.deltasource.training.library.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private long bookId;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;
    @Column(name = "ISBN", nullable = false)
    private String isbn;
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

    public Book(String title, LocalDate publicationDate, String isbn, double price) {
        this.title = title;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.price = price;
    }

    public Book(long bookId, String title, LocalDate publicationDate, String isbn, double price) {
        this(title, publicationDate, isbn, price);
        this.bookId = bookId;
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
}
