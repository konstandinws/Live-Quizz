// be.helha.clapetteChaussette.controller.LoginController
package be.helha.clapetteChaussette.client.controller;

import be.helha.clapetteChaussette.common.models.events.LoginRequest;
import be.helha.clapetteChaussette.common.models.events.LoginResponse;
import be.helha.clapetteChaussette.common.models.events.RegisterRequest;
import be.helha.clapetteChaussette.common.models.events.SaveQuizzResponse;
import be.helha.clapetteChaussette.common.models.events.LaunchQuizzResponse;
import be.helha.clapetteChaussette.common.models.Teacher;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.dto.TeacherDTO;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.controller.teacher.TeacherQuizzChoicesController;
import be.helha.clapetteChaussette.client.controller.teacher.TeacherWaitingController;
import be.helha.clapetteChaussette.client.view.ControlerComponent.ErrorPopup;
import be.helha.clapetteChaussette.client.view.teacher.TeacherQuizzChoicesViewFX;
import be.helha.clapetteChaussette.client.view.teacher.TeacherWaitingViewFX;
import be.helha.clapetteChaussette.server.network.ClientThreadHandler;
import be.helha.clapetteChaussette.server.repository.ITeacherRepository;
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

    private ITeacherRepository teacherRepository;
    private EditorService editorService;
    private ValidateQuestionEditorService validateService;

    private final NetworkController networkController;

    /** Prof connecté, mémorisé pour rafraîchir l'écran après une sauvegarde. */
    private TeacherDTO loggedInTeacher;

    public LoginController(NavigationService nav, ITeacherRepository db, EditorService ed,
            ValidateQuestionEditorService val, NetworkController networkController) {
        super(nav);
        this.teacherRepository = db;
        this.editorService = ed;
        this.validateService = val;
        this.networkController = networkController;
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

    @Override
    public void onSaveQuizzResponse(SaveQuizzResponse response) {
        // Réponse reçue sur le thread réseau : on repasse sur le thread JavaFX.
        Platform.runLater(() -> {
            if (response.isSuccess()) {
                // On dépile l'éditeur de l'historique : après l'enregistrement,
                // « Retour » depuis le menu ne doit pas ramener sur l'éditeur.
                showTeacherQuizzChoices(loggedInTeacher, response.getQuizzes(), true);
            } else {
                ErrorPopup.show(response.getMessage());
            }
        });
    }

    @Override
    public void onLaunchQuizzResponse(LaunchQuizzResponse response) {
        // Réponse reçue sur le thread réseau : on repasse sur le thread JavaFX.
        Platform.runLater(() -> {
            if (response.isSuccess()) {
                TeacherWaitingController waitingCtrl = new TeacherWaitingController(navigationService);
                TeacherWaitingViewFX waitingView = new TeacherWaitingViewFX();
                navigateToView(waitingView, "En attente de lancement", waitingCtrl);
                waitingView.setCode(response.getCode());
                waitingView.setParticipantCount(response.getParticipantCount());
            } else {
                ErrorPopup.show(response.getMessage());
            }
        });
    }

    private void openTeacherQuizzChoices(LoginResponse response) {
        Teacher teacher = response.getTeacher();
        TeacherDTO teacherDTO = new TeacherDTO(teacher.getUuid(), teacher.getName(), null);
        this.loggedInTeacher = teacherDTO;

        showTeacherQuizzChoices(teacherDTO, response.getQuizzes(), false);
    }

    /**
     * Affiche (ou réaffiche) l'écran « Mes questionnaires » avec la liste fournie.
     * Réutilisé après le login et après une sauvegarde réussie.
     *
     * @param replace {@code true} pour remplacer l'écran courant (l'éditeur) sans
     *                l'empiler dans l'historique ; {@code false} pour empiler
     *                normalement (cas du login).
     */
    private void showTeacherQuizzChoices(TeacherDTO teacher, List<QuizzDTO> quizzes, boolean replace) {
        TeacherQuizzChoicesController controller =
                new TeacherQuizzChoicesController(navigationService, teacher, editorService, validateService,
                        networkController);
        TeacherQuizzChoicesViewFX view = new TeacherQuizzChoicesViewFX(teacher.name());

        if (replace) {
            replaceView(view, "Mes questionnaires", controller);
        } else {
            navigateToView(view, "Mes questionnaires", controller);
        }
        view.displayQuizzes(quizzes);
    }
}
