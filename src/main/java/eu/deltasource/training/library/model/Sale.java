package eu.deltasource.training.library.model;

import eu.deltasource.training.library.exceptions.InvalidAuthorException;
import eu.deltasource.training.library.exceptions.InvalidSaleException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.springframework.util.StringUtils.hasLength;

/**
 * This is an entity representing a Sale in our database
 */
@Entity
@Table(name="sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id", nullable = false)
    private long saleId;
    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Sale() {
    }

    public Sale(String saleDate, int quantity, Book book) {
        setQuantity(quantity);
        setSaleDate(saleDate);
        setBook(book);
    }

    public void setBook(Book book) {
        if (book == null) {
            throw new InvalidSaleException("Sale Book cannot be null");
        }
        this.book = book;
    }

    public void setSaleDate(String saleDate) {
        if (!hasLength(saleDate)) {
            throw new InvalidSaleException("Sale date cannot be empty");
        }
        parseOrThrowDate(saleDate);
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        } else {
            throw new InvalidSaleException("Sale quantity cannot be negative");
        }
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return saleId + "    " + saleDate + "    " +  book + ": " + saleDate + ", x" + quantity;
    }

    private void parseOrThrowDate(String date) {
        try {
            this.saleDate = LocalDate.parse(date);
        } catch (DateTimeParseException exception) {
            throw new InvalidSaleException("Could not parse Sale date: " + date + " reason: "
                    + exception.getMessage());
        }
    }
}
