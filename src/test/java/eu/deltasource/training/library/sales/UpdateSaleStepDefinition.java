package eu.deltasource.training.library.sales;

import eu.deltasource.training.library.EntityManagerHelper;
import eu.deltasource.training.library.model.Author;
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

public class UpdateSaleStepDefinition {

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

    @Given("Sale Date to be updated to 2022-02-02")
    public void saleDateToBeUpdatedTo() {
        saleDate = "2000-01-01";
        quantity = 100;
        bookId = 1;
        book = new Book(1,"test", LocalDate.parse("2000-01-01"), "TST", 10);
        booksRepository.save(book);
        salesRepository.save(new Sale(LocalDate.parse("2000-01-01"), 100, book));
    }

    @When("Attempting to update the information for Sale {int}")
    public void attemptingToUpdateTheInformationForSale(int saleId) {
        TestRestTemplate restTemplate = new TestRestTemplate();
        String url =  constructUrl(saleId);
        actualResponse = restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
    }

    @Then("The Sale should have been updated")
    public void theSaleShouldHaveBeenUpdated() {
        List <Sale> l = (List<Sale>) salesRepository.findAll();
        Sale actual = salesRepository.findById(1L).get();
        assertThat(actual.getQuantity(), is(quantity));
        assertThat(actual.getBook().toString(), is(book.toString()));
        assertThat(actual.getSaleDate(), is(LocalDate.parse(saleDate)));

    }

    @Given("Invalid sale date format to be updated")
    public void invalidSaleDateFormatToBeUpdated() {
        saleDate = "2000 text 01-01";
        quantity = 100;
        bookId = 1;
        book = new Book(1,"test", LocalDate.parse("2000-01-01"), "TST", 10);
        booksRepository.save(book);
        expectedResponse = new ResponseEntity<>("Date format is invalid", HttpStatus.BAD_REQUEST);
    }

    @Given("Quantity to be updated to {int}")
    public void quantityToBeUpdatedTo(int quantityUpdate) {
        saleDate = "2000-01-01";
        quantity = quantityUpdate;
        bookId = 1;
        book = new Book(1,"test", LocalDate.parse("2000-01-01"), "TST", 10);
    }

    @Given("Invalid quantity to be updated to {int}")
    public void invalidQuantityToBeUpdatedTo(int quantityUpdate) {
        saleDate = "2000-01-01";
        quantity = quantityUpdate;
        bookId = 1;
        book = new Book(1,"test", LocalDate.parse("2000-01-01"), "TST", 10);
        expectedResponse = new ResponseEntity<>("quantity should be positive", HttpStatus.BAD_REQUEST);
    }

    @Then("We should get an error message")
    public void weShouldGetAnErrorMessage() {
        assertThat(actualResponse.getStatusCode(), is(expectedResponse.getStatusCode()));
        assertThat(actualResponse.getBody(), is(expectedResponse.getBody()));
    }

    @Given("New book to be updated")
    public void newBookToBeUpdated() {
        saleDate = "2000-01-01";
        quantity = 100;
        bookId = 2;
        book = new Book("Java Book", LocalDate.parse("2022-02-02"), "JB", 15.20);
        booksRepository.save(book);
    }

    @Given("Empty Sale database")
    public void emptySaleDatabase() {
        saleDate = "2000-01-01";
        quantity = 100;
        bookId = 1;
        book = new Book("Java Book", LocalDate.parse("2022-02-02"), "JB", 15.20);
        expectedResponse = new ResponseEntity<>("Sale with such ID does not exist", HttpStatus.NOT_FOUND);
    }

    @Given("The Sale database is empty")
    public void theSaleDatabaseIsEmpty() {
        saleDate = "2000-01-01";
        quantity = 100;
        bookId = 1;
        book = new Book("Java Book", LocalDate.parse("2022-02-02"), "JB", 15.20);
        expectedResponse = new ResponseEntity<>("Entity ID should be positive", HttpStatus.BAD_REQUEST);
    }

    private String constructUrl(int saleId) {
        String ADD_SALE_BASE_URL = "http://localhost:8080/sale/update/" + saleId + "?";
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
