package eu.deltasource.training.library.controller;

import eu.deltasource.training.library.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            InvalidAuthorException.class,
            InvalidBookException.class,
            InvalidSaleException.class
    })
    public ResponseEntity<String> invalidEntityHandler(RuntimeException exception) {
        return responseGenerator(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> missingParameterHandler(MissingServletRequestParameterException exception) {
        return new ResponseEntity<>(exception.getParameterName() + " cannot be null/empty", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> adapterExceptionHandler(HttpClientErrorException exception) {
        String responseBody = extractClientErrorMessageFromException(exception);
        return new ResponseEntity<>(responseBody, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundHandler(RuntimeException exception) {
        return responseGenerator(exception, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> responseGenerator(Exception exception, HttpStatus status) {
        return new ResponseEntity<>(exception.getMessage(), status);
    }

    private String extractClientErrorMessageFromException (HttpClientErrorException exception) {
        int beginIndex = exception.getMessage().indexOf("\"") + 1;
        int endIndex = exception.getMessage().length() - 1;
        return exception.getMessage().substring(beginIndex, endIndex);
    }
}
