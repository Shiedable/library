package eu.deltasource.training.library.controller;

import eu.deltasource.training.library.model.Sale;
import eu.deltasource.training.library.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



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
        saleService.addSale(saleDate, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/sale/delete/{id}")
    public ResponseEntity<String> deleteSaleById(@PathVariable long id) {
        saleService.deleteSaleById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/sale/update/{id}")
    public ResponseEntity<String> updateSaleById(@PathVariable long id,
                                 @RequestParam(required = false) String saleDate,
                                 @RequestParam(required = false) int quantity) {
        saleService.updateSaleById(id, saleDate, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sale/get/{id}")
    public ResponseEntity<String> getSaleById(@PathVariable long id) {
        Sale sale = saleService.getSaleById(id).get();
        return new ResponseEntity<>(sale.toString(), HttpStatus.OK);
    }

    @GetMapping("/sale/get/all")
    public ResponseEntity<String> getAllSales() {
        return new ResponseEntity<>(saleService.getAllSales().toString(), HttpStatus.OK);
    }
}
