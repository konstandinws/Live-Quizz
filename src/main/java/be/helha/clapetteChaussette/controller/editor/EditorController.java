package be.helha.clapetteChaussette.controller.editor;

import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.view.OpenEditorFX;
import be.helha.clapetteChaussette.view.QcmEditorFX;
import be.helha.clapetteChaussette.view.QuizzFX;
import be.helha.clapetteChaussette.view.ViewFX;

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
        controller.setController(this);
        navigateTo(quizzFX, "Éditeur de Questionnaire");
    }

    public void navigateToOpenQuestionEditor() {
        OpenEditorFX openEditorFX = new OpenEditorFX();
        OpenEditorController controller = new OpenEditorController();
        openEditorFX.setOwnViewController(controller);
        controller.setController(this);
        navigateTo(openEditorFX, "Éditeur de Question Libre");
    }

    public void navigateToPollQuestionEditor() {
        // TODO: implement poll editor
    }

    public void navigateToQcmQuestionEditor() {
        QcmEditorFX qcmEditorFX = new QcmEditorFX();
        QcmEditorController qcmEditorController = new QcmEditorController();
        qcmEditorFX.setOwnViewController(qcmEditorController);
        qcmEditorController.setController(this);
        navigateTo(qcmEditorFX, "Éditeur de Question QCM");
    }

    public void navigateTo(ViewFX view, String title) {
        navigationService.navigateTo(view, title);
    }

    public void addQuestionToQuizz(Question question) {
        quizz.addQuestion(question);
    }

    public void saveQuizz() {
    }

}
