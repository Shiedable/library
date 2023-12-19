package eu.deltasource.training.library.steps.book;

import eu.deltasource.training.library.DatabaseResetter;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.repository.BookRepository;
import eu.deltasource.training.library.steps.HttpRequestExecutor;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AddingBookStepDefinition {

    private BookRepository bookRepository;
    private HttpRequestExecutor executor;
    private DatabaseResetter databaseResetter;
    private String title;
    private String isbn;
    private String publicationDate;
    private double price;

    public AddingBookStepDefinition(BookRepository bookRepository,
                                    HttpRequestExecutor executor,
                                    DatabaseResetter databaseResetter) {
        this.bookRepository = bookRepository;
        this.executor = executor;
        this.databaseResetter = databaseResetter;
    }

    @Before
    public void initialize() {
        bookRepository.deleteAll();
        databaseResetter.clear("books");
    }

    @Given("^Title (.*), ISBN (.*), publication date (.*) and price (.*)$")
    public void validBookInformation(String title, String isbn, String publicationDate, double price) {
        this.title = title;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.price = price;
    }

    @When("adding a new Book")
    public void addingANewBook() {
        String url =  constructUrl();
        executor.exchange(url, HttpMethod.POST);
    }

    @Then("The database should contain that same Book with id {int}")
    public void theDatabaseShouldContainThatSameBookWithId(long bookId) {
        Book actual = bookRepository.findById(bookId).get();
        assertThat(actual.getTitle(), is(title));
        assertThat(actual.getIsbn(), is(isbn));
        assertThat(actual.getPublicationDate(), is(LocalDate.parse("2000-01-01")));
        assertThat(actual.getPrice(), is(price));
    }

    @Given("Null title")
    public void noTitle() {
        title = null;
        isbn = "TST";
        publicationDate = "2000-01-01";
        price = 10.50;
    }

    @Given("Null ISBN")
    public void noISBN() {
        title = "test";
        isbn = null;
        publicationDate = "2000-01-01";
        price = 10.50;
    }

    @Given("Null publication date")
    public void noPublicationDate() {
        title = "test";
        isbn = "TST";
        publicationDate = null;
        price = 10.50;
    }

    private String constructUrl() {
        UriComponentsBuilder url = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/book");
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
