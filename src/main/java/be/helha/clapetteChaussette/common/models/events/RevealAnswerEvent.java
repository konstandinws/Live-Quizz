package be.helha.clapetteChaussette.common.models.events;

/**
 * Sent by the server to the teacher when they ask to reveal the correct answer
 */
public class RevealAnswerEvent extends NetworkEvent {
    private final boolean rightAnswer;

    public RevealAnswerEvent(boolean rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public boolean isRightAnswer() {
        return rightAnswer;
    }
}
