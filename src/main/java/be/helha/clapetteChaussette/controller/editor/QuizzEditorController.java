package be.helha.clapetteChaussette.controller.editor;

import be.helha.clapetteChaussette.view.QuizzFX.IQuestionnaireListener;

// Controleur de l'édition du questionnaire, c'est lui qui manipulera le modèle
public class QuizzEditorController implements IQuestionnaireListener {

    EditorController mainController;

    /*
     * pas sur que cette classe soit super utile maintenant, il faudras demander au
     * prof.
     * Bien que je pense qu'elle est quand même sont utilité pour faire le lien
     * entre le questionnaire et les différentes vues d'édition de question. Car ça
     * permet de faire des traitement avant d'envoyer ça au main controller.
     */

    // au démarrage on crée un questionnaire vide
    public QuizzEditorController() {
    }

    public void setController(EditorController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void onAddMcqRequested() {
        this.mainController.navigateToMcqQuestionEditor();
    }

    @Override
    public void onAddPollRequested() {
        this.mainController.navigateToPollQuestionEditor();
    }

    @Override
    public void onAddQuestionFreeRequested() {
        this.mainController.navigateToOpenQuestionEditor();
    }

    @Override
    public void onSaveRequested() {
        this.mainController.saveQuizz();
    }

}
