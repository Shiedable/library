package eu.deltasource.training.library.service;

import eu.deltasource.training.library.model.Sale;
import eu.deltasource.training.library.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SalesRepository sales;

    public void addSale(int book_id, String sale_date, int quantity) {
        Sale sale = new Sale(book_id, LocalDate.parse(sale_date), quantity);
        sales.addSale(sale);
    }

    public void deleteSaleById(int id) {
        sales.deleteSaleById(id);
    }

    public void updateSaleById(int id, int book_id, String sale_date, int quantity) {
        sales.updateSaleById(id, book_id, LocalDate.parse(sale_date), quantity);
    }

    public Sale getSaleById(int id) {
        return sales.getSaleById(id);
    }

    public List<Sale> getAllSales() {
        return sales.getAllSales();
    }
}
