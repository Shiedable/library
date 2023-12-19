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

public class DeletingBookStepDefinition {

    private HttpRequestExecutor executor;
    private BookRepository bookRepository;

    public DeletingBookStepDefinition(HttpRequestExecutor executor, BookRepository bookRepository) {
        this.executor = executor;
        this.bookRepository = bookRepository;
    }

    @Given("The database has a Book")
    public void theDatabaseHasABook() {
        bookRepository.save(new Book("test", "2000-01-01", "TST", 10.50));
    }

    @When("Attempting to delete Book {int}")
    public void attemptingToDeleteBook(int bookId) {
        String url =  "http://localhost:8080/book/" + bookId;
        executor.exchange(url, HttpMethod.DELETE);
    }

    @Then("The database should not have that Book")
    public void theDatabaseShouldNotHaveThatBook() {
        assertThat(bookRepository.count(), is(0L));
    }
}
