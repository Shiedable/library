package eu.deltasource.training.library.repository;

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

    public void updateSaleById(int id, int book_id, LocalDate sale_date, int quantity) {
        Sale sale = sales.get(id);
        sale.setBook_id(book_id);
        sale.setSale_date(sale_date);
        sale.setQuantity(quantity);
    }

    public void deleteSaleById(int id) {
        sales.remove(id);
    }

    public List<Sale> getAllSales() {
        return Collections.unmodifiableList(sales);
    }
}
