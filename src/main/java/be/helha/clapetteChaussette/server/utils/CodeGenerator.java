package be.helha.clapetteChaussette.server.utils;

import java.security.SecureRandom;

public final class CodeGenerator {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    private CodeGenerator() {
    }

    public static String generate() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return code.toString();
    }
}
