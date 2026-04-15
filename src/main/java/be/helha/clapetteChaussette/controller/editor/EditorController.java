package be.helha.clapetteChaussette.controller.editor;

import be.helha.clapetteChaussette.service.NavigationService;

public class EditorController {
    /**
     * This is the main controller for the editor features.
     * It will be used to navigate between the different editors and to share data
     * between them if needed.
     */
    NavigationService navigationService;

    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    public void navigateToQuizzEditor() {
    };

    public void navigateToOpenQuestionEditor() {
    };

    public void navigateToPollQuestionEditor() {
    };

    public void navigateToQcmQuestionEditor() {
    };

}
