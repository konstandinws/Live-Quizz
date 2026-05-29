// be.helha.clapetteChaussette.controller.LoginController
package be.helha.clapetteChaussette.client.controller;

import be.helha.clapetteChaussette.common.models.events.LoginRequest;
import be.helha.clapetteChaussette.common.models.events.LoginResponse;
import be.helha.clapetteChaussette.common.models.events.RegisterRequest;
import be.helha.clapetteChaussette.common.models.Teacher;
import be.helha.clapetteChaussette.common.dto.TeacherDTO;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.controller.teacher.TeacherQuizzChoicesController;
import be.helha.clapetteChaussette.client.view.ControlerComponent.ErrorPopup;
import be.helha.clapetteChaussette.client.view.teacher.TeacherQuizzChoicesViewFX;
import be.helha.clapetteChaussette.server.network.ClientThreadHandler;
import be.helha.clapetteChaussette.server.repository.ITeacherRepository;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.service.editor.EditorService;
import be.helha.clapetteChaussette.client.service.editor.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.client.view.LoginViewFX.ILoginListener;

import javafx.application.Platform;

import java.io.IOException;
import java.util.UUID;

public class LoginController
        extends BaseController
        implements ILoginListener, ClientThreadHandler.Listener {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3001;

    private ITeacherRepository teacherRepository;
    private EditorService editorService;
    private ValidateQuestionEditorService validateService;

    private final NetworkController networkController = new NetworkController(SERVER_HOST, SERVER_PORT);

    public LoginController(NavigationService nav, ITeacherRepository db, EditorService ed,
            ValidateQuestionEditorService val) {
        super(nav);
        this.teacherRepository = db;
        this.editorService = ed;
        this.validateService = val;
    }

    public void onLoginRequested(String name, String password) {
        Teacher teacher = new Teacher(UUID.randomUUID(), name);
        send(new LoginRequest(teacher, password));
    }

    public void onRegisterRequested(String name, String password) {
        Teacher teacher = new Teacher(UUID.randomUUID(), name);
        send(new RegisterRequest(teacher, password));
    }

    private void send(Object event) {
        if (!networkController.isConnected()) {
            networkController.connect(this);
        }
        try {
            networkController.send(event);
        } catch (IOException e) {
            ErrorPopup.show("Impossible de joindre le serveur.");
        }
    }

    @Override
    public void onLogin(ClientThreadHandler handler, LoginRequest request) {
    }

    @Override
    public void onRegister(ClientThreadHandler handler, RegisterRequest request) {
    }

    @Override
    public void onLoginResponse(LoginResponse response) {
        if (response.isSuccess()) {
            // La réponse arrive sur le thread réseau : on repasse sur le thread JavaFX.
            Platform.runLater(() -> openTeacherQuizzChoices(response));
        } else {
            ErrorPopup.show(response.getMessage());
        }
    }

    @Override
    public void onError(Exception e) {
        ErrorPopup.show("Impossible de joindre le serveur.");
    }

    private void openTeacherQuizzChoices(LoginResponse response) {
        Teacher teacher = response.getTeacher();
        TeacherDTO teacherDTO = new TeacherDTO(teacher.getUuid(), teacher.getName(), null);

        TeacherQuizzChoicesController controller =
                new TeacherQuizzChoicesController(navigationService, teacherDTO, editorService, validateService);
        TeacherQuizzChoicesViewFX view = new TeacherQuizzChoicesViewFX(teacher.getName());

        navigateToView(view, "Mes questionnaires", controller);
        view.displayQuizzes(response.getQuizzes());
    }
}
