// be.helha.clapetteChaussette.controller.LoginController
package be.helha.clapetteChaussette.controller;

import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.service.DbService;
import be.helha.clapetteChaussette.service.EditorService;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.service.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.controller.editor.EditorController;
import be.helha.clapetteChaussette.view.LoginViewFX.ILoginListener;
import be.helha.clapetteChaussette.view.editor.QuizzEditorFX;

import java.util.Optional;
import java.util.UUID;

public class LoginController
        extends BaseController
        implements ILoginListener {
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

        Optional<TeacherDTO> teacher = dbService.findTeacher(name);
        if (teacher.isPresent()) {
            goToEditor(teacher.get());
        } else {
            System.out.println("Échec connexion : Prof non trouvé.");// gonna need to throw an error that gonna be
                                                                     // catched by the view to display an error message
        }
    }

    public void onRegisterRequested(String name, String password) {
        TeacherDTO newTeacher = new TeacherDTO(UUID.randomUUID(), name, password); // need to deleguate into a service
        dbService.saveTeacher(newTeacher);
        goToEditor(newTeacher);
    }

    private void goToEditor(TeacherDTO teacher) {
        // for now we directly create a new quiz, but we should display a list of quiz
        // to choose from or an option to create a new one
        Quizz quizz = new Quizz("Nouveau Quiz");
        EditorController editorCtrl = new EditorController(quizz, editorService, navigationService, validateService);
        navigateToView(new QuizzEditorFX(), "Éditeur de Quiz", editorCtrl);
    }
}
