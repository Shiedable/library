package eu.deltasource.training.library.steps.adapter;

import eu.deltasource.training.library.steps.HttpRequestExecutor;
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
import java.util.ArrayList;

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

    @Then("Get Expected Book and Author Data")
    public void getExpectedBookData() {
        assertTrue(executor.getResponse().getBody().contains("title"));
        assertTrue(executor.getResponse().getBody().contains("2000-01-01"));
        assertTrue(executor.getResponse().getBody().contains("133TEST9018144"));
        assertTrue(executor.getResponse().getBody().contains("author name"));
    }

    @Given("^Book ISBN 133TEST9018144$")
    public void bookISBN() throws IOException {
        this.isbn = "133TEST9018144";
        byte[] fileBytes = Files.readAllBytes(Paths.get("src/test/resources/json/ValidData.json"));
        String adapterResponseJson = new String(fileBytes);
        Mockito.when(mockedRestTemplate.exchange(startsWith(bookApiUrl), eq(HttpMethod.GET), eq(null), eq(String.class)))
                .thenReturn(new ResponseEntity<>(adapterResponseJson, HttpStatus.OK));
        Mockito.when(mockedRestTemplate.exchange(startsWith(authorApiUrl), eq(HttpMethod.GET), eq(null), eq(String.class)))
                .thenReturn(new ResponseEntity<>("2000-01-01", HttpStatus.OK));
    }

    @Given("Book ISBN TESTISBN123")
    public void bookISBNTESTISBN() {
        this.isbn = "TESTISBN123";
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
