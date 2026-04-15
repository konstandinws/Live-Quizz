package be.helha.clapetteChaussette.model.question;

import be.helha.clapetteChaussette.model.enums.QuestionType;

import java.util.List;

public class OpenQuestion extends Question {

    private boolean pointsEnabled;

    public OpenQuestion(String question, List<Anwser> correctAnswers, boolean pointsEnabled) {
        super(question, QuestionType.OPEN);
        this.answers = correctAnswers;
        this.pointsEnabled = pointsEnabled;
    }

    public boolean isPointsEnabled() {
        return pointsEnabled;
    }

    public void setPointsEnabled(boolean pointsEnabled) {
        this.pointsEnabled = pointsEnabled;
    }

}
