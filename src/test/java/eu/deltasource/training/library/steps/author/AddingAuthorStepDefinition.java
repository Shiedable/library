package eu.deltasource.training.library.steps.author;

import eu.deltasource.training.library.DatabaseResetter;
import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.repository.AuthorRepository;
import eu.deltasource.training.library.steps.HttpRequestExecutor;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AddingAuthorStepDefinition {

    private AuthorRepository authorsRepository;
    private HttpRequestExecutor executor;
    private DatabaseResetter databaseResetter;
    private String firstName;
    private String lastName;
    private String birthDate;

    public AddingAuthorStepDefinition(AuthorRepository authorsRepository,
                                      HttpRequestExecutor executor,
                                      DatabaseResetter databaseResetter) {
        this.authorsRepository = authorsRepository;
        this.executor = executor;
        this.databaseResetter = databaseResetter;
    }

    @Before
    public void initialize() {
        authorsRepository.deleteAll();
        databaseResetter.clear("authors");
    }

    @Given("^First Name (.*) Last Name (.*) and Birth Date (.*)$")
    public void validAuthorData(String firstName, String lastName, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    @When("^adding a new Author$")
    public void addingNewAuthor() {
        String url =  constructUrl();
        executor.exchange(url, HttpMethod.POST);
    }

    @Then("^The database should contain that same Author with id (.*)$")
    public void databaseShouldContainAuthor(long authorId) {
        Author actual = authorsRepository.findById(authorId).get();
        assertThat(actual.getFirstName(), is(firstName));
        assertThat(actual.getLastName(), is(lastName));
        assertThat(actual.getBirthDate(), is(LocalDate.parse(birthDate)));
    }

    @Then("^We should be getting a response with code (.*) and (.*) error message$")
    public void weShouldBeGettingAResponseWithCodeErrorCodeAndMessageErrorMessage(String errorCode, String errorMessage) {
        assertThat(executor.getResponse().getStatusCode().toString(), is(errorCode));
        assertThat(executor.getResponse().getBody(), is(errorMessage));
    }

    @Given("^Invalid name (.*) (.*) and birth date (.*)$")
    public void firstnameLastNameAndBirthDate(String firstName, String lastName, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    @Given("^Null First Name$")
    public void nullFirstName() {
        this.firstName = null;
        this.lastName = "testov";
        this.birthDate = "2000-01-01";
    }

    @Given("^Null Last Name$")
    public void nullLastName() {
        this.firstName = "test";
        this.lastName = null;
        this.birthDate = "2000-01-01";
    }

    @Given("^Null Birth Date$")
    public void nullBirthDate() {
        this.firstName = "test";
        this.lastName = "testov";
        this.birthDate = null;
    }

    private String constructUrl() {
        UriComponentsBuilder url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/author");
        if (firstName != null) {
            url.queryParam("firstName", firstName);
        }
        if (lastName != null) {
            url.queryParam("lastName", lastName);
        }
        if (birthDate != null) {
            url.queryParam("birthDate", birthDate);
        }
        return url.build()
                .encode()
                .toUriString();
    }
}
