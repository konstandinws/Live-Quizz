package be.helha.clapetteChaussette.client.model.question;

import be.helha.clapetteChaussette.common.models.questions.Answer;
import be.helha.clapetteChaussette.common.models.questions.PollQuestion;
import be.helha.clapetteChaussette.common.models.questions.Question;

import java.util.List;

public class PollFactory extends QuestionFactory {

    /**
     * Creates a new poll question.
     * 
     * @param questionText   the text of the poll question
     * @param answers        an array of possible answers (ignored for poll)
     * @param correctAnswers an array indicating which answers are correct (ignored
     *                       for poll)
     * @param asPoints       indicates if the question counts for points (ignored
     *                       for poll)
     * @return a new PollQuestion instance
     */
    @Override
    public Question createQuestion(String questionText, List<String> answers, List<Boolean> correctAnswers,
            boolean asPoints) {
        List<Answer> options = answers.stream().map(a -> new Answer(a, true)).toList();
        return new PollQuestion(questionText, options);
    }

}
