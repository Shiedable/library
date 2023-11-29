package eu.deltasource.training.library.service;

import eu.deltasource.training.library.exceptions.EntityNotFoundException;
import eu.deltasource.training.library.exceptions.InvalidDateException;
import eu.deltasource.training.library.exceptions.InvalidStringException;
import eu.deltasource.training.library.exceptions.NegativeIdException;
import eu.deltasource.training.library.model.Author;
import eu.deltasource.training.library.repository.AuthorsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthorServiceTest {

    private static AuthorService authorService;
    private static AuthorsRepository mockedAuthorRepository;

    @BeforeAll
    public static void initialize() {
        mockedAuthorRepository = Mockito.mock(AuthorsRepository.class);
        authorService = new AuthorService(mockedAuthorRepository);
    }


    //CREATE RELATED TESTS
    @Test
    public void givenEmptyFirstName_WhenAddingAuthor_ThenThrowInvalidStringException() {
        //Given
        String firstName = "";
        String lastName = "testov";
        String birthDate = "2000-01-01";

        //When

        //Then
        assertThrows(InvalidStringException.class, () -> authorService.addAuthor(firstName, lastName, birthDate));
    }

    @Test
    public void givenNullFirstName_WhenAddingAuthor_ThenThrowInvalidStringException() {
        //Given
        String firstName = null;
        String lastName = "testov";
        String birthDate = "2000-01-01";

        //When

        //Then
        assertThrows(InvalidStringException.class, () -> authorService.addAuthor(firstName, lastName, birthDate));
    }

    @Test
    public void givenEmptyLastName_WhenAddingAuthor_ThenThrowInvalidStringException() {
        //Given
        String firstName = "test";
        String lastName = "";
        String birthDate = "2000-01-01";

        //When

        //Then
        assertThrows(InvalidStringException.class, () -> authorService.addAuthor(firstName, lastName, birthDate));
    }

    @Test
    public void givenNullLastName_WhenAddingAuthor_ThenThrowInvalidStringException() {
        //Given
        String firstName = "test";
        String lastName = null;
        String birthDate = "2000-01-01";

        //When

        //Then
        assertThrows(InvalidStringException.class, () -> authorService.addAuthor(firstName, lastName, birthDate));
    }

    @Test
    public void givenEmptyStringBirthDate_WhenAddingAuthor_ThenThrowInvalidDateException() {
        //Given
        String firstName = "test";
        String lastName = "testov";
        String birthDate = "";

        //When

        //Then
        assertThrows(InvalidDateException.class, () -> authorService.addAuthor(firstName, lastName, birthDate));
    }

    @Test
    public void givenNullStringBirthDate_WhenAddingAuthor_ThenThrowInvalidDateException() {
        //Given
        String firstName = "test";
        String lastName = "testov";
        String birthDate = null;

        //When

        //Then
        assertThrows(InvalidDateException.class, () -> authorService.addAuthor(firstName, lastName, birthDate));
    }

    @Test
    public void givenInvalidStringBirthDate_WhenAddingAuthor_ThenThrowInvalidDateException() {
        //Given
        String firstName = "test";
        String lastName = "testov";
        String birthDate = "invalidDateFormat";

        //When

        //Then
        assertThrows(InvalidDateException.class, () -> authorService.addAuthor(firstName, lastName, birthDate));
    }

    //DELETE RELATED TESTS
    @Test
    public void givenNegativeIndex_WhenDeletingAuthor_ThenThrowNegativeIdException() {
        //Given
        long authorId = -1;

        //When

        //Then
        assertThrows(NegativeIdException.class, () -> authorService.deleteAuthorById(authorId));
    }

    @Test
    public void givenIndexThatDoesNotExist_WhenDeletingAuthor_ThenThrowIdNotFoundException() {
        //Given
        long authorId = 9999;

        //When

        //Then
        assertThrows(EntityNotFoundException.class, () -> authorService.deleteAuthorById(authorId));
    }

    //UPDATE RELATED TESTS
    @Test
    public void givenNegativeIndex_WhenUpdatingAuthor_ThenThrowNegativeIdException() {
        //Given
        String firstName = "test";
        String lastName = "testov";
        String birthDate = "2000-01-01";
        long authorId = -1;

        //When

        //Then
        assertThrows(NegativeIdException.class,
                () -> authorService.updateAuthorById(authorId, firstName, lastName, birthDate));
    }

    @Test
    public void givenIndexThatDoesNotExist_WhenUpdatingAuthor_ThenThrowNegativeIdException() {
        //Given
        String firstName = "test";
        String lastName = "testov";
        String birthDate = "2000-01-01";
        long authorId = 9999;

        //When

        //Then
        assertThrows(EntityNotFoundException.class,
                () -> authorService.updateAuthorById(authorId, firstName, lastName, birthDate));
    }

    @Test
    public void givenInvalidStringBirthDate_WhenUpdatingAuthor_ThenInvalidDateException() {
        //Given
        String firstName = "";
        String lastName = "testov";
        String birthDate = "gladen sum";
        Author savedAuthor = new Author(1, "test", "testov", LocalDate.parse("2000-01-01"));

        //When
        Mockito.when(mockedAuthorRepository.existsById((long) 1)).thenReturn(true);
        Mockito.when(mockedAuthorRepository.findById((long) 1))
                .thenReturn(Optional.of(savedAuthor));

        //Then
        assertThrows(InvalidDateException.class,
                () -> authorService.updateAuthorById(1, firstName, lastName, birthDate));
    }

    //READ RELATED TESTS
    @Test
    public void givenNegativeIndex_WhenGettingAnAuthor_ThenThrowNegativeIdException() {
        //Given
        long authorId = -1;

        //When

        //Then
        assertThrows(NegativeIdException.class, () -> authorService.getAuthorById(authorId));
    }

    @Test
    public void givenNonExistentIndex_WhenGettingAnAuthor_ThenThrowIdNotFoundException() {
        //Given
        long authorId = 9999;

        //When

        //Then
        assertThrows(EntityNotFoundException.class, () -> authorService.getAuthorById(authorId));
    }

    @Test
    public void givenEmptyRepository_WhenGettingAllAuthors_ThenGetEmptyList() {
        //Given
        List<Author> emptyList = new ArrayList<>();

        //When
        Mockito.when(mockedAuthorRepository.findAll()).thenReturn(emptyList);

        //Then
        assertThat(authorService.getAllAuthors().isEmpty(), is(true));
    }

    @Test
    public void givenRepositoryWithAuthors_WhenGettingAllAuthors_ThenGetListOfAuthors() {
        //Given
        List<Author> authorList = new ArrayList<>();
        Author author1 = new Author("test", "testov", LocalDate.parse("2000-01-01"));
        Author author2 = new Author("pesho", "peshov", LocalDate.parse("2000-02-02"));
        Author author3 = new Author("go6o", "go6ov", LocalDate.parse("2000-03-03"));
        authorList.add(author1);
        authorList.add(author2);
        authorList.add(author3);

        //When
        Mockito.when(mockedAuthorRepository.findAll()).thenReturn(authorList);

        //Then
        assertThat(authorService.getAllAuthors().contains(author1), is(true));
        assertThat(authorService.getAllAuthors().contains(author2), is(true));
        assertThat(authorService.getAllAuthors().contains(author3), is(true));
    }
}
