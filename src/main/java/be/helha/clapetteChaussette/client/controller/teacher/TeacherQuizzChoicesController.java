package be.helha.clapetteChaussette.client.controller.teacher;

import java.io.IOException;
import java.util.List;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.client.controller.editor.EditorController;
import be.helha.clapetteChaussette.common.models.Quizz;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.dto.TeacherDTO;
import be.helha.clapetteChaussette.common.models.events.LaunchQuizzRequest;
import be.helha.clapetteChaussette.common.models.events.LaunchQuizzResponse;
import javafx.application.Platform;
import be.helha.clapetteChaussette.client.network.ClientThreadHandler;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.service.editor.EditorService;
import be.helha.clapetteChaussette.client.service.editor.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.client.view.teacher.TeacherQuizzChoicesViewFX;
import be.helha.clapetteChaussette.client.view.teacher.TeacherWaitingViewFX;
import be.helha.clapetteChaussette.client.view.teacher.TeacherQuizzChoicesViewFX.ITeacherQuizzChoicesListener;

public class TeacherQuizzChoicesController extends BaseController
        implements ITeacherQuizzChoicesListener, ClientThreadHandler.Listener {

    private final TeacherDTO teacher;
    private final EditorService editorService;
    private final ValidateQuestionEditorService validateService;
    private final NetworkController networkController;
    private final TeacherQuizzChoicesViewFX view;

    public TeacherQuizzChoicesController(NavigationService nav, TeacherDTO teacher,
            EditorService editorService, ValidateQuestionEditorService validateService,
            NetworkController networkController, TeacherQuizzChoicesViewFX view) {
        super(nav);
        this.teacher = teacher;
        this.editorService = editorService;
        this.validateService = validateService;
        this.networkController = networkController;
        this.view = view;

        try {
            networkController.addListener(this);
        } catch (IOException e) {
            ErrorPopup.show("Erreur de connexion au serveur : " + e.getMessage());
        }
    }

    /**
     * Handles the "New Quiz" button click by creating a new quiz and navigating to
     * the quiz editor view.
     */
    @Override
    public void onNewQuizzRequested() {
        Quizz quizz = new Quizz("Nouveau Quiz");
        EditorController editorCtrl = new EditorController(quizz, teacher, editorService, navigationService,
                validateService, this, networkController);
        editorCtrl.openEditor();
    }

    public void setQuizzes(List<QuizzDTO> quizz) {
        view.setQuizzes(quizz);
    }

    public void showQuizzes(List<QuizzDTO> quizzes) {
        view.setQuizzes(quizzes);
        replaceView(view, "Mes questionnaires", this);
    }

    @Override
    public void onLaunchQuizzRequested(QuizzDTO quizz) throws IOException {
        networkController.send(new LaunchQuizzRequest(quizz));
    }

    @Override
    public void onLaunchQuizzResponse(LaunchQuizzResponse response) {
        Platform.runLater(() -> {
            if (response.isSuccess()) {
                TeacherWaitingViewFX teacherWaitingView = new TeacherWaitingViewFX();
                TeacherWaitingController waitingCtrl = new TeacherWaitingController(navigationService,
                        response.getQuizz(), networkController, teacherWaitingView);
                navigateToView(teacherWaitingView, "En attente de lancement", waitingCtrl);
                teacherWaitingView.setCode(response.getCode());
                teacherWaitingView.setParticipantCount(response.getParticipantCount());
            } else {
                view.showError(response.getMessage());
            }
        });
    }
}
