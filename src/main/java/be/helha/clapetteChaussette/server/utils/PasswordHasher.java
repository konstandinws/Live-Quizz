package be.helha.clapetteChaussette.server.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class responsible for securing passwords.
 * <p>
 * Encapsulating this method abstracts the underlying library from the
 * rest of the application, centralizing the cryptographic implementation details.
 * </p>
 */
public class PasswordHasher {

    /**
     * Hashes a plain-text password using the BCrypt algorithm.
     *
     * @param password The plain-text password to hash.
     * @return The resulting hash string (which includes the generated salt).
     */
    public static String hashPassword(String password) {
        int rounds = 10;

        String salt = BCrypt.gensalt(rounds);

        return BCrypt.hashpw(password, salt);
    }

    /**
     * Verifies a plain-text password against a previously generated BCrypt hash.
     *
     * @param password The plain-text password provided by the user during login.
     * @param hashedPassword The stored hash retrieved from the database.
     * @return {@code true} if the password matches the hash, {@code false} otherwise.
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
