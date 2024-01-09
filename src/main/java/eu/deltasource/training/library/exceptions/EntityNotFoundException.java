package eu.deltasource.training.library.exceptions;

/**
 * Thrown to indicate that an Entity was not found
 */
public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String message) {
        super(message);
    }
}
