package clapetteChaussette.model.question;

import java.util.List;

import clapetteChaussette.service.IDisplayable;

public class QcmQuestion extends Question implements IDisplayable {

    String correctAnswer;
    List<String> correctAnswers;
    boolean choixMultiple;

    // QCM classique avec une seule bonne réponse
    public QcmQuestion(String question, List<String> answers, String correctAnswer) {
        super(question);
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.choixMultiple = false;
    }

    // Vrai QCM avec plusieurs bonnes réponses
    public QcmQuestion(String question, List<String> answers, List<String> correctAnswers) {
        super(question);
        this.answers = answers;
        this.correctAnswers = correctAnswers;
        this.choixMultiple = true;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public boolean isChoixMultiple() {
        return choixMultiple;
    }
}
