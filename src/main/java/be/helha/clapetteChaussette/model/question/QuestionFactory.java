package be.helha.clapetteChaussette.model.question;

public abstract class QuestionFactory {
    public abstract Question createQuestion(String questionText, String[] answers, boolean[] correctAnswers);
}
