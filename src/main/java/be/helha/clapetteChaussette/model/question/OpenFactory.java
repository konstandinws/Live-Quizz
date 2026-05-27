package be.helha.clapetteChaussette.model.question;

import java.util.ArrayList;
import java.util.List;

public class OpenFactory extends QuestionFactory {
    @Override
    public Question createQuestion(String questionText, String[] answers, boolean[] correctAnswers, boolean asPoints) {
        List<Answer> AnswerList = new ArrayList<Answer>();
        for (int index = 0; index < answers.length; index++) {
            AnswerList.add(new Answer(answers[index], correctAnswers[index]));
        }
        return new OpenQuestion(questionText, AnswerList, asPoints);
    }
}
