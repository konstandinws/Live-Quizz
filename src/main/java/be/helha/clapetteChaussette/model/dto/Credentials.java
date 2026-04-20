package be.helha.clapetteChaussette.model.dto;

/**
 * Encapsulates the credentials used for authentication.
 *
 * @param name The username of the teacher.
 * @param hashed_password The hashed version of the password.
 */
public record Credentials(String name, String hashed_password) {}
