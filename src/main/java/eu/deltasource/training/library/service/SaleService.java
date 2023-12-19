package eu.deltasource.training.library.service;

import eu.deltasource.training.library.exceptions.EntityNotFoundException;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.model.Sale;
import eu.deltasource.training.library.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.springframework.util.StringUtils.hasLength;

/**
 * This is a service handling crud operations for {@link Sale}
 */
@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final BookService bookService;

    public SaleService(SaleRepository saleRepository, BookService bookService) {
        this.saleRepository = saleRepository;
        this.bookService = bookService;
    }

    public void addSale(String saleDate, Optional<Integer> quantity, Long bookId) {
        Book book = bookService.getBookById(bookId).get();
        Sale sale = new Sale(saleDate, quantity.get(), book);
        saleRepository.save(sale);
    }

    public void deleteSaleById(long id) {
        if (saleRepository.existsById(id)) {
            saleRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Entity with such ID does not exist");
        }
    }

    public void updateSaleById(long id, String saleDateString, Optional<Integer> quantity, Long bookId) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with such ID does not exist"));
        Optional<Book> book = bookService.getBookById(id);
        setIfPresent(saleDateString, sale::setSaleDate);
        setIfPresent(quantity, book, sale::setQuantity, sale::setBook);
        saleRepository.save(sale);
    }

    public Optional<Sale> getSaleById(long id) {
        if (saleRepository.existsById(id)) {
            return saleRepository.findById(id);
        } else {
            throw new EntityNotFoundException("Entity with such ID does not exist");
        }
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    private void setIfPresent(String value, Consumer<String> consumer) {
        if (hasLength(value)) {
            consumer.accept(value);
        }
    }

    private void setIfPresent(Optional<Integer> value,
                              Optional<Book> book,
                              Consumer<Integer> consumer,
                              Consumer<Book> bookConsumer) {
        value.ifPresent(consumer);
        book.ifPresent(bookConsumer);
    }
}
