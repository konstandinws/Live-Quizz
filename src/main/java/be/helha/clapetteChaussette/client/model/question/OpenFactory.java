package be.helha.clapetteChaussette.client.model.question;

import be.helha.clapetteChaussette.common.models.questions.Answer;
import be.helha.clapetteChaussette.common.models.questions.OpenQuestion;
import be.helha.clapetteChaussette.common.models.questions.Question;

import java.util.ArrayList;
import java.util.List;

public class OpenFactory extends QuestionFactory {

    /**
     * Creates a new open-ended question.
     * 
     * @param questionText   the text of the question
     * @param answers        an array of possible answers
     * @param correctAnswers an array indicating which answers are correct
     * @param asPoints       indicates if the question counts for points
     * @return a new OpenQuestion instance
     */
    @Override
    public Question createQuestion(String questionText, List<String> answers, List<Boolean> correctAnswers,
                                   boolean asPoints) {
        List<Answer> AnswerList = new ArrayList<Answer>();
        for (int index = 0; index < answers.size(); index++) {
            AnswerList.add(new Answer(answers.get(index), correctAnswers.get(index)));
        }
        return new OpenQuestion(questionText, AnswerList, asPoints);
    }
}
