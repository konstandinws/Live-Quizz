package be.helha.clapetteChaussette.model.question;

public class PollFactory extends QuestionFactory {
    @Override
    public Question createQuestion(String questionText, String[] answers, boolean[] correctAnswers, boolean asPoints) {
        return new PollQuestion(questionText);
    }

}
