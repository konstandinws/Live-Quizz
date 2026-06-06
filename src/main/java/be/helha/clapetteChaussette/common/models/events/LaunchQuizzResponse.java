package be.helha.clapetteChaussette.common.models.events;

import be.helha.clapetteChaussette.common.dto.QuizzDTO;

/**
 * Server reply to a {@link LaunchQuizzRequest}. Carries the invitation code the
 * teacher must share so students can join the session.
 */
public class LaunchQuizzResponse extends NetworkEvent {

    private final boolean success;
    private final String code;
    private final int participantCount;
    private final String message;
    private final QuizzDTO quizz;

    public LaunchQuizzResponse(boolean success, String code, int participantCount, String message, QuizzDTO quizz) {
        this.success = success;
        this.code = code;
        this.participantCount = participantCount;
        this.message = message;
        this.quizz = quizz;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public String getMessage() {
        return message;
    }

    public QuizzDTO getQuizz() {
        return quizz;
    }
}
