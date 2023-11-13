package eu.deltasource.training.library.service;

import eu.deltasource.training.library.exceptions.IdNotFoundException;
import eu.deltasource.training.library.exceptions.NegativeIdException;
import eu.deltasource.training.library.exceptions.NegativeNumberException;
import eu.deltasource.training.library.exceptions.NullDateException;
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
    @Autowired
    private BookService bookService;

    public void addSale(int bookId, String saleDate, int quantity)
            throws NegativeIdException, NegativeNumberException, NullDateException, IdNotFoundException {
        validateBookID(bookId);
        Sale sale = new Sale(bookId, LocalDate.parse(saleDate), quantity);
        sales.addSale(sale);
    }

    public void deleteSaleById(int id) throws IdNotFoundException {
        validateSaleID(id);
        sales.deleteSaleById(id);
    }

    public void updateSaleById(int id, int bookId, String saleDateString, int quantity)
            throws NegativeIdException, NegativeNumberException, NullDateException, IdNotFoundException {
        validateSaleID(id);
        validateBookID(bookId);
        LocalDate saleDate = validateAndParseDate(saleDateString);
        sales.updateSaleById(id, bookId, saleDate, quantity);
    }

    public Sale getSaleById(int id) throws IdNotFoundException {
        validateSaleID(id);
        return sales.getSaleById(id);
    }

    public List<Sale> getAllSales() {
        return sales.getAllSales();
    }

    private void validateSaleID(int id) throws IdNotFoundException {
        if (id >= sales.getAllSales().size()) {
            throw new IdNotFoundException("Sale with such ID does not exist");
        }
    }

    private void validateBookID(int id) throws IdNotFoundException {
        if (id >= bookService.getAllBooks().size()) {
            throw new IdNotFoundException("Book with such ID does not exist");
        }
    }

    private LocalDate validateAndParseDate(String date) {
        if (date == null) {
            return null;
        }
        return LocalDate.parse(date);
    }
}
