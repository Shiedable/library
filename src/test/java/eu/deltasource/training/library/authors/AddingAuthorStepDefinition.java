package eu.deltasource.training.library.authors;

import eu.deltasource.training.library.controller.AuthorController;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class AddingAuthorStepDefinition {

    @Autowired
    private AuthorController authorController;

    private ResponseEntity<String> actual;
    private ResponseEntity<String> expected;

    @Given("Having no authors saved in the database")
    public void havingNoAuthorsSaved() {
        expected = ResponseEntity.ok().build();
    }

    @When("^adding a new Author with valid (.*), (.*) and (.*)$")
    public void addingNewAuthor(String firstName, String lastName, String birthDate) {
        actual = authorController.addAuthor(firstName, lastName, birthDate);
    }

    @Then("The database should contain that same Author")
    public void repositoryShouldContainAuthor() {
        Assertions.assertEquals(expected, actual);
    }

    @When("adding a new Author with empty first name")
    public void addingANewAuthorWithEmptyFirstName() {
        actual = authorController.addAuthor("","test", "2000-01-01");
    }

    @Then("An error should occur with response for an empty first name")
    public void anErrorShouldOccur() {
        Assertions.assertTrue(actual.toString().contains("Empty first name!"));
    }

    @When("adding a new Author with empty last name")
    public void addingANewAuthorWithEmptyLastName() {
        actual = authorController.addAuthor("test", "", "2000-01-01");
    }

    @Then("An error should occur with response for an empty last name")
    public void anErrorShouldOccurWithResponseForAnEmptyLastName() {
        Assertions.assertTrue(actual.toString().contains("Empty last name!"));
    }

    @When("adding a new Author with empty birth date")
    public void addingANewAuthorWithEmptyBirthDate() {
        actual = authorController.addAuthor("test", "test", "");
    }

    @Then("An error should occur with response for an empty birth date")
    public void anErrorShouldOccurWithResponseForAnEmptyBirthDate() {
        Assertions.assertTrue(actual.toString().contains("Birth date is empty!"));
    }
}
