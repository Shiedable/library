package eu.deltasource.training.library.model;

import eu.deltasource.training.library.exceptions.EmptyStringException;
import eu.deltasource.training.library.exceptions.NullDateException;

import java.time.LocalDate;
import static org.springframework.util.StringUtils.hasLength;

//pojo that represents an author in our system
public class Author {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public Author() {
    } // remove no args contstructors

    //TODO : remove throws clause
    public Author(String firstName, String lastName, LocalDate birthDate) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthday(birthDate);
    }

    //TODO: guard clause all of the setters
    public void setFirstName(String firstName) throws EmptyStringException {
        if (hasLength(firstName)) {
            this.firstName = firstName;
        } else {
            throw new EmptyStringException("Empty first name!");
        }
    }

    public void setLastName(String lastName) throws EmptyStringException {
        if (hasLength(lastName)) {
            this.lastName = lastName;
        } else {
            throw new EmptyStringException("Empty last name!");
        }
    }

    public void setBirthday(LocalDate birthDate) throws NullDateException {
        if (birthDate != null) {
            this.birthDate = birthDate;
        } else {
            throw new NullDateException("Null birthdate");
        }
    }

    @Override
    public String toString() {
        return firstName + lastName + ", birthday=" + birthDate;
    }
}
