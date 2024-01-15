package eu.deltasource.training.library.steps.adapter;

import eu.deltasource.training.library.steps.HttpRequestExecutor;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;

public class AdapterStepDefinition {

    @Value("${author.url}")
    private String authorApiUrl;
    @Value("${book.url}")
    private String bookApiUrl;
    private HttpRequestExecutor executor;
    private RestTemplate mockedRestTemplate;
    private String isbn;
    private String firstName;
    private String lastName;

    public AdapterStepDefinition(RestTemplate mockedRestTemplate, HttpRequestExecutor executor) {
        this.mockedRestTemplate =  mockedRestTemplate;
        this.executor = executor;
    }

    @Then("^Get Expected Book Data - (.*), (.*), (.*)$")
    public void getExpectedBookData(String title, String publicationDate, String isbn) {
        assertTrue(executor.getResponse().getBody().contains(title));
        assertTrue(executor.getResponse().getBody().contains(publicationDate));
        assertTrue(executor.getResponse().getBody().contains(isbn));
    }

    @And("^Get Expected Author Data - (.*), (.*)$")
    public void getExpectedAuthorData(String name, String birthDate) {
        assertTrue(executor.getResponse().getBody().contains(name));
        assertTrue(executor.getResponse().getBody().contains(birthDate));
    }

    @Given("^Book ISBN (.*)$")
    public void bookISBN(String isbn) throws IOException {
        this.isbn = isbn;
        byte[] fileBytes = Files.readAllBytes(Paths.get("src/test/resources/json/ValidData.json"));
        String adapterResponseJson = new String(fileBytes);
        Mockito.when(mockedRestTemplate.exchange(startsWith(bookApiUrl), eq(HttpMethod.GET), eq(null), eq(String.class)))
                .thenReturn(new ResponseEntity<>(adapterResponseJson, HttpStatus.OK));
        Mockito.when(mockedRestTemplate.exchange(startsWith(authorApiUrl), eq(HttpMethod.GET), eq(null), eq(String.class)))
                .thenReturn(new ResponseEntity<>("2000-02-02", HttpStatus.OK));
    }

    @Given("^Book isbn (.*)$")
    public void bookISBNTESTISBN(String isbn) {
        this.isbn = isbn;
        Mockito.when(mockedRestTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(String.class)))
                .thenReturn(new ResponseEntity<>("No book was found with isbn: " + isbn, HttpStatus.NOT_FOUND));
    }

    @When("Getting Book data from API")
    public void gettingBookDataFromAPI() {
        executor.exchange("http://localhost:8080/bookByIsbn?isbn=" + isbn, HttpMethod.POST);
    }

    @Given("Empty isbn")
    public void emptyIsbn() {
        isbn = "";
    }
}