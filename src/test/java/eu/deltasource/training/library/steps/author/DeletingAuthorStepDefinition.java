package eu.deltasource.training.library.steps.author;

import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.repository.AuthorRepository;
import eu.deltasource.training.library.steps.HttpRequestExecutor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpMethod;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeletingAuthorStepDefinition {

    private AuthorRepository authorsRepository;
    private HttpRequestExecutor executor;

    public DeletingAuthorStepDefinition(AuthorRepository authorsRepository, HttpRequestExecutor executor) {
        this.authorsRepository = authorsRepository;
        this.executor = executor;
    }

    @Given("The database has an Author")
    public void theDatabaseHasAuthor() {
        authorsRepository.save(new Author("test", "testov", "2000-01-01"));
    }

    @When("^Attempting to delete Author (.*)$")
    public void attemptingToDeleteThatAuthor(long authorId) {
        String url =  "http://localhost:8080/author/" + authorId;
        executor.exchange(url, HttpMethod.DELETE);
    }

    @Then("The database should not have that Author")
    public void theDatabaseShouldBeEmpty() {
        assertThat(authorsRepository.count(), is(0L));
    }
}
