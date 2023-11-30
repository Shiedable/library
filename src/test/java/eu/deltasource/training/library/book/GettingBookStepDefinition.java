package eu.deltasource.training.library.book;

import eu.deltasource.training.library.EntityManagerHelper;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.repository.BooksRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GettingBookStepDefinition {

    @Autowired
    private BooksRepository booksRepository;
    private ResponseEntity<String> actualResponse;
    private ResponseEntity<String> expectedResponse;
    @Autowired
    private EntityManagerHelper entityManagerHelper;

    @Given("The database has a Book record")
    public void theDatabaseHasABookRecord() {
        booksRepository.deleteAll();
        entityManagerHelper.resetTableId("books");
        booksRepository.save(new Book("test", LocalDate.parse("2000-01-01"), "TST", 10.50));
    }

    @Then("We should get all the data for that Book")
    public void weShouldGetAllTheDataForThatBook() {
        assertThat(actualResponse.getBody().contains("test"), is(true));
        assertThat(actualResponse.getBody().contains("TST"), is(true));
        assertThat(actualResponse.getBody().contains("2000-01-01"), is(true));
        assertThat(actualResponse.getBody().contains("10.5"), is(true));
    }

    @Given("The database has 0 Books")
    public void theDatabaseHasBooks() {
        expectedResponse = new ResponseEntity<>("Entity ID should be positive", HttpStatus.BAD_REQUEST);
    }

    @When("Attempting to get the information for all Books in the database")
    public void attemptingToGetTheInformationForAllBooksInTheDatabase() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  "http://localhost:8080/book/get/all";
        actualResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    }

    @Then("We should get all the data for all the Books in the database")
    public void weShouldGetAllTheDataForAllTheBooksInTheDatabase() {
        assertThat(actualResponse.getBody().contains("test"), is(true));
        assertThat(actualResponse.getBody().contains("test1"), is(true));
        assertThat(actualResponse.getBody().contains("test2"), is(true));
    }

    @Given("The database has no Book record")
    public void theDatabaseHasNoBookRecord() {
        expectedResponse = new ResponseEntity<>("Book with such ID does not exist", HttpStatus.NOT_FOUND);
    }

    @When("Attempting to get the information for Book {int}")
    public void attemptingToGetTheInformationForBook(int bookId) {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  "http://localhost:8080/book/get/" + bookId;
        actualResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    }

    @Then("We should be getting Entity ID error message")
    public void weShouldBeGettingEntityIDErrorMessage() {
        assertThat(actualResponse.getStatusCode(), is(expectedResponse.getStatusCode()));
        assertThat(actualResponse.getBody(), is(expectedResponse.getBody()));
        booksRepository.deleteAll();
        entityManagerHelper.resetTableId("books");
    }

    @Given("The database has 3 Books")
    public void theDatabaseHas3Books() {
        booksRepository.save(new Book("test", LocalDate.parse("2000-01-01"), "TST", 10.50));
        booksRepository.save(new Book("test1", LocalDate.parse("2000-02-02"), "TST1", 10.50));
        booksRepository.save(new Book("test2", LocalDate.parse("2000-03-03"), "TST2", 10.50));
    }
}
