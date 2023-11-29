package eu.deltasource.training.library.service;

import eu.deltasource.training.library.exceptions.InvalidDateException;
import eu.deltasource.training.library.exceptions.NegativeNumberException;
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
    public SaleService(SalesRepository salesRepository) {
        sales = salesRepository;
    }

    public void addSale(String saleDate, int quantity) {
        validateDate(saleDate);
        validateNumber(quantity);
        Sale sale = new Sale(LocalDate.parse(saleDate), quantity);
        sales.save(sale);
    }

    public void deleteSaleById(long id) {
        validateEntityExistence(id, sales);
        sales.deleteById(id);
    }

    public void updateSaleById(long id, String saleDateString, int quantity) {
        validateEntityExistence(id, sales);
        Sale sale = sales.findById(id).get();
        Sale updatedSale = setUpdatedSale(id, sale, saleDateString, quantity);
        sales.save(updatedSale);
    }

    public Optional<Sale> getSaleById(long id) {
        validateEntityExistence(id, sales);
        return sales.findById(id);
    }

    public List<Sale> getAllSales() {
        return (List<Sale>) sales.findAll();
    }

    private Sale setUpdatedSale(long id, Sale sale, String saleDateString, int quantity) {
        LocalDate saleDate;
        if (!hasLength(saleDateString)) {
            saleDate = sale.getSaleDate();
        } else {
            try {
                saleDate = LocalDate.parse(saleDateString);
            } catch (DateTimeParseException exception) {
                throw new InvalidDateException("Date format is invalid");
            }
        }
        if (quantity < 0) {
            throw new NegativeNumberException("quantity should be positive");
        } else if (quantity == 0) {
            quantity = sale.getQuantity();
        }
        return new Sale(id, saleDate, quantity);
    }
}
