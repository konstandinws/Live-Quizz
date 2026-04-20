package be.helha.clapetteChaussette.controller.editor;

import java.util.ArrayList;
import java.util.List;

import be.helha.clapetteChaussette.controller.IViewController;
import be.helha.clapetteChaussette.exception.InvalidQuestionException;
import be.helha.clapetteChaussette.model.question.Answer;
import be.helha.clapetteChaussette.model.question.McqQuestion;

/**
 * Contrôleur responsable de la création et de la validation des questions QCM.
 * <p>
 * Fait le lien entre la vue
 * {@link be.helha.clapetteChaussette.view.McqEditorFX} et
 * le service de persistance (à intégrer). Toute la logique de validation
 * métier est centralisée ici.
 */
public class McqEditorController implements IViewController {

    /**
     * Valide les données fournies et crée une nouvelle instance de
     * {@link McqQuestion}.
     * <p>
     * Note : Actuellement, cette méthode retourne l'objet créé. À l'avenir, elle
     * devrait
     * appeler un service (ex: {@code QuestionService}) pour enregistrer la question
     * en base de données.
     *
     * @param title          L'intitulé de la question.
     * @param answers        La liste complète des choix de réponses possibles.
     * @param correctAnswers La liste des réponses sélectionnées comme étant
     *                       correctes.
     * @return Une instance valide de {@link McqQuestion}.
     * @throws InvalidQuestionException Si la validation échoue (titre vide, réponse
     *                                  vide, ou aucune bonne réponse).
     */

    EditorController mainController;

    public void setController(EditorController mainController) {
        this.mainController = mainController;
    }

    public void createMcq(String title, List<String> answers, List<Boolean> correctAnswers)
            throws InvalidQuestionException {

        validateTitle(title);
        List<Answer> answerObjects = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            answerObjects.add(new Answer(answers.get(i), correctAnswers.get(i)));
        }

        validateAnswers(answerObjects);

        List<String> correctAnswerTexts = answerObjects.stream()
                .filter(Answer::isCorrect)
                .map(Answer::getAnswer)
                .toList();
        validateCorrectAnswer(correctAnswerTexts);

        // remplacer le return par un appel au service
        mainController.addQuestionToQuizz(new McqQuestion(title, answerObjects));
        mainController.navigateToQuizzEditor();
    }

    /**
     * Vérifie que le titre de la question n'est pas vide ou composé uniquement
     * d'espaces.
     *
     * @param title Le titre à valider.
     * @throws InvalidQuestionException Si le titre est invalide.
     */
    private void validateTitle(String title) throws InvalidQuestionException {
        if (title.isBlank())
            throw new InvalidQuestionException("il ne faut pas que la question posée soit vide");
    }

    /**
     * Vérifie qu'aucune des réponses proposées n'est vide ou composée uniquement
     * d'espaces.
     *
     * @param answers La liste des réponses à valider.
     * @throws InvalidQuestionException Si au moins une réponse est invalide.
     */
    private void validateAnswers(List<Answer> answers) throws InvalidQuestionException {
        for (Answer answer : answers) {
            if (answer.getAnswer().isBlank())
                throw new InvalidQuestionException("une reponse ne peut pas etre vide");
        }
    }

    /**
     * Vérifie qu'au moins une réponse a été sélectionnée comme correcte.
     *
     * @param correctAnswers La liste des bonnes réponses.
     * @throws InvalidQuestionException Si la liste est vide.
     */
    private void validateCorrectAnswer(List<String> correctAnswers) throws InvalidQuestionException {
        if (correctAnswers.isEmpty())
            throw new InvalidQuestionException("il faut au moins une réponse correcte");
    }

    // ajouter peut-etre une validation de minimum 2 choix sinon erreur
}
