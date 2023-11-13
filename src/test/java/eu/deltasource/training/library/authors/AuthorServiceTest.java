package eu.deltasource.training.library.authors;

import eu.deltasource.training.library.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @Test
    public void givenAuthorFields_WhenAddingNewAuthor_AuthorRepositoryShouldNotBeEmpty() {
        //when
        authorService.addAuthor("test", "testov", "2000-01-01");
        //then
        Assertions.assertFalse(authorService.getAllAuthors().isEmpty());
    }
}
