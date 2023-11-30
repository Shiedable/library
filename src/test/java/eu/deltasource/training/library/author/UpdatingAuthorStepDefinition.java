package eu.deltasource.training.library.author;

import eu.deltasource.training.library.EntityManagerHelper;
import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.repository.AuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UpdatingAuthorStepDefinition {


    @Autowired
    private AuthorsRepository authorsRepository;

    private ResponseEntity<String> actualResponse;
    private ResponseEntity<String> expectedResponse;
    private String firstName;
    private String lastName;
    private String birthDate;
    @Autowired
    private EntityManagerHelper entityManagerHelper;


    @When("Attempting to update the information for Author {int}")
    public void attemptingToUpdateTheInformationForAuthor(Integer authorId) {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  constructUrl(authorId);
        actualResponse = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
    }

    @Then("The Author should have been updated")
    public void theAuthorShouldHaveBeenUpdated() {
        Author actual = authorsRepository.findById(1L).get();
        assertThat(actual.getFirstName(), is(firstName));
        assertThat(actual.getLastName(), is(lastName));
        assertThat(actual.getBirthDate(), is(LocalDate.parse(birthDate)));
        authorsRepository.deleteAll();
        entityManagerHelper.resetTableId("authors");
    }

    private String constructUrl(Integer authorId) {
        String UPDATE_AUTHOR_BASE_URL = "http://localhost:8080/author/update/" + authorId + "?";
        StringBuilder url = new StringBuilder(UPDATE_AUTHOR_BASE_URL);
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

    @Given("First Name to be updated to Chocho")
    public void firstNameToBeUpdatedToChocho() {
        firstName = "Chocho";
        lastName = "testov";
        birthDate = "2000-01-01";
        authorsRepository.save(new Author("test", "testov", LocalDate.parse("2000-01-01")));
    }

    @Given("Last Name to be updated to Chochkovich")
    public void lastNameToBeUpdatedToChochkovich() {
        firstName = "test";
        lastName = "Chochkovich";
        birthDate = "2000-01-01";
        authorsRepository.save(new Author("test", "testov", LocalDate.parse("2000-01-01")));
    }

    @Given("Birth Date to be updated to 2022 02 02")
    public void birthDateToBeUpdatedTo() {
        firstName = "test";
        lastName = "testov";
        birthDate = "2022-02-02";
        authorsRepository.save(new Author("test", "testov", LocalDate.parse("2000-01-01")));
    }

    @Then("We should be getting an error message")
    public void weShouldBeGettingEntityIDErrorMessage() {
        assertThat(actualResponse.getStatusCode(), is(expectedResponse.getStatusCode()));
        assertThat(actualResponse.getBody(), is(expectedResponse.getBody()));
    }

    @Given("Empty Author database")
    public void emptyAuthorDatabase() {
        firstName = "test";
        lastName = "test";
        birthDate = "2022-02-02";
        expectedResponse = new ResponseEntity<>("Author with such ID does not exist", HttpStatus.NOT_FOUND);
    }

    @Given("The Author database is empty")
    public void theAuthorDatabaseIsEmpty() {
        firstName = "test";
        lastName = "test";
        birthDate = "2022-02-02";
        expectedResponse = new ResponseEntity<>("Entity ID should be positive", HttpStatus.BAD_REQUEST);
    }

    @Given("Invalid birthdate format")
    public void invalidBirthdateFormat() {
        authorsRepository.save(new Author("test", "testov", LocalDate.parse("2000-01-01")));
        this.firstName = "test";
        this.lastName = "testov";
        this.birthDate = "20-12-2000";
        expectedResponse = new ResponseEntity<>("Date format is invalid", HttpStatus.BAD_REQUEST);
    }
}
