package be.helha.clapetteChaussette.exception.login;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {
        super("Invalid credentials");
    }
}
