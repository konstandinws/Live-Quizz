package be.helha.clapetteChaussette.common.exception.login;

public class LoginFailedException extends Exception {
    public LoginFailedException() {
        super("Invalid credentials");
    }
}
