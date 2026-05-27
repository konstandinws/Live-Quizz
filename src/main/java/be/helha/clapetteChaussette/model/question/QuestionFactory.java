package be.helha.clapetteChaussette.model.question;

public abstract class QuestionFactory {
    
    /**
     * Creates a new question.
     * 
     * @param questionText the text of the question
     * @param answers an array of possible answers
     * @param correctAnswers an array indicating which answers are correct
     * @param asPoints indicates if the question counts for points
     * @return a new Question instance
     */
    public abstract Question createQuestion(String questionText, String[] answers, boolean[] correctAnswers,
            boolean asPoints);
}
