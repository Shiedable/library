package eu.deltasource.training.library.controller;

import eu.deltasource.training.library.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            InvalidDateException.class,
            InvalidStringException.class,
            NegativeIdException.class,
            NegativeNumberException.class,
    })
    public ResponseEntity<String> badRequestHandler(RuntimeException exception) {
        return responseGenerator(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> badRequestHandler(MissingServletRequestParameterException exception) {
        if (exception.getMessage().contains("Date")) {
            return new ResponseEntity<>("Date cannot be null/empty", HttpStatus.BAD_REQUEST);
        }

        //TODO: use ex.getParam instead of checking
        return new ResponseEntity<>("String cannot be null/empty", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> notFoundHandler(RuntimeException exception) {
        return responseGenerator(exception, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> responseGenerator(Exception exception, HttpStatus status) {
        return new ResponseEntity<>(exception.getMessage(), status);
    }
}
