package eu.deltasource.training.library.steps.sale;

import eu.deltasource.training.library.DatabaseResetter;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.model.Sale;
import eu.deltasource.training.library.repository.BookRepository;
import eu.deltasource.training.library.repository.SaleRepository;
import eu.deltasource.training.library.steps.HttpRequestExecutor;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AddingSaleStepDefinition {

    private SaleRepository saleRepository;
    private BookRepository bookRepository;
    private HttpRequestExecutor executor;
    private DatabaseResetter databaseResetter;
    private String saleDate;
    private int quantity;
    private long bookId;
    private Book book;

    public AddingSaleStepDefinition(SaleRepository saleRepository,
                                    BookRepository bookRepository,
                                    HttpRequestExecutor executor,
                                    DatabaseResetter databaseResetter) {
        this.saleRepository = saleRepository;
        this.bookRepository = bookRepository;
        this.executor = executor;
        this.databaseResetter = databaseResetter;
    }

    @Before
    public void initialize() {
        saleRepository.deleteAll();
        databaseResetter.clear("sales");
    }

    @Given("^Sale Date (.*), Quantity (.*)$")
    public void validSaleInformation(String saleDate, int quantity) {
        this.saleDate = saleDate;
        this.quantity = quantity;
        bookId = 1;
        book = new Book("test", "2000-01-01", "TST", 10);
        bookRepository.save(book);
    }

    @When("adding a new Sale")
    public void addingANewSale() {
        String url =  constructUrl();
        executor.exchange(url, HttpMethod.POST);
    }

    @Then("The database should contain that same Sale with id {int}")
    public void theDatabaseShouldContainThatSameSaleWithId(long saleId) {
        Sale actual = saleRepository.findById(saleId).get();
        assertThat(actual.getQuantity(), is(quantity));
        assertThat(actual.getBook().toString(), is(book.toString()));
        assertThat(actual.getSaleDate(), is(LocalDate.parse(saleDate)));
    }

    @Given("Null sale date")
    public void noSaleDate() {
        saleDate = null;
        quantity = 100;
        bookId = 1;
        book = new Book("test", "2000-01-01", "TST", 10);
        bookRepository.save(book);
    }

    private String constructUrl() {
        UriComponentsBuilder url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/sale");
        if (saleDate != null) {
            url.queryParam("saleDate", saleDate);
        }
        return url.queryParam("quantity", quantity)
                .queryParam("bookId", "1")
                .build()
                .encode()
                .toUriString();
    }
}
