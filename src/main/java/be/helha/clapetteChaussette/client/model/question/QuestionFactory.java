package be.helha.clapetteChaussette.client.model.question;

import be.helha.clapetteChaussette.common.models.questions.Question;

import java.util.List;

/**
 * Factory class for our question. If we want to add a new type of question, we
 * just need to create a new factory that extends this class and implements the
 * createQuestion method.
 * Also if we want to add new thing like timer or something else, we just need
 * to add it in the createQuestion method and it will be available for all the
 * question types.
 */
public abstract class QuestionFactory {

    /**
     * Creates a new question.
     * 
     * @param questionText   the text of the question
     * @param answers        an List of possible answers
     * @param correctAnswers an List indicating which answers are correct
     * @param asPoints       indicates if the question counts for points
     * @return a new Question instance
     */
    public abstract Question createQuestion(String questionText, List<String> answers, List<Boolean> correctAnswers,
                                            boolean asPoints);
}
