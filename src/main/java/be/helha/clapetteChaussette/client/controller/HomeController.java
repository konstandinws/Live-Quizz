package be.helha.clapetteChaussette.client.controller;

import be.helha.clapetteChaussette.client.controller.student.StudentInvitationController;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.service.editor.EditorService;
import be.helha.clapetteChaussette.client.service.editor.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.client.view.HomeViewFX.IHomeListener;
import be.helha.clapetteChaussette.client.view.LoginViewFX;
import be.helha.clapetteChaussette.client.view.student.StudentInvitationViewFX;

public class HomeController extends BaseController implements IHomeListener {

    private final EditorService editorService;
    private final ValidateQuestionEditorService validateService;
    private final NetworkController networkController;

    public HomeController(NavigationService nav,
            EditorService editorService, ValidateQuestionEditorService validateService,
            NetworkController networkController) {
        super(nav);
        this.editorService = editorService;
        this.validateService = validateService;
        this.networkController = networkController;
    }

    @Override
    public void onTeacherSelected() {
        LoginController loginCtrl = new LoginController(navigationService, editorService,
                validateService, networkController);
        navigateToView(new LoginViewFX(), "Connexion - Professeur", loginCtrl);
    }

    @Override
    public void onStudentSelected() {
        StudentInvitationController studentCtrl = new StudentInvitationController(navigationService, networkController);
        navigateToView(new StudentInvitationViewFX(), "Rejoindre un Quiz", studentCtrl);
    }
}
