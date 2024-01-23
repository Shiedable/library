package eu.deltasource.training.library.controller;

import eu.deltasource.training.library.model.Sale;
import eu.deltasource.training.library.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService){
        this.saleService = saleService;
    }

    @PostMapping("/sale")
    public ResponseEntity<String> addSale(@RequestParam String saleDate,
                                          @RequestParam Optional<Integer> quantity,
                                          @RequestParam Long bookId) {
        saleService.addSale(saleDate, quantity, bookId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/sale/{id}")
    public ResponseEntity<String> deleteSaleById(@PathVariable long id) {
        saleService.deleteSaleById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/sale/{id}")
    public ResponseEntity<String> updateSaleById(@PathVariable long id,
                                 @RequestParam(required = false) String saleDate,
                                 @RequestParam(required = false) Optional<Integer> quantity,
                                 @RequestParam(required = false) Long bookId) {
        saleService.updateSaleById(id, saleDate, quantity, bookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sale/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable long id) {
        Sale sale = saleService.getSaleById(id).get();
        return new ResponseEntity<>(sale, HttpStatus.OK);
    }

    @GetMapping("/sale/get")
    public ResponseEntity<List<Sale>> getAllSales() {
        return new ResponseEntity<>(saleService.getAllSales(), HttpStatus.OK);
    }
}
