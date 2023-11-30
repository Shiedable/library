package eu.deltasource.training.library.author;

import eu.deltasource.training.library.EntityManagerHelper;
import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.repository.AuthorsRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.core.Is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GettingAuthorStepDefinition {

    @Autowired
    private AuthorsRepository authorsRepository;
    private ResponseEntity<String> actualResponse;
    private ResponseEntity<String> expectedResponse;
    @Autowired
    private EntityManagerHelper entityManagerHelper;

    @When("Attempting to get the information for an Author")
    public void attempting_to_get_the_information_for_an_author() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  "http://localhost:8080/author/get/1";
        actualResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    }

    @Then("We should get all the data for that Author")
    public void weShouldGetAllTheDataForThatAuthor() {
        assertThat(actualResponse.getBody().contains("test"), is(true));
        assertThat(actualResponse.getBody().contains("testov"), is(true));
        assertThat(actualResponse.getBody().contains("2000-01-01"), is(true));
    }

    @Given("The database has an Author record")
    public void theDatabaseHasAnAuthorRecord() {
        authorsRepository.save(new Author("test", "testov", LocalDate.parse("2000-01-01")));
    }

    @Given("The database has 3 Authors")
    public void theDatabaseHasAuthors() {
        authorsRepository.save(new Author("test", "testov", LocalDate.parse("2000-01-01")));
        authorsRepository.save(new Author("test1", "testov1", LocalDate.parse("2000-01-02")));
        authorsRepository.save(new Author("test2", "testov2", LocalDate.parse("2000-01-03")));
    }

    @When("Attempting to get the information for all Authors in the database")
    public void attemptingToGetTheInformationForAllAuthorsInTheDatabase() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  "http://localhost:8080/author/get/all";
        actualResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    }

    @Then("We should get all the data for all the Authors in the database")
    public void weShouldGetAllTheDataForAllTheAuthorsInTheDatabase() {
        assertThat(actualResponse.getBody().contains("test"), is(true));
        assertThat(actualResponse.getBody().contains("test1"), is(true));
        assertThat(actualResponse.getBody().contains("test2"), is(true));
    }

    @Given("The database has no Author record")
    public void theDatabaseHasNoAuthorRecord() {
        expectedResponse = new ResponseEntity<>("Author with such ID does not exist", HttpStatus.NOT_FOUND);
        authorsRepository.deleteAll();
        entityManagerHelper.resetTableId("authors");
    }

    @When("Attempting to get the information for Author {int}")
    public void attemptingToGetTheInformationForAuthor(int authorId) {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  "http://localhost:8080/author/get/" + authorId;
        actualResponse = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    }

    @Then("We should be getting Entity ID error response")
    public void weShouldBeGettingEntityIDErrorResponse() {
        assertThat(actualResponse.getStatusCode(), is(expectedResponse.getStatusCode()));
        assertThat(actualResponse.getBody(), is(expectedResponse.getBody()));
    }

    @Given("The database has 0 Authors")
    public void theDatabaseHas0Authors() {
        expectedResponse = new ResponseEntity<>("Entity ID should be positive", HttpStatus.BAD_REQUEST);
        authorsRepository.deleteAll();
        entityManagerHelper.resetTableId("authors");
    }
}
