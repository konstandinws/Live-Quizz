package be.helha.clapetteChaussette.model.dto;

import java.util.UUID;

/**
 * A safe Data Transfer Object representing a Teacher for external responses.
 * <p>
 * This record deliberately excludes sensitive data, such as password hashes,
 * to ensure secure data transmission back to the client.
 * </p>
 *
 * @param uuid The unique identifier of the teacher.
 * @param name The teacher's username or display name.
 */
public record TeacherResponseDTO(UUID uuid, String name) {}
