package be.helha.clapetteChaussette.common.models.events;

import be.helha.clapetteChaussette.common.dto.QuestionDTO;

import java.util.List;

/**
 * Sent by the server to the teacher and the students when the teacher reveals
 * the correct answer. Carries the current question with its correct answers
 * (students received a copy with the answers hidden) and the vote count per
 * answer, in answer order.
 */
public class RevealAnswerEvent extends NetworkEvent {

    private final QuestionDTO question;
    private final List<Integer> votesPerAnswer;

    public RevealAnswerEvent(QuestionDTO question, List<Integer> votesPerAnswer) {
        this.question = question;
        this.votesPerAnswer = votesPerAnswer;
    }

    public QuestionDTO getQuestion() {
        return question;
    }

    public List<Integer> getVotesPerAnswer() {
        return votesPerAnswer;
    }
}
