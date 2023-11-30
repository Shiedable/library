package eu.deltasource.training.library.sales;

import eu.deltasource.training.library.EntityManagerHelper;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.model.Sale;
import eu.deltasource.training.library.repository.BooksRepository;
import eu.deltasource.training.library.repository.SalesRepository;
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

public class AddingSaleStepDefinition {

    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private BooksRepository booksRepository;
    private ResponseEntity<String> actualResponse;
    private ResponseEntity<String> expectedResponse;
    private String saleDate;
    private int quantity;
    private long bookId;
    private Book book;
    @Autowired
    private EntityManagerHelper entityManagerHelper;


    @Given("Valid Sale information")
    public void validSaleInformation() {
        saleDate = "2000-01-01";
        quantity = 100;
        bookId = 1;
        book = new Book(1,"test", LocalDate.parse("2000-01-01"), "TST", 10);
        booksRepository.save(book);
    }

    @When("adding a new Sale")
    public void addingANewSale() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  constructUrl();
        actualResponse = restTemplate.exchange(url, HttpMethod.POST, null, String.class);
    }

    @Then("The database should contain that same Sale with id {int}")
    public void theDatabaseShouldContainThatSameSaleWithId(long saleId) {
        List<Sale> l = (List<Sale>) salesRepository.findAll();
        Sale actual = salesRepository.findById(saleId).get();
        assertThat(actual.getQuantity(), is(quantity));
        assertThat(actual.getBook().toString(), is(book.toString()));
        assertThat(actual.getSaleDate(), is(LocalDate.parse(saleDate)));
    }

    @Given("Empty sale date")
    public void emptySaleDate() {
        saleDate = "empty";
        quantity = 100;
        bookId = 1;
        expectedResponse = new ResponseEntity<>("Date cannot be null/empty", HttpStatus.BAD_REQUEST);
    }

    @Then("We should be getting an appropriate message")
    public void weShouldBeGettingAnAppropriateMessage() {
        assertThat(actualResponse.getStatusCode(), is(expectedResponse.getStatusCode()));
        assertThat(actualResponse.getBody(), is(expectedResponse.getBody()));
    }

    @Given("No sale date")
    public void noSaleDate() {
        saleDate = "null";
        quantity = 100;
        bookId = 1;
        expectedResponse = new ResponseEntity<>("saleDate cannot be null/empty", HttpStatus.BAD_REQUEST);
    }

    @Given("Invalid sale date format")
    public void invalidSaleDateFormat() {
        saleDate = "01 01 01";
        quantity = 100;
        bookId = 1;
        expectedResponse = new ResponseEntity<>("Date format is invalid", HttpStatus.BAD_REQUEST);
    }

    @Given("Negative quantity")
    public void negativeQuantity() {
        saleDate = "2000-01-01";
        quantity = -100;
        bookId = 1;
        expectedResponse = new ResponseEntity<>("Number should be positive", HttpStatus.BAD_REQUEST);
    }

    private String constructUrl() {
        String ADD_SALE_BASE_URL = "http://localhost:8080/sale/add?";
        StringBuilder url = new StringBuilder(ADD_SALE_BASE_URL);
        String saleDatePart = constructSaleDate();
        if (saleDatePart != null) {
            url.append(saleDatePart);
        }
        url.append(constructQuantity());
        url.append(constructBookId());
        return url.toString();
    }

    private String constructSaleDate() {
        StringBuilder saleDateParam = new StringBuilder();
        if (saleDate.equals("empty")) {
            return saleDateParam.append("saleDate=&")
                    .toString();
        }
        if (saleDate.equals("null")) {
            return  null;
        }
        return saleDateParam.append("saleDate=")
                .append(saleDate)
                .append("&")
                .toString();
    }

    private String constructQuantity() {
        StringBuilder quantityParam = new StringBuilder();
        return quantityParam.append("quantity=")
                .append(quantity)
                .append("&")
                .toString();
    }

    private String constructBookId() {
        String bookIdParam = "bookId=" +
                bookId;
        return bookIdParam;
    }
}
