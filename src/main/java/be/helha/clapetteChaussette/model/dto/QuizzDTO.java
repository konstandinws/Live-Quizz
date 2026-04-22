package be.helha.clapetteChaussette.model.dto;

import java.util.UUID;

/**
 * Data Transfer Object representing a Quiz.
 * <p>
 * This object is used to transfer basic quiz information between the service
 * and the repository layers.
 * </p>
 *
 * @param uuid The unique identifier of the quiz.
 * @param name The display name of the quiz (e.g., "Math 101 Midterm").
 * @param teacherUuid The identifier of the teacher who owns and manages this quiz.
 */
public record QuizzDTO(UUID uuid, String name, UUID teacherUuid) {}
