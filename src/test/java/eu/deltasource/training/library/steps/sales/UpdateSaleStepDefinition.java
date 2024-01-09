package eu.deltasource.training.library.steps.sales;

import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.model.Sale;
import eu.deltasource.training.library.repository.BookRepository;
import eu.deltasource.training.library.repository.SaleRepository;
import eu.deltasource.training.library.steps.HttpRequestExecutor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UpdateSaleStepDefinition {

    private SaleRepository saleRepository;
    private BookRepository bookRepository;
    private HttpRequestExecutor executor;
    private String saleDate;
    private int quantity;
    private Book book;

    public UpdateSaleStepDefinition(SaleRepository saleRepository,
                                    BookRepository bookRepository,
                                    HttpRequestExecutor executor) {
        this.saleRepository = saleRepository;
        this.bookRepository = bookRepository;
        this.executor = executor;
    }

    @Given("Sale Date to be updated to 2022-02-02")
    public void saleDateToBeUpdatedTo() {
        saleDate = "2000-01-01";
        quantity = 100;
        book = new Book("test", "2000-01-01", "TST", 10);
        bookRepository.save(book);
        saleRepository.save(new Sale("2000-01-01", 100, book));
    }

    @When("Attempting to update the information for Sale {int}")
    public void attemptingToUpdateTheInformationForSale(int saleId) {
        String url =  constructUrl(saleId);
        executor.exchange(url, HttpMethod.PUT);
    }

    @Then("The Sale should have been updated")
    public void theSaleShouldHaveBeenUpdated() {
        Sale actual = saleRepository.findById(1L).get();
        assertThat(actual.getQuantity(), is(quantity));
        assertThat(actual.getBook().toString(), is(book.toString()));
        assertThat(actual.getSaleDate(), is(LocalDate.parse(saleDate)));
    }

    @Given("Invalid sale date format to be updated")
    public void invalidSaleDateFormatToBeUpdated() {
        saleDate = "2000 text 01-01";
        quantity = 100;
        book = new Book("test", "2000-01-01", "TST", 10);
        bookRepository.save(book);
        saleRepository.save(new Sale("2000-01-01", 100, book));
    }

    @Given("Quantity to be updated to {int}")
    public void quantityToBeUpdatedTo(int quantityUpdate) {
        saleDate = "2000-01-01";
        quantity = quantityUpdate;
        book = new Book("test", "2000-01-01", "TST", 10);
        bookRepository.save(book);
        saleRepository.save(new Sale("2000-01-01", 100, book));
    }

    @Given("Invalid quantity to be updated to {int}")
    public void invalidQuantityToBeUpdatedTo(int quantityUpdate) {
        saleDate = "2000-01-01";
        quantity = quantityUpdate;
        book = new Book("test", "2000-01-01", "TST", 10);
    }

    private String constructUrl(int saleId) {
        UriComponentsBuilder url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/sale/" + saleId);
        if (saleDate != null) {
            url.queryParam("saleDate", saleDate);
        }
        return url.queryParam("quantity", quantity)
                .queryParam("bookId", "1")
                .build()
                .encode()
                .toUriString();
    }

    @Given("^Invalid Sale Date (.*), Quantity (.*)$")
    public void invalidSaleDateSaleDateQuantityQuantity(String saleDate, int quantity) {
        this.saleDate = saleDate;
        this.quantity = quantity;
        book = new Book("test", "2000-01-01", "TST", 10);
        bookRepository.save(book);
        saleRepository.save(new Sale("2000-01-01", 100, book));
    }
}
