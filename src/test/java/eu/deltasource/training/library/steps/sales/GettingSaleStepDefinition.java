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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GettingSaleStepDefinition {

    private SaleRepository saleRepository;
    private BookRepository bookRepository;
    private HttpRequestExecutor executor;
    private Book book;

    public GettingSaleStepDefinition(SaleRepository saleRepository,
                                     BookRepository bookRepository,
                                     HttpRequestExecutor executor) {
        this.saleRepository = saleRepository;
        this.bookRepository = bookRepository;
        this.executor = executor;
    }

    @Then("We should get all the data for that Sale")
    public void weShouldGetAllTheDataForThatSale() {
        assertThat(executor.getResponse().getBody().contains("test"), is(true));
        assertThat(executor.getResponse().getBody().contains("10.0$"), is(true));
        assertThat(executor.getResponse().getBody().contains("2000-01-01"), is(true));
        assertThat(executor.getResponse().getBody().contains("TST"), is(true));
    }

    @Given("The database has 3 Sales")
    public void theDatabaseHasSAles() {
        book = new Book("test", "2000-01-01", "TST", 10);
        bookRepository.save(book);
        saleRepository.save(new Sale("2000-01-01", 100, book));
        saleRepository.save(new Sale("2000-02-01", 200, book));
        saleRepository.save(new Sale("2000-03-01", 300, book));
    }

    @When("Attempting to get the information for all Sales in the database")
    public void attemptingToGetTheInformationForAllSalesInTheDatabase() {
        String url =  "http://localhost:8080/sale/get";
        executor.exchange(url, HttpMethod.GET);
    }

    @Then("We should get all the data for all the Sales in the database")
    public void weShouldGetAllTheDataForAllTheSalesInTheDatabase() {
        assertThat(executor.getResponse().getBody().contains("100"), is(true));
        assertThat(executor.getResponse().getBody().contains("200"), is(true));
        assertThat(executor.getResponse().getBody().contains("300"), is(true));
    }

    @When("Attempting to get the information for Sale {int}")
    public void attemptingToGetTheInformationForSale(long saleId) {
        String url =  "http://localhost:8080/sale/" + saleId;
        executor.exchange(url, HttpMethod.GET);
    }
}
