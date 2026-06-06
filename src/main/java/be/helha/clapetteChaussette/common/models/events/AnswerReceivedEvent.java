package be.helha.clapetteChaussette.common.models.events;

import java.util.List;

/**
 * Sent by the server to the teacher each time a student answers the current
 * question. Carries the overall progress (N/N) and the live vote count per
 * answer, in answer order.
 */
public class AnswerReceivedEvent extends NetworkEvent {

    private final int answeredCount;
    private final int participantCount;
    private final List<Integer> votesPerAnswer;

    public AnswerReceivedEvent(int answeredCount, int participantCount, List<Integer> votesPerAnswer) {
        this.answeredCount = answeredCount;
        this.participantCount = participantCount;
        this.votesPerAnswer = votesPerAnswer;
    }

    public int getAnsweredCount() {
        return answeredCount;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public List<Integer> getVotesPerAnswer() {
        return votesPerAnswer;
    }
}
