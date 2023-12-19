package eu.deltasource.training.library.steps.book;

import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.repository.BookRepository;
import eu.deltasource.training.library.steps.HttpRequestExecutor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpMethod;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GettingBookStepDefinition {

    private BookRepository bookRepository;
    private HttpRequestExecutor executor;

    public GettingBookStepDefinition(BookRepository bookRepository, HttpRequestExecutor executor) {
        this.bookRepository = bookRepository;
        this.executor = executor;
    }

    @Then("We should get all the data for that Book")
    public void weShouldGetAllTheDataForThatBook() {
        assertThat(executor.getResponse().getBody().contains("test"), is(true));
        assertThat(executor.getResponse().getBody().contains("TST"), is(true));
        assertThat(executor.getResponse().getBody().contains("2000-01-01"), is(true));
        assertThat(executor.getResponse().getBody().contains("10.5"), is(true));
    }

    @When("Attempting to get the information for all Books in the database")
    public void attemptingToGetTheInformationForAllBooksInTheDatabase() {
        String url =  "http://localhost:8080/book/get";
        executor.exchange(url, HttpMethod.GET);
    }

    @Then("We should get all the data for all the Books in the database")
    public void weShouldGetAllTheDataForAllTheBooksInTheDatabase() {
        assertThat(executor.getResponse().getBody().contains("test"), is(true));
        assertThat(executor.getResponse().getBody().contains("test1"), is(true));
        assertThat(executor.getResponse().getBody().contains("test2"), is(true));
    }

    @When("Attempting to get the information for Book {int}")
    public void attemptingToGetTheInformationForBook(int bookId) {
        String url =  "http://localhost:8080/book/" + bookId;
        executor.exchange(url, HttpMethod.GET);
    }

    @Given("The database has 3 Books")
    public void theDatabaseHas3Books() {
        bookRepository.save(new Book("test", "2000-01-01", "TST", 10.50));
        bookRepository.save(new Book("test1", "2000-02-02", "TST1", 10.50));
        bookRepository.save(new Book("test2", "2000-03-03", "TST2", 10.50));
    }
}
