package be.helha.clapetteChaussette.model.question;

import java.util.ArrayList;
import java.util.List;

public class OpenFactory extends QuestionFactory {
    
    /**
     * Creates a new open-ended question.
     * 
     * @param questionText the text of the question
     * @param answers an array of possible answers
     * @param correctAnswers an array indicating which answers are correct
     * @param asPoints indicates if the question counts for points
     * @return a new OpenQuestion instance
     */
    @Override
    public Question createQuestion(String questionText, String[] answers, boolean[] correctAnswers, boolean asPoints) {
        List<Answer> AnswerList = new ArrayList<Answer>();
        for (int index = 0; index < answers.length; index++) {
            AnswerList.add(new Answer(answers[index], correctAnswers[index]));
        }
        return new OpenQuestion(questionText, AnswerList, asPoints);
    }
}
