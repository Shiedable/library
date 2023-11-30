package eu.deltasource.training.library.service;

import eu.deltasource.training.library.exceptions.InvalidDateException;
import eu.deltasource.training.library.exceptions.NegativeNumberException;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.model.Sale;
import eu.deltasource.training.library.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import static eu.deltasource.training.library.validator.Validator.*;
import static org.springframework.util.StringUtils.hasLength;

@Service
public class SaleService {

    private SalesRepository sales;
    @Autowired
    private BookService bookService;

    @Autowired
    public SaleService(SalesRepository salesRepository) {
        sales = salesRepository;
    }

    public void addSale(String saleDate, Optional<Integer> quantity, Long bookId) {
        validateDate(saleDate);
        validateNumber(Optional.of(Double.valueOf(quantity.get())));
        Book book = bookService.getBookById(bookId).get();
        Sale sale = new Sale(LocalDate.parse(saleDate), quantity.get(), book);
        sales.save(sale);
    }

    public void deleteSaleById(long id) {
        validateEntityExistence(id, sales);
        sales.deleteById(id);
    }

    public void updateSaleById(long id, String saleDateString, Optional<Integer> quantity, Long bookId) {
        validateEntityExistence(id, sales);
        Sale sale = sales.findById(id).get();
        Sale updatedSale = setUpdatedSale(id, sale, saleDateString, quantity, bookId);
        sales.save(updatedSale);
    }

    public Optional<Sale> getSaleById(long id) {
        validateEntityExistence(id, sales);
        return sales.findById(id);
    }

    public List<Sale> getAllSales() {
        return (List<Sale>) sales.findAll();
    }

    private Sale setUpdatedSale(long id, Sale sale, String saleDateString, Optional<Integer> quantity, Long bookId) {
        LocalDate saleDate;
        int quantityParam;
        Book book;
        if (!hasLength(saleDateString)) {
            saleDate = sale.getSaleDate();
        } else {
            try {
                saleDate = LocalDate.parse(saleDateString);
            } catch (DateTimeParseException exception) {
                throw new InvalidDateException("Date format is invalid");
            }
        }
        if (quantity.isPresent()) {
            if (quantity.get() < 0) {
                throw new NegativeNumberException("quantity should be positive");
            } else {
                quantityParam = quantity.get();
            }
        } else {
            quantityParam = sale.getQuantity();
        }
        if (bookId != null) {
            book = bookService.getBookById(bookId).get();
        } else {
            book = sales.findById(id).get().getBook();
        }
        return new Sale(id, saleDate, quantityParam, book);
    }
}
