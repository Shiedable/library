package eu.deltasource.training.library.controller;

import eu.deltasource.training.library.exceptions.IdNotFoundException;
import eu.deltasource.training.library.exceptions.NegativeIdException;
import eu.deltasource.training.library.exceptions.NegativeNumberException;
import eu.deltasource.training.library.exceptions.InvalidDateException;
import eu.deltasource.training.library.model.Sale;
import eu.deltasource.training.library.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@Controller
public class SaleController {

    private SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService){
        this.saleService = saleService;
    }

    @PostMapping("/sale/add")
    public ResponseEntity<String> addSale(@RequestParam String saleDate,
                                          @RequestParam int quantity) {
        try {
            saleService.addSale(saleDate, quantity);
        } catch (NegativeIdException | NegativeNumberException | InvalidDateException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IdNotFoundException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/sale/delete/{id}")
    public ResponseEntity<String> deleteSaleById(@PathVariable long id) {
        try {
            saleService.deleteSaleById(id);
        } catch (IdNotFoundException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/sale/update/{id}")
    public ResponseEntity<String> updateSaleById(@PathVariable long id,
                                 @RequestParam(required = false) String saleDate,
                                 @RequestParam(required = false) int quantity) {
        try {
            saleService.updateSaleById(id, saleDate, quantity);
        } catch (NegativeIdException | NegativeNumberException | InvalidDateException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IdNotFoundException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sale/get/{id}")
    public ResponseEntity<String> getSaleById(@PathVariable long id) {
        Optional<Sale> sale;
        try {
            sale = saleService.getSaleById(id);
        } catch (IdNotFoundException exception) {
            return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sale.toString(), HttpStatus.OK);
    }

    @GetMapping("/sale/get/all")
    public ResponseEntity<String> getAllSales() {
        return new ResponseEntity<>(saleService.getAllSales().toString(), HttpStatus.OK);
    }
}
