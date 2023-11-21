package eu.deltasource.training.library.validator;

import eu.deltasource.training.library.exceptions.*;
import eu.deltasource.training.library.repository.AuthorsRepository;
import eu.deltasource.training.library.repository.BooksRepository;
import eu.deltasource.training.library.repository.SalesRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.springframework.util.StringUtils.hasLength;

public class Validator {

    public static void validateString(String string) {
        if (!hasLength(string)) {
            throw new InvalidStringException("String cannot be null/empty");
        }
    }

    public static void validateDate(String date) {
        if (!hasLength(date)) {
            throw new InvalidDateException("Date cannot be null/empty");
        }
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException exception) {
            throw new InvalidDateException("Date format is invalid");
        }
    }

    public static void validateId(long id) {
        if (id < 1) {
            throw new NegativeIdException("Index should positive");
        }
    }

    public static void validateNumber(int number) {
        if (number < 1) {
            throw new NegativeNumberException("Number should be positive");
        }
    }

    public static void validateNumber(double number) {
        if (number < 1) {
            throw new NegativeNumberException("Number should be positive");
        }
    }

    public static void validateId(long id, AuthorsRepository repository) {
        validateId(id);
        if(!repository.existsById(id)) {
            throw new IdNotFoundException("Author with such ID does not exist");
        }
    }

    public static void validateId(long id, BooksRepository repository) {
        validateId(id);
        if(!repository.existsById(id)) {
            throw new IdNotFoundException("Book with such ID does not exist");
        }
    }

    public static void validateId(long id, SalesRepository repository) {
        validateId(id);
        if(!repository.existsById(id)) {
            throw new IdNotFoundException("Sale with such ID does not exist");
        }
    }
}
