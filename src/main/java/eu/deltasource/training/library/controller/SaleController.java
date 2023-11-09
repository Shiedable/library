package eu.deltasource.training.library.controller;

import eu.deltasource.training.library.exceptions.IdNotFoundException;
import eu.deltasource.training.library.exceptions.NegativeIdException;
import eu.deltasource.training.library.exceptions.NegativeSaleQuantityException;
import eu.deltasource.training.library.exceptions.NullDateException;
import eu.deltasource.training.library.model.Sale;
import eu.deltasource.training.library.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Controller
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping("/sale/add")
    public ResponseEntity<String> addSale(@RequestParam int bookId,
                                          @RequestParam String saleDate,
                                          @RequestParam int quantity) {
        try {
            saleService.addSale(bookId, saleDate, quantity);
        } catch (NegativeIdException | NegativeSaleQuantityException | NullDateException exception) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, exception.getMessage(), exception);
        } catch (IdNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/sale/delete/{id}")
    public void deleteSaleById(@PathVariable int id) {
        try {
            saleService.deleteSaleById(id);
        } catch (IdNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @PutMapping("/sale/update/{id}")
    public ResponseEntity<String> updateSaleById(@PathVariable int id,
                                 @RequestParam(required = false) int bookId,
                                 @RequestParam(required = false) String saleDate,
                                 @RequestParam(required = false) int quantity) {
        try {
            saleService.updateSaleById(id, bookId, saleDate, quantity);
        } catch (NegativeIdException | NegativeSaleQuantityException | NullDateException exception) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, exception.getMessage(), exception);
        } catch (IdNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sale/get/{id}")
    public Sale getSaleById(@PathVariable int id) {
        try {
            return saleService.getSaleById(id);
        } catch (IdNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    @GetMapping("/sale/get/all")
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }
}
