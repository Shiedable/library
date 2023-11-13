package eu.deltasource.training.library.model;

import eu.deltasource.training.library.exceptions.NegativeIdException;
import eu.deltasource.training.library.exceptions.NegativeNumberException;
import eu.deltasource.training.library.exceptions.NullDateException;

import java.time.LocalDate;

public class Sale {

    private int bookId;
    private LocalDate saleDate;
    private int quantity;

    public Sale() {
    }

    public Sale(int bookId, LocalDate saleDate, int quantity)
            throws NegativeIdException, NullDateException, NegativeNumberException {
        setBookId(bookId);
        setSaleDate(saleDate);
        setQuantity(quantity);
    }

    public void setBookId(int bookId) throws NegativeIdException {
        if (bookId > 0) {
            this.bookId = bookId;
        } else {
            throw new NegativeIdException("Book ID cannot be negative");
        }
    }

    public void setSaleDate(LocalDate saleDate) throws NullDateException {
        if (saleDate != null) {
            this.saleDate = saleDate;
        } else {
            throw new NullDateException("Sale Date is null");
        }
    }

    public void setQuantity(int quantity) throws NegativeNumberException {
        if (quantity > 0) {
            this.quantity = quantity;
        } else {
            throw new NegativeNumberException("Sale quantity cannot be negative");
        }
    }

    @Override
    public String toString() {
        return bookId + " - " + saleDate + ", x" + quantity;
    }
}
