package be.helha.clapetteChaussette.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public static String hashPassword(String password) {
        int rounds = 10;

        String salt = BCrypt.gensalt(rounds);

        return BCrypt.hashpw(password, salt);
    }
}
