package be.helha.clapetteChaussette.common.exception.login;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {
        super("Invalid credentials");
    }
}
