package eu.deltasource.training.library.authors;

import eu.deltasource.training.library.controller.AuthorController;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class DeletingAuthorStepDefinition {

    @Autowired
    private AuthorController authorController;

    private ResponseEntity<String> actual;

    @Given("The database has an Author")
    public void theDatabaseHasAuthor() {
        authorController.addAuthor("test", "testov","2000-01-01");
    }

    @When("Attempting to delete that Author")
    public void attemptingToDeleteThatAuthor() {
        authorController.deleteAuthorById(0);
        actual = authorController.getAllAuthors();
    }

    @Then("The database should be empty")
    public void theDatabaseShouldBeEmpty() {
        Assertions.assertTrue(actual.toString().contains("[]"));
    }

    @When("Attempting to delete an Author that does not exist")
    public void attemptingToDeleteAnAuthorThatDoesNotExist() {
        actual = authorController.deleteAuthorById(5);
    }

    @Then("Then an error should occur with a response for Author not existing")
    public void thenAnErrorShouldOccurWithAResponseForAuthorNotExisting() {
        Assertions.assertTrue(actual.toString().contains("Author with such ID does not exist"));
    }
}
