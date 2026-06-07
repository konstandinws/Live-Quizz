package be.helha.clapetteChaussette.common.dto;

import java.util.UUID;

/**
 * Data Transfer Object representing a Teacher, including sensitive information.
 * <p>
 * Typically used for registration or internal operations where the password hash
 * must be persisted to the database.
 * </p>
 *
 * @param uuid The unique identifier of the teacher.
 * @param name The teacher's username or display name.
 * @param hashedPassword The hashed version of the teacher's password.
 */
public record TeacherDTO(UUID uuid, String name, String hashedPassword) {}
