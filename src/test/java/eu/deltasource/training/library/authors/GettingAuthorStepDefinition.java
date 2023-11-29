package eu.deltasource.training.library.authors;

import eu.deltasource.training.library.repository.AuthorsRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class GettingAuthorStepDefinition {

    @Autowired
    private AuthorsRepository authorsRepository;
    private ResponseEntity<String> actualResponse;
    private ResponseEntity<String> expectedResponse;

    @When("Attempting to get the information for an Author")
    public void attempting_to_get_the_information_for_an_author() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  "http://localhost:8080/author/delete/1";
        actualResponse = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }

    @Then("We should get all the data for that Author")
    public void weShouldGetAllTheDataForThatAuthor() {

    }
}
