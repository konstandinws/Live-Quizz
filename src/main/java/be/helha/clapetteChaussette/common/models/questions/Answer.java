package be.helha.clapetteChaussette.common.models.questions;

import java.io.Serializable;

public class Answer implements Serializable {
    private static final long serialVersionUID = 1L;

    String answer;
    boolean isCorrect;

    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
