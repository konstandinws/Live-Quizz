package be.helha.clapetteChaussette.model.question;

import be.helha.clapetteChaussette.model.enums.QuestionType;

import java.util.List;

public class OpenQuestion extends Question {

    private List<Anwser> correctAnswers;
    private boolean pointsEnabled;

    public OpenQuestion(String question, List<Anwser> correctAnswers, boolean pointsEnabled) {
        super(question, QuestionType.OPEN);
        this.correctAnswers = correctAnswers;
        this.pointsEnabled = pointsEnabled;
    }

    public List<Anwser> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(List<Anwser> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public boolean isPointsEnabled() {
        return pointsEnabled;
    }

    public void setPointsEnabled(boolean pointsEnabled) {
        this.pointsEnabled = pointsEnabled;
    }

}
