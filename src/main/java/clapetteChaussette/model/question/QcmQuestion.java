package clapetteChaussette.model.question;

public class QcmQuestion extends Question{

    String correctAnswer;

    public QcmQuestion(String question, String correctAnswer) {
        super(question);
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
