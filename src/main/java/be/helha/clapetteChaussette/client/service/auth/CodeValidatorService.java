package be.helha.clapetteChaussette.client.service.auth;

public class CodeValidatorService {
    /**
     * Validates that the given code is in the correct format (e.g. 6 alphanumeric
     * characters).
     * This is a simple client-side check and does not guarantee that the code is
     * valid on the server.
     *
     * @param code The session code to validate.
     * @return true if the code is in a valid format, false otherwise.
     */
    private boolean isValidCodeFormat(String code) {
        return code != null && code.matches("^[a-zA-Z0-9]{6}$");
    }

    public boolean validateSessionCodeAndPseudo(String code, String pseudo) {
        if (code == null || code.isBlank()) {
            return false;
        }
        if (!isValidCodeFormat(code)) {
            return false;
        }
        if (pseudo == null || pseudo.isBlank()) {
            return false;
        }
        return true;
    }
}
