package eu.deltasource.training.library.book;

import eu.deltasource.training.library.EntityManagerHelper;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.repository.AuthorsRepository;
import eu.deltasource.training.library.repository.BooksRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AddingBookStepDefinition {

    @Autowired
    private BooksRepository booksRepository;

    private ResponseEntity<String> actualResponse;
    private ResponseEntity<String> expectedResponse;
    private String title;
    private String isbn;
    private String publicationDate;
    private double price;
    @Autowired
    private EntityManagerHelper entityManagerHelper;

    @Given("Valid book information")
    public void validBookInformation() {
        title = "test";
        isbn = "TST";
        publicationDate = "2000-01-01";
        price = 10.50;
    }

    @When("adding a new Book")
    public void addingANewBook() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  constructUrl();
        actualResponse = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
    }

    @Then("The database should contain that same Book with id {int}")
    public void theDatabaseShouldContainThatSameBookWithId(long bookId) {
        Book actual = booksRepository.findById(bookId).get();
        assertThat(actual.getTitle(), is(title));
        assertThat(actual.getIsbn(), is(isbn));
        assertThat(actual.getPublicationDate(), is(LocalDate.parse("2000-01-01")));
        assertThat(actual.getPrice(), is(price));
    }

    @Given("Invalid publication date format to be updated")
    public void invalidPublicationDateFormat() {
        title = "test";
        isbn = "TST";
        publicationDate = "2000 01 01";
        price = 10.50;
        expectedResponse = new ResponseEntity<>("Date format is invalid", HttpStatus.BAD_REQUEST);
    }

    @Given("Empty title")
    public void emptyTitle() {
        title = "empty";
        isbn = "TST";
        publicationDate = "2000-01-01";
        price = 10.50;
        expectedResponse = new ResponseEntity<>("String cannot be null/empty", HttpStatus.BAD_REQUEST);
    }

    @Then("We should be getting an appropriate error")
    public void weShouldBeGettingAnAppropriateError() {
        assertThat(actualResponse.getStatusCode(), is(expectedResponse.getStatusCode()));
        assertThat(actualResponse.getBody(), is(expectedResponse.getBody()));
    }

    @Given("No title")
    public void noTitle() {
        title = "null";
        isbn = "TST";
        publicationDate = "2000-01-01";
        price = 10.50;
        expectedResponse = new ResponseEntity<>("title cannot be null/empty", HttpStatus.BAD_REQUEST);
    }

    @Given("Empty ISBN")
    public void emptyISBN() {
        title = "test";
        isbn = "empty";
        publicationDate = "2000-01-01";
        price = 10.50;
        expectedResponse = new ResponseEntity<>("String cannot be null/empty", HttpStatus.BAD_REQUEST);
    }

    @Given("No ISBN")
    public void noISBN() {
        title = "test";
        isbn = "null";
        publicationDate = "2000-01-01";
        price = 10.50;
        expectedResponse = new ResponseEntity<>("isbn cannot be null/empty", HttpStatus.BAD_REQUEST);
    }

    @Given("Empty publication date")
    public void emptyPublicationDate() {
        title = "test";
        isbn = "TST";
        publicationDate = "empty";
        price = 10.50;
        expectedResponse = new ResponseEntity<>("Date cannot be null/empty", HttpStatus.BAD_REQUEST);
    }

    @Given("No publication date")
    public void noPublicationDate() {
        title = "test";
        isbn = "TST";
        publicationDate = "null";
        price = 10.50;
        expectedResponse = new ResponseEntity<>("publicationDate cannot be null/empty", HttpStatus.BAD_REQUEST);
    }

    @Given("Negative book price")
    public void negativeBookPrice() {
        title = "test";
        isbn = "TST";
        publicationDate = "2000-01-01";
        price = -10.50;
        expectedResponse = new ResponseEntity<>("Number should be positive", HttpStatus.BAD_REQUEST);
    }

    private String constructUrl() {
        String ADD_BOOK_BASE_URL = "http://localhost:8080/book/add?";
        StringBuilder url = new StringBuilder(ADD_BOOK_BASE_URL);
        String titlePart = constructTitle();
        if (titlePart != null) {
            url.append(titlePart);
        }
        String publicationDatePart = constructPublicationDate();
        if (publicationDatePart != null) {
            url.append(publicationDatePart);
        }
        String isbnPart = constructIsbn();
        if (isbnPart != null) {
            url.append(isbnPart);
        }
        url.append("price=");
        if (price != 0) {
            url.append(price);
        }
        return url.toString();
    }

    private String constructTitle() {
        StringBuilder titleParam = new StringBuilder();
        if (title.equals("empty")) {
            return titleParam.append("title=&")
                    .toString();
        }
        if (title.equals("null")) {
            return  null;
        }
        return titleParam.append("title=")
                .append(title)
                .append("&")
                .toString();
    }

    private String constructIsbn() {
        StringBuilder isbnParam = new StringBuilder();
        if (isbn.equals("empty")) {
            return isbnParam.append("isbn=&")
                    .toString();
        }
        if (isbn.equals("null")) {
            return  null;
        }
        return isbnParam.append("isbn=")
                .append(isbn)
                .append("&")
                .toString();
    }

    private String constructPublicationDate() {
        StringBuilder publicationDateParam = new StringBuilder();
        if (publicationDate.equals("empty")) {
            return publicationDateParam.append("publicationDate=&")
                    .toString();
        }
        if (publicationDate.equals("null")) {
            return null;
        }
        return publicationDateParam.append("publicationDate=")
                .append(publicationDate)
                .append("&")
                .toString();
    }
}
