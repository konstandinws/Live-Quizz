package be.helha.clapetteChaussette.exception;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {
        super("Invalid credentials");
    }
}
