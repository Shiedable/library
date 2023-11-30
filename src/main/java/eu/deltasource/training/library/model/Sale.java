package eu.deltasource.training.library.model;

import jakarta.persistence.*;

import java.time.LocalDate;

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

    public Sale(LocalDate saleDate, int quantity, Book book) {
        this.saleDate = saleDate;
        this.quantity = quantity;
        this.book = book;
    }

    public Sale(long saleId, LocalDate saleDate, int quantity, Book book) {
        this(saleDate, quantity, book);
        this.saleId = saleId;
    }

    @Override
    public String toString() {
        return saleDate + "    " +  book + ": " + saleDate + ", x" + quantity;
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
}
