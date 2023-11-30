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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DeletingBookStepDefinition {

    @Autowired
    private BooksRepository booksRepository;
    private ResponseEntity<String> actualResponse;
    private ResponseEntity<String> expectedResponse;
    @Autowired
    private EntityManagerHelper entityManagerHelper;

    @Given("The database has a Book")
    public void theDatabaseHasABook() {
        booksRepository.deleteAll();
        entityManagerHelper.resetTableId("books");
        booksRepository.save(new Book("test", LocalDate.parse("2000-01-01"), "TST", 10.50));
    }

    @When("Attempting to delete Book {int}")
    public void attemptingToDeleteBook(int bookId) {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  "http://localhost:8080/book/delete/" + bookId;
        actualResponse = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }

    @Then("The database should not have that Book")
    public void theDatabaseShouldNotHaveThatBook() {
        assertThat(booksRepository.count(), is(0L));
    }

    @Given("The database has no Books")
    public void theDatabaseHasNoBooks() {
        expectedResponse = new ResponseEntity<>("Book with such ID does not exist", HttpStatus.NOT_FOUND);
    }

    @Then("Then an error should occur with response")
    public void thenAnErrorShouldOccurWithResponseForNonExistentBook() {
        assertThat(actualResponse.getStatusCode(), is(expectedResponse.getStatusCode()));
        assertThat(actualResponse.getBody(), is(expectedResponse.getBody()));
    }

    @Given("Negative id for a Book")
    public void negativeIdForABook() {
        expectedResponse = new ResponseEntity<>("Entity ID should be positive", HttpStatus.BAD_REQUEST);
    }
}
