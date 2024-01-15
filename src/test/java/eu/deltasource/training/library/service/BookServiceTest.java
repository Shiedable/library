package eu.deltasource.training.library.service;

import eu.deltasource.training.library.exceptions.*;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.repository.AuthorRepository;
import eu.deltasource.training.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookServiceTest {


    private BookService bookService;
    private BookRepository mockedBookRepository;

    @BeforeAll
    public void initialize() {
        mockedBookRepository = Mockito.mock(BookRepository.class);
        bookService = new BookService(mockedBookRepository, Mockito.mock(AuthorService.class), new RestTemplate());
    }

    @Test
    public void givenEmptyTitle_WhenAddingBook_ThenThrowInvalidBookException() {
        //Given
        String title = "";
        String publicationDateString = "2000-01-01";
        String isbn = "TEST";
        double price = 10.0;

        //When

        //Then
        assertThrows(InvalidBookException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenNullTitle_WhenAddingBook_ThenThrowInvalidBookException() {
        //Given
        String title = null;
        String publicationDateString = "2000-01-01";
        String isbn = "TEST";
        double price = 10.0;

        //When

        //Then
        assertThrows(InvalidBookException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenEmptyIsbn_WhenAddingBook_ThenThrowInvalidBookException() {
        //Given
        String title = "test";
        String publicationDateString = "2000-01-01";
        String isbn = "";
        double price = 10.0;

        //When

        //Then
        assertThrows(InvalidBookException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenNullIsbn_WhenAddingBook_ThenThrowInvalidBookException() {
        //Given
        String title = "test";
        String publicationDateString = "2000-01-01";
        String isbn = null;
        double price = 10.0;

        //When

        //Then
        assertThrows(InvalidBookException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenEmptyStringPublicationDate_WhenAddingBook_ThenThrowInvalidBookException() {
        //Given
        String title = "test";
        String publicationDateString = "";
        String isbn = "TEST";
        double price = 10.0;

        //When

        //Then
        assertThrows(InvalidBookException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenNullStringPublicationDate_WhenAddingBook_ThenThrowInvalidBookException() {
        //Given
        String title = "test";
        String publicationDateString = null;
        String isbn = "TEST";
        double price = 10.0;

        //When

        //Then
        assertThrows(InvalidBookException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenInvalidStringPublicationDate_WhenAddingBook_ThenThrowInvalidBookException() {
        //Given
        String title = "test";
        String publicationDateString = "iskam narga";
        String isbn = "TEST";
        double price = 10.0;

        //When

        //Then
        assertThrows(InvalidBookException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenNegativePrice_WhenAddingBook_ThenThrowInvalidBookException() {
        //Given
        String title = "test";
        String publicationDateString = "2000-01-01";
        String isbn = "TEST";
        double price = -10.0;

        //When

        //Then
        assertThrows(InvalidBookException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenNegativeIndex_WhenDeletingBook_ThenThrowEntityNotFoundException() {
        //Given
        long bookId = -1;

        //When

        //Then
        assertThrows(EntityNotFoundException.class, () -> bookService.deleteBookById(bookId));
    }

    @Test
    public void givenIndexThatDoesNotExist_WhenDeletingBook_ThenThrowIdNotFoundException() {
        //Given
        long bookId = 9999;

        //When

        //Then
        assertThrows(EntityNotFoundException.class, () -> bookService.deleteBookById(bookId));
    }

    @Test
    public void givenNegativeIndex_WhenUpdatingBook_ThenThrowEntityNotFoundException() {
        //Given
        String title = "test";
        String publicationDateString = "2000-01-01";
        String isbn = "TEST";
        double price = 10.0;
        long bookId = -1;

        //When

        //Then
        assertThrows(EntityNotFoundException.class,
                () -> bookService.updateBookById(bookId, title, publicationDateString, isbn, Optional.of(price)));
    }

    @Test
    public void givenIndexThatDoesNotExist_WhenUpdatingBook_ThenThrowEntityNotFoundException() {
        //Given
        String title = "test";
        String publicationDateString = "2000-01-01";
        String isbn = "TEST";
        double price = 10.0;
        long bookId = 9999;

        //When

        //Then
        assertThrows(EntityNotFoundException.class,
                () -> bookService.updateBookById(bookId, title, publicationDateString, isbn, Optional.of(price)));
    }

    @Test
    public void givenInvalidStringPublicationDate_WhenUpdatingBook_ThenInvalidBookException() {
        //Given
        String title = "test";
        String publicationDateString = "F.O.O.L. - Criminal";
        String isbn = "TEST";
        double price = 10.0;
        long bookId = 1;

        //When
        Mockito.when(mockedBookRepository.findById((long) 1)).thenReturn(Optional.of(new Book()));

        //Then
        assertThrows(InvalidBookException.class,
                () -> bookService.updateBookById(bookId, title, publicationDateString, isbn, Optional.of(price)));
    }

    @Test
    public void givenNegativePrice_WhenUpdatingBook_ThenThrowInvalidBookException() {
        //Given
        String title = "test";
        String publicationDateString = "2000-01-01";
        String isbn = "TEST";
        double price = -10.0;
        long bookId = 1;

        //When
        Mockito.when(mockedBookRepository.findById((long) 1)).thenReturn(Optional.of(new Book()));

        //Then
        assertThrows(InvalidBookException.class,
                () -> bookService.updateBookById(bookId, title, publicationDateString, isbn, Optional.of(price)));
    }

    @Test
    public void givenNegativeIndex_WhenGettingABook_ThenThrowEntityNotFoundException() {
        //Given
        long bookId = -1;

        //When

        //Then
        assertThrows(EntityNotFoundException.class, () -> bookService.getBookById(bookId));
    }

    @Test
    public void givenNonExistentIndex_WhenGettingABook_ThenThrowIdNotFoundException() {
        //Given
        long bookId = 9999;

        //When

        //Then
        assertThrows(EntityNotFoundException.class, () -> bookService.deleteBookById(bookId));
    }

    @Test
    public void givenEmptyRepository_WhenGettingAllBooks_ThenGetEmptyList() {
        //Given
        List<Book> emptyList = new ArrayList<>();

        //When
        Mockito.when(mockedBookRepository.findAll()).thenReturn(emptyList);

        //Then
        assertThat(bookService.getAllBooks().isEmpty(), is(true));
    }

    @Test
    public void givenRepositoryWithBooks_WhenGettingAllBooks_ThenGetListOfBooks() {
        //Given
        List<Book> bookList = new ArrayList<>();
        Book book1 = new Book("kniga", "2000-01-01", "KNG", 10.0);
        Book book2 = new Book("book", "2000-02-02", "BK", 15.59);
        Book book3 = new Book("KKNNIGGGA", "2000-03-03", "KKNNGGG", 20.99);
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);

        //When
        Mockito.when(mockedBookRepository.findAll()).thenReturn(bookList);

        //Then
        assertThat(bookService.getAllBooks().contains(book1), is(true));
        assertThat(bookService.getAllBooks().contains(book2), is(true));
        assertThat(bookService.getAllBooks().contains(book3), is(true));
    }
}
