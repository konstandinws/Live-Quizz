// be.helha.clapetteChaussette.controller.LoginController
package be.helha.clapetteChaussette.controller;

import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.dto.Credentials;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.service.DbService;
import be.helha.clapetteChaussette.service.EditorService;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.service.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.controller.editor.EditorController;
import be.helha.clapetteChaussette.view.LoginViewFX;
import be.helha.clapetteChaussette.view.editor.QuizzEditorFX;

import java.util.Optional;
import java.util.UUID;

public class LoginController extends BaseController {
    private DbService dbService;
    private EditorService editorService;
    private ValidateQuestionEditorService validateService;

    public LoginController(NavigationService nav, DbService db, EditorService ed, ValidateQuestionEditorService val) {
        super(nav);
        this.dbService = db;
        this.editorService = ed;
        this.validateService = val;
    }

    public void onLoginRequested(String name, String password) {
        Credentials creds = new Credentials(name, password);
        Optional<TeacherDTO> teacher = dbService.findTeacher(creds);

        if (teacher.isPresent()) {
            goToEditor(teacher.get());
        } else {
            System.out.println("Échec connexion : Prof non trouvé.");
        }
    }

    public void onRegisterRequested(String name, String password) {
        TeacherDTO newTeacher = new TeacherDTO(UUID.randomUUID(), name, password);
        dbService.saveTeacher(newTeacher);
        goToEditor(newTeacher);
    }

    private void goToEditor(TeacherDTO teacher) {
        // Pour l'instant on va direct à l'éditeur, mais on pourrait aller vers une liste de quiz
        Quizz quizz = new Quizz("Nouveau Quiz");
        EditorController editorCtrl = new EditorController(quizz, editorService, navigationService, validateService);
        // On stocke le prof dans l'application via le controller
        editorCtrl.navigateToView(new QuizzEditorFX(), "Éditeur de Quiz", editorCtrl);
    }
}