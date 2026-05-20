package be.helha.clapetteChaussette.controller;

import be.helha.clapetteChaussette.controller.student.StudentInvitationController;
import be.helha.clapetteChaussette.repository.ITeacherRepository;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.service.editor.EditorService;
import be.helha.clapetteChaussette.service.editor.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.view.HomeViewFX.IHomeListener;
import be.helha.clapetteChaussette.view.LoginViewFX;
import be.helha.clapetteChaussette.view.student.StudentInvitationViewFX;

public class HomeController extends BaseController implements IHomeListener {

    private final ITeacherRepository teacherRepository;
    private final EditorService editorService;
    private final ValidateQuestionEditorService validateService;

    public HomeController(NavigationService nav, ITeacherRepository teacherRepository,
                          EditorService editorService, ValidateQuestionEditorService validateService) {
        super(nav);
        this.teacherRepository = teacherRepository;
        this.editorService = editorService;
        this.validateService = validateService;
    }

    @Override
    public void onTeacherSelected() {
        LoginController loginCtrl = new LoginController(navigationService, teacherRepository, editorService, validateService);
        navigateToView(new LoginViewFX(), "Connexion - Professeur", loginCtrl);
    }

    @Override
    public void onStudentSelected() {
        StudentInvitationController studentCtrl = new StudentInvitationController(navigationService);
        navigateToView(new StudentInvitationViewFX(), "Rejoindre un Quiz", studentCtrl);
    }
}
