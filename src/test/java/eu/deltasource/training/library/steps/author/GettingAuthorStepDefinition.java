package eu.deltasource.training.library.steps.author;

import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.repository.AuthorRepository;
import eu.deltasource.training.library.steps.HttpRequestExecutor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpMethod;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GettingAuthorStepDefinition {

    private AuthorRepository authorsRepository;
    private HttpRequestExecutor executor;

    public GettingAuthorStepDefinition(AuthorRepository authorsRepository,
                                       HttpRequestExecutor executor) {
        this.authorsRepository = authorsRepository;
        this.executor = executor;
    }

    @Then("We should get all the data for that Author")
    public void weShouldGetAllTheDataForThatAuthor() {
        assertThat(executor.getResponse().getBody().contains("test"), is(true));
        assertThat(executor.getResponse().getBody().contains("testov"), is(true));
        assertThat(executor.getResponse().getBody().contains("2000-01-01"), is(true));
    }

    @Given("The database has 3 Authors")
    public void theDatabaseHasAuthors() {
        authorsRepository.save(new Author("test", "testov", "2000-01-01"));
        authorsRepository.save(new Author("test1", "testov1", "2000-01-02"));
        authorsRepository.save(new Author("test2", "testov2", "2000-01-03"));
    }

    @When("Attempting to get the information for all Authors in the database")
    public void attemptingToGetTheInformationForAllAuthorsInTheDatabase() {
        String url =  "http://localhost:8080/author/get";
        executor.exchange(url, HttpMethod.GET);
    }

    @Then("We should get all the data for all the Authors in the database")
    public void weShouldGetAllTheDataForAllTheAuthorsInTheDatabase() {
        assertThat(executor.getResponse().getBody().contains("test"), is(true));
        assertThat(executor.getResponse().getBody().contains("test1"), is(true));
        assertThat(executor.getResponse().getBody().contains("test2"), is(true));
    }

    @When("Attempting to get the information for Author {int}")
    public void attemptingToGetTheInformationForAuthor(int authorId) {
        String url =  "http://localhost:8080/author/" + authorId;
        executor.exchange(url, HttpMethod.GET);
    }
}
