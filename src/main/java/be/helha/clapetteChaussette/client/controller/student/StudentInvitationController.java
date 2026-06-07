package be.helha.clapetteChaussette.client.controller.student;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.client.network.ClientThreadHandler;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.service.auth.CodeValidatorService;
import be.helha.clapetteChaussette.client.view.ControlerComponent.ErrorPopup;
import be.helha.clapetteChaussette.client.view.student.StudentInvitationViewFX.IStudentInvitationListener;
import be.helha.clapetteChaussette.client.view.student.StudentWaitingViewFX;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.models.events.JoinSessionRequest;
import be.helha.clapetteChaussette.common.models.events.JoinSessionResponse;
import be.helha.clapetteChaussette.common.models.events.LeaveSessionRequest;
import be.helha.clapetteChaussette.common.models.events.NextQuestionResponse;
import be.helha.clapetteChaussette.common.models.events.ParticipantCountUpdate;
import javafx.application.Platform;

import java.io.IOException;

public class StudentInvitationController extends BaseController
        implements IStudentInvitationListener, ClientThreadHandler.Listener {

    private final NetworkController networkController;

    // Waiting screen shown once the session is joined, updated as students arrive.
    private StudentWaitingViewFX waitingView;
    private CodeValidatorService codeValidatorService;
    // Whether this controller is already registered to receive server events.
    private boolean registered = false;

    public StudentInvitationController(NavigationService nav, NetworkController networkController) {
        super(nav);
        this.networkController = networkController;
        this.codeValidatorService = new CodeValidatorService(); // not dependency injected bc it's the only one to use
                                                                // it
    }

    @Override
    public void onJoinRequested(String code, String pseudo) throws IOException, IllegalArgumentException {
        if (!codeValidatorService.validateSessionCodeAndPseudo(code, pseudo)) {
            throw new IllegalArgumentException("Code de session ou pseudo invalide.");
        }
        // Make sure we listen for the server's reply. connect() registers us on the
        // first join, but a controller created after a finished quizz reuses an already
        // open connection, so it must register itself explicitly.
        if (!registered) {
            if (networkController.isConnected()) {
                networkController.addListener(this);
            } else {
                networkController.connect(this);
            }
            registered = true;
        }
        networkController.send(new JoinSessionRequest(code, pseudo));
    }

    @Override
    public void onJoinSessionResponse(JoinSessionResponse response) {
        Platform.runLater(() -> {
            if (response.isSuccess()) {
                StudentWaitingController waitingCtrl = new StudentWaitingController(navigationService);
                waitingView = new StudentWaitingViewFX();
                navigateToView(waitingView, "En attente du lancement...", waitingCtrl);
                waitingView.setParticipantCount(response.getParticipantCount());
                waitingView.setOnReturn(this::leaveSession);
            } else {
                ErrorPopup.show(response.getMessage());
            }
        });
    }

    // Leaves the session on the server, then navigates back.
    private void leaveSession() {
        try {
            networkController.send(new LeaveSessionRequest());
            waitingView = null;
            navigationService.goBack();
        } catch (IOException e) {
            onError(e);
        }
    }

    @Override
    public void onParticipantCountUpdate(ParticipantCountUpdate update) {
        Platform.runLater(() -> {
            if (waitingView != null) {
                waitingView.setParticipantCount(update.getParticipantCount());
            }
        });
    }

    // Only for the first question, then it passes to the StudentLiveController's same name method
    @Override
    public void onNextQuestionResponse(NextQuestionResponse response) {
        Platform.runLater(() -> {
            if (!response.isSuccess()) {
                ErrorPopup.show("Erreur lors du lancement : " + response.getErrorMessage());
                return;
            }
            QuestionDTO question = response.getQuestion();
            if (question == null) {
                return;
            }
            // Hand over from the waiting screen to the live quizz screen.
            networkController.removeListener(this);
            StudentLiveController liveController = new StudentLiveController(navigationService, networkController);
            liveController.showQuestion(question);
        });
    }

    @Override
    public void onError(Exception e) {
        ErrorPopup.show(e.getMessage());
    }
}
