package eu.deltasource.training.library.repository;

import eu.deltasource.training.library.exceptions.NegativeIdException;
import eu.deltasource.training.library.exceptions.NegativeNumberException;
import eu.deltasource.training.library.exceptions.NullDateException;
import eu.deltasource.training.library.model.Sale;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class SalesRepository {

    private List<Sale> sales;

    public SalesRepository() {
        sales = new ArrayList<>();
    }

    public void addSale(Sale newSale) {
        sales.add(newSale);
    }

    public Sale getSaleById(int id) {
        return sales.get(id);
    }

    public void updateSaleById(int id, int bookId, LocalDate saleDate, int quantity)
            throws NegativeIdException, NullDateException, NegativeNumberException {
        Sale sale = sales.get(id);
        sale.setBookId(bookId);
        sale.setSaleDate(saleDate);
        sale.setQuantity(quantity);
    }

    public void deleteSaleById(int id) {
        sales.remove(id);
    }

    public List<Sale> getAllSales() {
        return Collections.unmodifiableList(sales);
    }
}
