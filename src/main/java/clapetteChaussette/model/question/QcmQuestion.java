package clapetteChaussette.model.question;

import java.util.ArrayList;

public class QcmQuestion extends Question{

    String correctAnswer;
    ArrayList<String> correctAnswers;
    boolean choixMultiple;

    public QcmQuestion(String question, String correctAnswer) {
        super(question);
        this.correctAnswer = correctAnswer;
        this.choixMultiple = false;
    }

    public QcmQuestion(String question, ArrayList<String> correctAnswers) {
        super(question);
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
