package eu.deltasource.training.library.steps.book;

import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.repository.BookRepository;
import eu.deltasource.training.library.steps.HttpRequestExecutor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UpdatingBookStepDefinition {

    private BookRepository bookRepository;
    private HttpRequestExecutor executor;
    private String title;
    private String titleExpected;
    private String isbn;
    private String isbnExpected;
    private String publicationDate;
    private String publicationDateExpected;
    private Double price;
    private Double priceExpected;

    public UpdatingBookStepDefinition(BookRepository bookRepository, HttpRequestExecutor executor) {
        this.bookRepository = bookRepository;
        this.executor = executor;
    }

    @Given("Book title to be updated to Java Book")
    public void bookTitleToBeUpdatedToJavaBook() {
        title = "JavaBook";
        isbn = null;
        publicationDate =  null;
        price = 10.5;
        titleExpected = "JavaBook";
        isbnExpected = "TST";
        publicationDateExpected = "2000-01-01";
        priceExpected = 10.5;
        bookRepository.save(new Book("test", "2000-01-01", "TST", 10.50));
    }

    @Given("Invalid publication date format")
    public void invalidPublicationDateFormatToBeUpdated() {
        title = "test";
        isbn = "TST";
        publicationDate = "02-02-2000";
        price = 10.50;
        bookRepository.save(new Book("test", "2000-01-01", "TST", 10.50));
    }

    @When("Attempting to update the information for Book {int}")
    public void attemptingToUpdateTheInformationForBook(int bookId) {
        String url =  constructUrl(bookId);
        executor.exchange(url, HttpMethod.PUT);
    }

    @Then("The Book should have been updated")
    public void theBookShouldHaveBeenUpdated() {
        Book actual = bookRepository.findById(1L).get();
        assertThat(actual.getTitle(), is(titleExpected));
        assertThat(actual.getIsbn(), is(isbnExpected));
        assertThat(actual.getPublicationDate(), is(LocalDate.parse(publicationDateExpected)));
        assertThat(actual.getPrice(), is(priceExpected));
    }

    @Given("ISBN to be updated to DASDAS")
    public void isbnToBeUpdatedToDASDAS() {
        title = null;
        isbn = "DASDAS";
        publicationDate = null;
        price = 10.5;
        titleExpected = "test";
        isbnExpected = "DASDAS";
        publicationDateExpected = "2000-01-01";
        priceExpected = 10.5;
        bookRepository.save(new Book("test", "2000-01-01", "TST", 10.50));
    }

    @Given("Publication Date to be updated to 2022 02 02")
    public void publicationDateToBeUpdatedTo() {
        title = null;
        isbn = null;
        publicationDate = "2000-02-02";
        price = 10.5;
        titleExpected = "test";
        isbnExpected = "TST";
        publicationDateExpected = "2000-02-02";
        priceExpected = 10.5;
        bookRepository.save(new Book("test", "2000-01-01", "TST", 10.50));
    }

    @Given("Price to be updated to {double}")
    public void priceToBeUpdatedTo(double price) {
        title = null;
        isbn = null;
        publicationDate = null;
        this.price = price;
        titleExpected = "test";
        isbnExpected = "TST";
        publicationDateExpected = "2000-01-01";
        priceExpected = price;
        bookRepository.save(new Book("test", "2000-01-01", "TST", 10.50));
    }

    @Given("Negative price")
    public void negativePrice() {
        title = "test";
        isbn = "TST";
        publicationDate = "2000-01-01";
        price = -10.50;
        bookRepository.save(new Book("test", "2000-01-01", "TST", 10.50));
    }

    private String constructUrl(int bookId) {
        UriComponentsBuilder url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/book/" + bookId);
        if (title != null) {
            url.queryParam("title", title);
        }
        if (isbn != null) {
            url.queryParam("isbn", isbn);
        }
        if (publicationDate != null) {
            url.queryParam("publicationDate", publicationDate);
        }
        return url.queryParam("price", price)
                .build()
                .encode()
                .toUriString();
    }
}
