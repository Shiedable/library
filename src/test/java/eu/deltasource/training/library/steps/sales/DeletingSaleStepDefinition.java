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

public class DeletingSaleStepDefinition {

    private SaleRepository saleRepository;
    private BookRepository bookRepository;
    private HttpRequestExecutor executor;

    public DeletingSaleStepDefinition(SaleRepository saleRepository,
                                      BookRepository bookRepository,
                                      HttpRequestExecutor executor) {
        this.saleRepository = saleRepository;
        this.bookRepository = bookRepository;
        this.executor = executor;
    }

    @Given("The database has a Sale")
    public void theDatabaseHasASale() {
        Book book = new Book("test", "2000-01-01", "TST", 10);
        bookRepository.save(book);
        saleRepository.save(new Sale("2000-01-01", 10, book));
    }

    @When("Attempting to delete Sale {int}")
    public void attemptingToDeleteSale(int saleId) {
        String url =  "http://localhost:8080/sale/" + saleId;
        executor.exchange(url, HttpMethod.DELETE);
    }

    @Then("The database should not have that Sale")
    public void theDatabaseShouldNotHaveThatSale() {
        assertThat(saleRepository.count(), is(0L));
    }
}