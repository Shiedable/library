package eu.deltasource.training.library.authors;

import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.repository.AuthorsRepository;
import eu.deltasource.training.library.EntityManagerHelper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeletingAuthorStepDefinition {

    @Autowired
    private AuthorsRepository authorsRepository;
    private ResponseEntity<String> actualResponse;
    private ResponseEntity<String> expectedResponse;
    @Autowired
    private EntityManagerHelper entityManagerHelper;


    @Before
    public void initialize() {
        authorsRepository.deleteAll();
        entityManagerHelper.resetTableId("authors");
    }

    @Given("The database has an Author")
    public void theDatabaseHasAuthor() {
        authorsRepository.save(new Author(1, "test", "test", LocalDate.parse("2000-01-01")));
    }

    @Given("The database has no Author")
    public void theDatabaseHasNoAuthor() {
        authorsRepository.deleteAll();
        expectedResponse = new ResponseEntity<>("Author with such ID does not exist", HttpStatus.NOT_FOUND);
    }

    @Given("Negative id")
    public void negativeId() {
        authorsRepository.deleteAll();
        expectedResponse = new ResponseEntity<>("Entity ID should positive", HttpStatus.BAD_REQUEST);
    }

    @When("^Attempting to delete Author (.*)$")
    public void attemptingToDeleteThatAuthor(long authorId) throws IOException {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  "http://localhost:8080/author/delete/" + authorId;
        actualResponse = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }

    @Then("The database should not have that Author")
    public void theDatabaseShouldBeEmpty() {
        assertThat(authorsRepository.count(), is(0L));
    }

    @Then("Then an error should occur with an error response")
    public void thenAnErrorShouldOccurWithAResponseForAuthorNotExisting() {
        assertThat(actualResponse.getStatusCode(), is(expectedResponse.getStatusCode()));
        assertThat(actualResponse.getBody(), is(expectedResponse.getBody()));
    }
}
