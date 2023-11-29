package eu.deltasource.training.library.service;

import eu.deltasource.training.library.exceptions.*;
import eu.deltasource.training.library.model.Book;
import eu.deltasource.training.library.repository.BooksRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookServiceTest {


    private static BookService bookService;
    private static BooksRepository mockedBookRepository;

    @BeforeAll
    public static void initialize() {
        mockedBookRepository = Mockito.mock(BooksRepository.class);
        bookService = new BookService(mockedBookRepository);
    }


    //CREATE RELATED TESTS
    @Test
    public void givenEmptyTitle_WhenAddingBook_ThenThrowInvalidStringException() {
        //Given
        String title = "";
        String publicationDateString = "2000-01-01";
        String isbn = "TEST";
        double price = 10.0;

        //When

        //Then
        assertThrows(InvalidStringException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenNullTitle_WhenAddingBook_ThenThrowInvalidStringException() {
        //Given
        String title = null;
        String publicationDateString = "2000-01-01";
        String isbn = "TEST";
        double price = 10.0;

        //When

        //Then
        assertThrows(InvalidStringException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenEmptyIsbn_WhenAddingBook_ThenThrowInvalidStringException() {
        //Given
        String title = "test";
        String publicationDateString = "2000-01-01";
        String isbn = "";
        double price = 10.0;

        //When

        //Then
        assertThrows(InvalidStringException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenNullIsbn_WhenAddingBook_ThenThrowInvalidStringException() {
        //Given
        String title = "test";
        String publicationDateString = "2000-01-01";
        String isbn = null;
        double price = 10.0;

        //When

        //Then
        assertThrows(InvalidStringException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenEmptyStringPublicationDate_WhenAddingBook_ThenThrowInvalidDateException() {
        //Given
        String title = "test";
        String publicationDateString = "";
        String isbn = "TEST";
        double price = 10.0;

        //When

        //Then
        assertThrows(InvalidDateException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenNullStringPublicationDate_WhenAddingBook_ThenThrowInvalidDateException() {
        //Given
        String title = "test";
        String publicationDateString = null;
        String isbn = "TEST";
        double price = 10.0;

        //When

        //Then
        assertThrows(InvalidDateException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenInvalidStringPublicationDate_WhenAddingBook_ThenThrowInvalidDateException() {
        //Given
        String title = "test";
        String publicationDateString = "iskam narga";
        String isbn = "TEST";
        double price = 10.0;

        //When

        //Then
        assertThrows(InvalidDateException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenNegativePrice_WhenAddingBook_ThenThrowNegativeNumberException() {
        //Given
        String title = "test";
        String publicationDateString = "2000-01-01";
        String isbn = "TEST";
        double price = -10.0;

        //When

        //Then
        assertThrows(NegativeNumberException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    //DELETE RELATED TESTS
    @Test
    public void givenNegativeIndex_WhenDeletingBook_ThenThrowNegativeIdException() {
        //Given
        long bookId = -1;

        //When

        //Then
        assertThrows(NegativeIdException.class, () -> bookService.deleteBookById(bookId));
    }

    @Test
    public void givenIndexThatDoesNotExist_WhenDeletingBook_ThenThrowIdNotFoundException() {
        //Given
        long bookId = 9999;

        //When

        //Then
        assertThrows(EntityNotFoundException.class, () -> bookService.deleteBookById(bookId));
    }

    //UPDATE RELATED TESTS
    @Test
    public void givenNegativeIndex_WhenUpdatingBook_ThenThrowNegativeIdException() {
        //Given
        long bookId = -1;

        //When

        //Then
        assertThrows(NegativeIdException.class, () -> bookService.deleteBookById(bookId));
    }

    @Test
    public void givenIndexThatDoesNotExist_WhenUpdatingBook_ThenThrowNegativeIdException() {
        //Given
        long bookId = 9999;

        //When

        //Then
        assertThrows(EntityNotFoundException.class, () -> bookService.deleteBookById(bookId));
    }

    @Test
    public void givenInvalidStringPublicationDate_WhenUpdatingBook_ThenInvalidDateException() {
        //Given
        String title = "test";
        String publicationDateString = "F.O.O.L. - Criminal";
        String isbn = "TEST";
        double price = 10.0;

        //When

        //Then
        assertThrows(InvalidDateException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    @Test
    public void givenNegativePrice_WhenUpdatingBook_ThenNegativeNumberException() {
        //Given
        String title = "test";
        String publicationDateString = "2000-01-01";
        String isbn = "TEST";
        double price = -10.0;

        //When

        //Then
        assertThrows(NegativeNumberException.class, () -> bookService.addBook(title, publicationDateString, isbn, price));
    }

    //READ RELATED TESTS
    @Test
    public void givenNegativeIndex_WhenGettingABook_ThenThrowNegativeIdException() {
        //Given
        long bookId = -1;

        //When

        //Then
        assertThrows(NegativeIdException.class, () -> bookService.deleteBookById(bookId));
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
        Book book1 = new Book("kniga", LocalDate.parse("2000-01-01"), "KNG", 10.0);
        Book book2 = new Book("book", LocalDate.parse("2000-02-02"), "BK", 15.59);
        Book book3 = new Book("KKNNIGGGA", LocalDate.parse("2000-03-03"), "KKNNGGG", 20.99);
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
