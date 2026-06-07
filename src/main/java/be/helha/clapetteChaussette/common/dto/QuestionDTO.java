package be.helha.clapetteChaussette.common.dto;

import be.helha.clapetteChaussette.common.models.enums.QuestionType;
import be.helha.clapetteChaussette.common.models.questions.Answer;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object for persisting and retrieving Question entities.
 * <p>
 * This object abstracts the complexity of different question types from the
 * database.
 * It stores the essential elements needed to rebuild the specific question
 * subclass
 * later, allowing the database layer to remain agnostic of the specific model
 * implementations.
 * </p>
 *
 * @param uuid      The unique identifier of the question.
 * @param quizzUuid The unique identifier of the quiz this question belongs to.
 * @param title     The main content or title of the question.
 * @param answers   The list of possible answers associated with the question.
 * @param type      The specific type of the question, used later to reconstruct
 *                  the correct object.
 */
public record QuestionDTO(UUID uuid, UUID quizzUuid, String title, List<Answer> answers, QuestionType type)
        implements Serializable {
    private static final long serialVersionUID = 1L;
}
