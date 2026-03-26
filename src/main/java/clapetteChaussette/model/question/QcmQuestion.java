package clapetteChaussette.model.question;

import java.util.ArrayList;
import java.util.List;

public class QcmQuestion extends Question{

    String correctAnswer;
    ArrayList<String> correctAnswers;
    boolean choixMultiple;

    // QCM classique avec une seule bonne réponse
    public QcmQuestion(String question, ArrayList<String> answers, String correctAnswer) {
        super(question);
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.choixMultiple = false;
    }

    // Vrai QCM avec plusieurs bonnes réponses
    public QcmQuestion(String question, ArrayList<String> answers,  ArrayList<String> correctAnswers) {
        super(question);
        this.answers = answers;
        this.correctAnswers = correctAnswers;
        this.choixMultiple = true;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public ArrayList<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public boolean isChoixMultiple() {
        return choixMultiple;
    }
}
