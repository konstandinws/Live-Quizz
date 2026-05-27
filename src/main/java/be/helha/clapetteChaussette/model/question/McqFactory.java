package be.helha.clapetteChaussette.model.question;

import java.util.ArrayList;
import java.util.List;

public class McqFactory extends QuestionFactory {

    /**
     * Creates a new multiple-choice question.
     * 
     * @param questionText   the text of the question
     * @param answers        an array of possible answers
     * @param correctAnswers an array indicating which answers are correct
     * @param asPoints       indicates if the question counts for points
     * @return a new McqQuestion instance
     */
    @Override
    public Question createQuestion(String questionText, List<String> answers, List<Boolean> correctAnswers,
            boolean asPoints) {
        List<Answer> answersList = new ArrayList<Answer>();
        for (int index = 0; index < answers.size(); index++) {
            answersList.add(new Answer(answers.get(index), correctAnswers.get(index)));
        }
        return new McqQuestion(questionText, answersList);
    }
}
