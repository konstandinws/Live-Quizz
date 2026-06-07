package be.helha.clapetteChaussette.common.models.events;

import be.helha.clapetteChaussette.common.dto.QuizzDTO;

/**
 * Sent by a teacher's client to ask the server to open a live session for a
 * quizz. The server generates an invitation code and creates the session.
 */
public class LaunchQuizzRequest extends NetworkEvent {

    private final QuizzDTO quizz;

    public LaunchQuizzRequest(QuizzDTO quizz) {
        this.quizz = quizz;
    }

    public QuizzDTO getQuizz() {
        return quizz;
    }
}
