package be.helha.clapetteChaussette.common.models.events;

import be.helha.clapetteChaussette.common.dto.QuizzDTO;

import java.util.List;

/**
 * Server reply to a {@link SaveQuizzRequest}. Carries the refreshed list of the
 * teacher's quizzes so the client can update the "Mes questionnaires" screen.
 */
public class SaveQuizzResponse extends NetworkEvent {

    private final boolean success;
    private final String message;
    private final List<QuizzDTO> quizzes;

    public SaveQuizzResponse(boolean success, String message, List<QuizzDTO> quizzes) {
        this.success = success;
        this.message = message;
        this.quizzes = quizzes;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<QuizzDTO> getQuizzes() {
        return quizzes;
    }
}
