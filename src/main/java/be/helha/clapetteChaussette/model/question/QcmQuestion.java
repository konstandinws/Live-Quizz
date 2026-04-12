package be.helha.clapetteChaussette.model.question;

import be.helha.clapetteChaussette.model.enums.QuestionType;

import java.util.List;

public class QcmQuestion extends Question implements Answerable {

    List<String> correctAnswers;
    private boolean choixMultiple;

    // Vrai QCM avec plusieurs bonnes réponses
    public QcmQuestion(String question, List<String> answers, List<String> correctAnswers) {
        super(question, QuestionType.QCM);
        this.answers = answers;
        this.correctAnswers = correctAnswers;
        this.choixMultiple = isMultipleAnswers();
    }

    public boolean isMultipleAnswers() {
        return (correctAnswers.size() > 1);
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

}
