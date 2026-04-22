package be.helha.clapetteChaussette.exception;

// On change "extends Exception" par "extends RuntimeException"
public class InvalidQuestionException extends RuntimeException {
    public InvalidQuestionException(String message) {
        super(message);
    }
}