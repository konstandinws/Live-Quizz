package be.helha.clapetteChaussette.service;

import java.util.List;

import be.helha.clapetteChaussette.exception.InvalidQuestionException;

public class ValidateQuestionEditorService {
    /**
     * This service will be responsible for all the business logic related to the
     * validation of the questions created in the editor.
     * It will check if the question is valid and if it can be saved to the
     * database.
     */

    final int MIN_ANSWERS_MCQ = 2;
    final int MAX_ANSWERS_MCQ = 6;

    /**
     * Vérifie que le titre de la question n'est pas vide ou composé uniquement
     * d'espaces.
     *
     * @param title Le titre à valider.
     * @throws InvalidQuestionException Si le titre est invalide.
     */
    private void validateTitle(String title) throws InvalidQuestionException {
        if (title.isBlank())
            throw new InvalidQuestionException("Le titre de la question est invalide.");
    }

    /**
     * check if the answers of a QCM question are valid (not empty, not blank, not
     * null) and that there are between 2 and 6 answers
     *
     * @param answers List of answer texts to validate.
     * @throws InvalidQuestionException If the number of answers is not between 2
     *                                  and 6.
     */
    private void validateMcqAnswers(List<String> answers) throws InvalidQuestionException {
        if (answers.size() < MIN_ANSWERS_MCQ || answers.size() > MAX_ANSWERS_MCQ)
            throw new InvalidQuestionException("Le nombre de réponses est invalide.");
    }

    /**
     * Checks that at least one answer has been selected as correct.
     *
     * @param correctAnswers The list of correct-answer flags.
     * @throws InvalidQuestionException If no correct answer is selected.
     */
    private void validateIfAtLeastOneValidAnswser(List<Boolean> correctAnswers) throws InvalidQuestionException {
        if (correctAnswers.stream().filter(Boolean::booleanValue).count() == 0)
            throw new InvalidQuestionException("Aucune réponse correcte n'a été sélectionnée.");
    }

    /**
     * Validates a list of answer texts.
     * <p>
     * Each answer is trimmed before validation. If any trimmed answer is blank,
     * validation fails and an exception is thrown indicating the 1-based position
     * of the invalid answer.
     *
     * @param answers the list of answers to validate
     * @throws InvalidQuestionException if at least one answer is blank after
     *                                  trimming
     */
    private void validateAnswers(List<String> answers) throws InvalidQuestionException {
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).trim().isBlank())
                throw new InvalidQuestionException("La réponse " + (i + 1) + " est invalide.");
        }
    }

    public void validateOpenQuestion(String title, List<String> answerTexts)
            throws InvalidQuestionException {
        validateTitle(title);
        validateAnswers(answerTexts);
    }

    public void validateMcqQuestion(String title, List<String> answers, List<Boolean> correctAnswers)
            throws InvalidQuestionException {
        validateTitle(title);
        validateAnswers(answers);
        validateMcqAnswers(answers);
        validateIfAtLeastOneValidAnswser(correctAnswers);
    }
}
