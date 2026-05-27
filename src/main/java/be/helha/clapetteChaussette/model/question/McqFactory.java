package be.helha.clapetteChaussette.model.question;

import java.util.ArrayList;
import java.util.List;

public class McqFactory extends QuestionFactory {
    
    /**
     * Creates a new multiple-choice question.
     * 
     * @param questionText the text of the question
     * @param answers an array of possible answers
     * @param correctAnswers an array indicating which answers are correct
     * @param asPoints indicates if the question counts for points
     * @return a new McqQuestion instance
     */
    @Override
    public Question createQuestion(String questionText, String[] answers, boolean[] correctAnswers, boolean asPoints) {
        List<Answer> answersList = new ArrayList<Answer>();
        for (int index = 0; index < answers.length; index++) {
            answersList.add(new Answer(answers[index], correctAnswers[index]));
        }
        return new McqQuestion(questionText, answersList);
    }
}
