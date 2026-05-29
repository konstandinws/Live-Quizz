// be.helha.clapetteChaussette.controller.LoginController
package be.helha.clapetteChaussette.controller;

import be.helha.clapetteChaussette.common.models.LoginRequest;
import be.helha.clapetteChaussette.common.models.RegisterRequest;
import be.helha.clapetteChaussette.model.Teacher;
import be.helha.clapetteChaussette.network.Client;
import be.helha.clapetteChaussette.network.ClientThreadHandler;
import be.helha.clapetteChaussette.repository.ITeacherRepository;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.service.editor.EditorService;
import be.helha.clapetteChaussette.service.editor.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.view.LoginViewFX.ILoginListener;

import java.io.IOException;
import java.util.UUID;

public class LoginController
        extends BaseController
        implements ILoginListener {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3001;

    private ITeacherRepository teacherRepository;
    private EditorService editorService;
    private ValidateQuestionEditorService validateService;

    private final Client client = new Client(SERVER_HOST, SERVER_PORT);

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
        if (!client.isConnected()) {
            client.connect(new ClientThreadHandler.Listener() {
                @Override
                public void onLogin(ClientThreadHandler handler, LoginRequest request) {
                }

                @Override
                public void onRegister(ClientThreadHandler handler, RegisterRequest request) {
                }
            });
        }
        try {
            client.send(event);
        } catch (IOException e) {
            System.err.println("Erreur d'envoi au serveur : " + e.getMessage());
        }
    }
}
