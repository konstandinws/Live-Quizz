package be.helha.clapetteChaussette.client.controller;

import be.helha.clapetteChaussette.common.models.events.LoginRequest;
import be.helha.clapetteChaussette.common.models.events.LoginResponse;
import be.helha.clapetteChaussette.common.models.events.NetworkEvent;
import be.helha.clapetteChaussette.common.models.events.RegisterRequest;
import be.helha.clapetteChaussette.common.models.Teacher;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.dto.TeacherDTO;
import be.helha.clapetteChaussette.client.network.ClientThreadHandler;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.controller.teacher.TeacherQuizzChoicesController;
import be.helha.clapetteChaussette.client.view.ControlerComponent.ErrorPopup;
import be.helha.clapetteChaussette.client.view.teacher.TeacherQuizzChoicesViewFX;
import be.helha.clapetteChaussette.client.view.teacher.TeacherWaitingViewFX;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.service.editor.EditorService;
import be.helha.clapetteChaussette.client.service.editor.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.client.view.LoginViewFX.ILoginListener;

import javafx.application.Platform;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class LoginController
        extends BaseController
        implements ILoginListener, ClientThreadHandler.Listener {

    private EditorService editorService;
    private ValidateQuestionEditorService validateService;

    private final NetworkController networkController;

    /** Logged-in teacher, kept to refresh the screen after a save. */
    private TeacherDTO loggedInTeacher;

    // Teacher's waiting screen, updated as students join.
    private TeacherWaitingViewFX teacherWaitingView;

    public LoginController(NavigationService nav, EditorService ed,
            ValidateQuestionEditorService val, NetworkController networkController) {
        super(nav);
        this.editorService = ed;
        this.validateService = val;
        this.networkController = networkController;
    }

    public void onLoginRequested(String name, String password) throws IOException {
        Teacher teacher = new Teacher(UUID.randomUUID(), name);
        send(new LoginRequest(teacher, password));
    }

    public void onRegisterRequested(String name, String password) throws IOException {
        Teacher teacher = new Teacher(UUID.randomUUID(), name);
        send(new RegisterRequest(teacher, password));
    }

    private void send(NetworkEvent event) throws IOException {
        if (!networkController.isConnected()) {
            networkController.connect(this);
        }
        networkController.send(event);
    }

    @Override
    public void onLoginResponse(LoginResponse response) {
        if (response.isSuccess()) {
            // Response arrives on the network thread: hop back onto the JavaFX thread.
            Platform.runLater(() -> openTeacherQuizzChoices(response));
        } else {
            ErrorPopup.show(response.getMessage());
        }
    }

    private void openTeacherQuizzChoices(LoginResponse response) {
        Teacher teacher = response.getTeacher();
        TeacherDTO teacherDTO = new TeacherDTO(teacher.getUuid(), teacher.getName(), null);
        this.loggedInTeacher = teacherDTO;

        showTeacherQuizzChoices(teacherDTO, response.getQuizzes(), false);
    }

    /**
     * Shows (or re-shows) the "Mes questionnaires" screen with the given list.
     * Reused after login and after a successful save.
     *
     * @param replace {@code true} to replace the current screen (the editor)
     *                without
     *                pushing it onto the history; {@code false} to push normally
     *                (login case).
     */
    private void showTeacherQuizzChoices(TeacherDTO teacher, List<QuizzDTO> quizzes, boolean replace) {
        TeacherQuizzChoicesViewFX view = new TeacherQuizzChoicesViewFX(teacher.name(), quizzes);
        TeacherQuizzChoicesController controller = new TeacherQuizzChoicesController(navigationService, teacher,
                editorService, validateService,
                networkController, view);

        if (replace) {
            replaceView(view, "Mes questionnaires", controller);
        } else {
            navigateToView(view, "Mes questionnaires", controller);
        }
    }
}
