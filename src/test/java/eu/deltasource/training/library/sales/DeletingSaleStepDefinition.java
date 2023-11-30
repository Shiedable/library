package eu.deltasource.training.library.sales;

import eu.deltasource.training.library.EntityManagerHelper;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.model.Sale;
import eu.deltasource.training.library.repository.BooksRepository;
import eu.deltasource.training.library.repository.SalesRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DeletingSaleStepDefinition {

    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private BooksRepository booksRepository;
    private ResponseEntity<String> actualResponse;
    private ResponseEntity<String> expectedResponse;
    @Autowired
    private EntityManagerHelper entityManagerHelper;

    @Given("The database has a Sale")
    public void theDatabaseHasASale() {
        Book book = new Book(1,"test", LocalDate.parse("2000-01-01"), "TST", 10);
        booksRepository.save(book);
        salesRepository.deleteAll();
        entityManagerHelper.resetTableId("sales");
        salesRepository.save(new Sale(LocalDate.parse("2000-01-01"), 100, book));
    }

    @When("Attempting to delete Sale {int}")
    public void attemptingToDeleteSale(int saleId) {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  "http://localhost:8080/sale/delete/" + saleId;
        actualResponse = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }

    @Then("The database should not have that Sale")
    public void theDatabaseShouldNotHaveThatSale() {
        assertThat(salesRepository.count(), is(0L));
    }

    @Given("The database has no Sale")
    public void theDatabaseHasNoSale() {
        expectedResponse = new ResponseEntity<>("Sale with such ID does not exist", HttpStatus.NOT_FOUND);
    }

    @Then("Then an error should occur with an error message")
    public void thenAnErrorShouldOccurWithAnErrorMessage() {
        assertThat(actualResponse.getStatusCode(), is(expectedResponse.getStatusCode()));
        assertThat(actualResponse.getBody(), is(expectedResponse.getBody()));
    }

    @Given("Negative Sale id")
    public void negativeSaleId() {
        expectedResponse = new ResponseEntity<>("Entity ID should be positive", HttpStatus.BAD_REQUEST);
    }
}