package eu.deltasource.training.library.book;

import eu.deltasource.training.library.EntityManagerHelper;
import eu.deltasource.training.library.model.Book;
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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UpdatingBookStepDefinition {
    @Autowired
    private BooksRepository booksRepository;

    private ResponseEntity<String> actualResponse;
    private ResponseEntity<String> expectedResponse;
    private String title;
    private String isbn;
    private String publicationDate;
    private Double price;
    @Autowired
    private EntityManagerHelper entityManagerHelper;

    @Given("Book title to be updated to Java Book")
    public void bookTitleToBeUpdatedToJavaBook() {
        title = "Java Book";
        isbn = "TST";
        publicationDate = "2000-01-01";
        price = (double) 10.5;
        booksRepository.deleteAll();
        entityManagerHelper.resetTableId("books");
        booksRepository.save(new Book("test", LocalDate.parse("2000-01-01"), "TST", 10.50));
    }

    @Given("Invalid publication date format")
    public void invalidPublicationDateFormatToBeUpdated() {
        title = "test";
        isbn = "TST";
        publicationDate = "2000 01 01";
        price = 10.50;
        expectedResponse = new ResponseEntity<>("Date format is invalid", HttpStatus.BAD_REQUEST);
    }

    @When("Attempting to update the information for Book {int}")
    public void attemptingToUpdateTheInformationForBook(int bookId) {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  constructUrl(bookId);
        List <Book> l = (List<Book>) booksRepository.findAll();
        actualResponse = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
    }

    @Then("The Book should have been updated")
    public void theBookShouldHaveBeenUpdated() {
        Book actual = booksRepository.findById(1L).get();
        assertThat(actual.getTitle(), is(title));
        assertThat(actual.getIsbn(), is(isbn));
        assertThat(actual.getPublicationDate(), is(LocalDate.parse(publicationDate)));
        assertThat(actual.getPrice(), is(price));
    }

    @Given("ISBN to be updated to DASDAS")
    public void isbnToBeUpdatedToDASDAS() {
        title = "Java Book";
        isbn = "DASDAS";
        publicationDate = "2000-01-01";
        price = (double) 10.5;
    }

    @Given("Publication Date to be updated to 2022 02 02")
    public void publicationDateToBeUpdatedTo() {
        title = "Java Book";
        isbn = "DASDAS";
        publicationDate = "2022-02-02";
        price = (double) 10.5;
    }

    @Given("Price to be updated to {double}")
    public void priceToBeUpdatedTo(double price) {
        title = "Java Book";
        isbn = "TST";
        publicationDate = "2000-01-01";
        this.price = price;
    }

    @Given("Empty Book database")
    public void emptyBookDatabase() {
        title = "test";
        isbn = "TST";
        publicationDate = "2000-01-01";
        price = 10.50;
        expectedResponse = new ResponseEntity<>("Book with such ID does not exist", HttpStatus.NOT_FOUND);
    }


    @Then("We should be getting an error")
    public void weShouldBeGettingAnError() {
        assertThat(actualResponse.getStatusCode(), is(expectedResponse.getStatusCode()));
        assertThat(actualResponse.getBody(), is(expectedResponse.getBody()));
    }

    @Given("The Book database is empty")
    public void theBookDatabaseIsEmpty() {
        title = "test";
        isbn = "TST";
        publicationDate = "2000-01-01";
        price = 10.50;
        expectedResponse = new ResponseEntity<>("Entity ID should be positive", HttpStatus.BAD_REQUEST);
    }

    @Given("Negative price")
    public void negativePrice() {
        title = "Java Book";
        isbn = "TST";
        publicationDate = "2000-01-01";
        price = -10.50;
        expectedResponse = new ResponseEntity<>("price should be positive", HttpStatus.BAD_REQUEST);
    }

    private String constructUrl(int bookId) {
        String ADD_BOOK_BASE_URL = "http://localhost:8080/book/update/" + bookId + "?";
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
            url.append("price=").append(price);
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
            return publicationDateParam.append("publicationDate=")
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
