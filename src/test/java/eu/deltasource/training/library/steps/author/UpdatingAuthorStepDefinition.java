package eu.deltasource.training.library.steps.author;

import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.repository.AuthorRepository;
import eu.deltasource.training.library.steps.HttpRequestExecutor;
import org.springframework.http.HttpMethod;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UpdatingAuthorStepDefinition {

    private AuthorRepository authorsRepository;
    private HttpRequestExecutor executor;
    private String firstName;
    private String firstNameExpected;
    private String lastName;
    private String lastNameExpected;
    private String birthDate;
    private String birthDateExpected;

    public UpdatingAuthorStepDefinition(AuthorRepository authorsRepository, HttpRequestExecutor executor) {
        this.authorsRepository = authorsRepository;
        this.executor = executor;
    }

    @When("Attempting to update the information for Author {int}")
    public void attemptingToUpdateTheInformationForAuthor(Integer authorId) {
        String url =  constructUrl(authorId);
        executor.exchange(url, HttpMethod.PUT);
    }

    @Then("The Author should have been updated")
    public void theAuthorShouldHaveBeenUpdated() {
        Author actual = authorsRepository.findById(1L).get();
        assertThat(actual.getFirstName(), is(firstNameExpected));
        assertThat(actual.getLastName(), is(lastNameExpected));
        assertThat(actual.getBirthDate(), is(LocalDate.parse(birthDateExpected)));
    }

    private String constructUrl(Integer authorId) {
        UriComponentsBuilder url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/author/" + authorId);
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

    @Given("First Name to be updated to Chocho")
    public void firstNameToBeUpdatedToChocho() {
        firstName = "Chocho";
        lastName = null;
        birthDate = null;
        firstNameExpected = "Chocho";
        lastNameExpected = "testov";
        birthDateExpected = "2000-01-01";
        authorsRepository.save(new Author("test", "testov", "2000-01-01"));
    }

    @Given("Last Name to be updated to Chochkovich")
    public void lastNameToBeUpdatedToChochkovich() {
        firstName = null;
        lastName = "Chochkovich";
        birthDate = null;
        firstNameExpected = "test";
        lastNameExpected = "Chochkovich";
        birthDateExpected = "2000-01-01";
        authorsRepository.save(new Author("test", "testov", "2000-01-01"));
    }

    @Given("Birth Date to be updated to 2022 02 02")
    public void birthDateToBeUpdatedTo() {
        firstName = null;
        lastName = null;
        birthDate = "2022-02-02";
        firstNameExpected = "test";
        lastNameExpected = "testov";
        birthDateExpected = "2022-02-02";
        authorsRepository.save(new Author("test", "testov", "2000-01-01"));
    }

    @Given("Invalid birthdate format")
    public void invalidBirthdateFormat() {
        this.firstName = null;
        this.lastName = null;
        this.birthDate = "20-12-2000";
        authorsRepository.save(new Author("test", "testov", "2000-01-01"));
    }
}
