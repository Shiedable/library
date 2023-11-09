package eu.deltasource.training.library.exceptions;

public class NegativeBookPriceException extends Exception{

    public NegativeBookPriceException(String message) {
        super(message);
    }
}
