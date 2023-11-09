package eu.deltasource.training.library.authors;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;


public class AddingAuthorStepDefinition {
    @Given("Having no authors saved")
    public void havingNoAuthorsSaved() {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("adding a new Author with valid Muncho, Veliki and {double}.{int}")
    public void adding_a_new_author_with_valid_muncho_veliki_and(Double double1, Integer int1) {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("The repository should contain that same Author")
    public void the_repository_should_contain_that_same_author() {
        // Write code here that turns the phrase above into concrete actions
    }
}
