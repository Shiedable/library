package eu.deltasource.training.library.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Sale {

    private int book_id;
    private LocalDate sale_date;
    private int quantity;

    public Sale() {
    }

    public Sale(int book_id, LocalDate sale_date, int quantity) {
        this.book_id = book_id;
        this.sale_date = sale_date;
        this.quantity = quantity;
    }

    public void setBook_id(int book_id) {
        if (book_id > 0) {
            this.book_id = book_id;
        }
    }

    public void setSale_date(LocalDate sale_date) {
        if (sale_date != null) {
            this.sale_date = sale_date;
        }
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        }
    }

    @Override
    public String toString() {
        return book_id + " - " + sale_date + ", x" + quantity;
    }
}
