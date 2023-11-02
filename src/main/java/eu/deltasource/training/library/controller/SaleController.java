package eu.deltasource.training.library.controller;

import eu.deltasource.training.library.model.Sale;
import eu.deltasource.training.library.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping("/sale/add")
    public ResponseEntity addSale(@RequestParam int bookId, @RequestParam String saleDate, @RequestParam int quantity) {
        saleService.addSale(bookId, saleDate, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/sale/delete/{id}")
    public void deleteSaleById(@PathVariable int id) {
        saleService.deleteSaleById(id);
    }

    @PutMapping("/sale/update/{id}")
    public ResponseEntity updateSaleById(@PathVariable int id,
                                 @RequestParam(required = false) int bookId,
                                 @RequestParam(required = false) String saleDate,
                                 @RequestParam(required = false) int quantity) {
        saleService.updateSaleById(id, bookId, saleDate, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sale/get/{id}")
    public Sale getSaleById(@PathVariable int id) {
        return saleService.getSaleById(id);
    }

    @GetMapping("/sale/get/all")
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }
}
