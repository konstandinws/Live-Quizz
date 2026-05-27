package be.helha.clapetteChaussette.model.question;

import java.util.ArrayList;
import java.util.List;

public class McqFactory extends QuestionFactory {
    @Override
    public Question createQuestion(String questionText, String[] answers, boolean[] correctAnswers, boolean asPoints) {
        List<Answer> answersList = new ArrayList<Answer>();
        for (int index = 0; index < answers.length; index++) {
            answersList.add(new Answer(answers[index], correctAnswers[index]));
        }
        return new McqQuestion(questionText, answersList);
    }

}
