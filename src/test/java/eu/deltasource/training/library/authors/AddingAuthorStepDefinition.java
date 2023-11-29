package eu.deltasource.training.library.authors;

import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.repository.AuthorsRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AddingAuthorStepDefinition {

    @Autowired
    private DataSource src;
    @LocalServerPort
    private int p;

    @Autowired
    private AuthorsRepository authorsRepository;

    private ResponseEntity<String> actualResponse;
    private ResponseEntity<String> expectedResponse;
    private String firstName;
    private String lastName;
    private String birthDate;

    @Given("^Valid (.*), (.*) and (.*)$")
    public void validAuthorData(String firstName, String lastName, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    @When("^adding a new Author$")
    public void addingNewAuthor() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  constructUrl();
        actualResponse = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
    }

    @Then("^The database should contain that same Author with id (.*)$")
    public void databaseShouldContainAuthor(long id) {
        Author actual = authorsRepository.findById(id).get();
        assertThat(actual.getFirstName(), is(firstName));
        assertThat(actual.getLastName(), is(lastName));
        assertThat(actual.getBirthDate(), is(LocalDate.parse(birthDate)));
    }

    @Given("^Invalid (.*) and (.*)$")
    public void invalidFirstNameAndLastName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = "2000-01-01";
        expectedResponse = new ResponseEntity<>("String cannot be null/empty", HttpStatus.BAD_REQUEST);
    }

    @Given("^Invalid birthdate - (.*)$")
    public void invalidBirthDate(String birthDate) {
        this.firstName = "test";
        this.lastName = "test";
        this.birthDate = birthDate;
        expectedResponse = new ResponseEntity<>("Date cannot be null/empty", HttpStatus.BAD_REQUEST);
    }

    @Given("Invalid birthDate format")
    public void invalidBirthDateFormat() {
        this.firstName = "test";
        this.lastName = "test";
        this.birthDate = "20-12-2000";
        expectedResponse = new ResponseEntity<>("Date format is invalid", HttpStatus.BAD_REQUEST);
    }

    @Then("^We should be getting an appropriate response$")
    public void weShouldBeGettingAnAppropriateResponse() {
        assertThat(actualResponse.getStatusCode(), is(expectedResponse.getStatusCode()));
        assertThat(actualResponse.getBody(), is(expectedResponse.getBody()));
    }



    private String constructUrl() {
        String ADD_AUTHOR_BASE_URL = "http://localhost:8080/author/add?";
        StringBuilder url = new StringBuilder(ADD_AUTHOR_BASE_URL);
        String firstNamePart = constructFirstName();
        if (firstNamePart != null) {
            url.append(firstNamePart);
        }
        String lastNamePart = constructLastName();
        if (lastNamePart != null) {
            url.append(lastNamePart);
        }
        String birthDatePart = constructBirthDate();
        if (birthDatePart != null) {
            url.append(birthDatePart);
        }
        return url.toString();
    }

    private String constructFirstName() {
        StringBuilder firstNameParam = new StringBuilder();
        if (firstName.equals("empty")) {
            return firstNameParam.append("firstName=&")
                    .toString();
        }
        if (firstName.equals("null")) {
            return  null;
        }
        return firstNameParam.append("firstName=")
                .append(firstName)
                .append("&")
                .toString();
    }

    private String constructLastName() {
        StringBuilder lastNameParam = new StringBuilder();
        if (lastName.equals("empty")) {
            return lastNameParam.append("lastName=&")
                    .toString();
        }
        if (lastName.equals("null")) {
            return  null;
        }
        return lastNameParam.append("lastName=")
                .append(lastName)
                .append("&")
                .toString();
    }

    private String constructBirthDate() {
        StringBuilder birthDateParam = new StringBuilder();
        if (birthDate.equals("empty")) {
            return birthDateParam.append("birthDate=")
                    .toString();
        }
        if (birthDate.equals("null")) {
            return null;
        }
        return birthDateParam.append("birthDate=")
                .append(birthDate)
                .append("&")
                .toString();
    }
}
