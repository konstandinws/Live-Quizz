package be.helha.clapetteChaussette.controller.editor;

import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.view.QuizzFX;

public class EditorController {
    /**
     * This is the main controller for the editor features.
     * It will be used to navigate between the different editors and to share data
     * between them if needed.
     */
    NavigationService navigationService;
    Quizz quizz;

    public EditorController() {
        quizz = new Quizz();
    }

    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    public void navigateToQuizzEditor() {
        QuizzFX quizzFX = new QuizzFX();
        QuizzEditorController controller = new QuizzEditorController();
        quizzFX.setOwnViewController(controller);
        navigationService.navigateTo(quizzFX, "Éditeur de Questionnaire");
    }

    public void navigateToOpenQuestionEditor() {
    }

    public void navigateToPollQuestionEditor() {
    }

    public void navigateToQcmQuestionEditor() {
    }

    public void addQuestionToQuizz(Question question) {
        quizz.addQuestion(question);
    }

    public void saveQuizz() {
    }

}
