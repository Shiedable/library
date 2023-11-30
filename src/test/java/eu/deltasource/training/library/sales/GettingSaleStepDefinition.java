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

public class GettingSaleStepDefinition {

    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private BooksRepository booksRepository;
    private ResponseEntity<String> actualResponse;
    private ResponseEntity<String> expectedResponse;
    private Book book;
    @Autowired
    private EntityManagerHelper entityManagerHelper;

    @Given("The database has a Sale record")
    public void theDatabaseHasASaleRecord() {
        book = new Book(1,"test", LocalDate.parse("2000-01-01"), "TST", 10);
        booksRepository.save(book);
        salesRepository.deleteAll();
        entityManagerHelper.resetTableId("sales");
        salesRepository.save(new Sale(LocalDate.parse("2000-01-01"), 100, book));
    }

    @Then("We should get all the data for that Sale")
    public void weShouldGetAllTheDataForThatSale() {
        assertThat(actualResponse.getBody().contains("test"), is(true));
        assertThat(actualResponse.getBody().contains("100"), is(true));
        assertThat(actualResponse.getBody().contains("2000-01-01"), is(true));
        assertThat(actualResponse.getBody().contains("TST"), is(true));
    }

    @Given("The database has 3 Sales")
    public void theDatabaseHasSAles() {
        salesRepository.save(new Sale(LocalDate.parse("2000-01-01"), 100, book));
        salesRepository.save(new Sale(LocalDate.parse("2000-02-01"), 200, book));
        salesRepository.save(new Sale(LocalDate.parse("2000-03-01"), 300, book));
    }

    @When("Attempting to get the information for all Sales in the database")
    public void attemptingToGetTheInformationForAllSalesInTheDatabase() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  "http://localhost:8080/sale/get/all";
        actualResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    }

    @Then("We should get all the data for all the Sales in the database")
    public void weShouldGetAllTheDataForAllTheSalesInTheDatabase() {
        assertThat(actualResponse.getBody().contains("100"), is(true));
        assertThat(actualResponse.getBody().contains("200"), is(true));
        assertThat(actualResponse.getBody().contains("300"), is(true));
    }

    @Given("The database has no Sale record")
    public void theDatabaseHasNoSaleRecord() {
        expectedResponse = new ResponseEntity<>("Sale with such ID does not exist", HttpStatus.NOT_FOUND);
    }

    @When("Attempting to get the information for Sale {int}")
    public void attemptingToGetTheInformationForSale(long saleId) {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  "http://localhost:8080/sale/get/" + saleId;
        actualResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    }

    @Given("The database has 0 Sales")
    public void theDatabaseHasSales() {
        expectedResponse = new ResponseEntity<>("Entity ID should be positive", HttpStatus.BAD_REQUEST);
    }

    @Then("We should be getting Entity ID error")
    public void weShouldBeGettingEntityIDError() {
        assertThat(actualResponse.getStatusCode(), is(expectedResponse.getStatusCode()));
        assertThat(actualResponse.getBody(), is(expectedResponse.getBody()));
        salesRepository.deleteAll();
        entityManagerHelper.resetTableId("sales");
    }
}
