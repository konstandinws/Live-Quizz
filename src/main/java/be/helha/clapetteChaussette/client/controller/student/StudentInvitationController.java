package be.helha.clapetteChaussette.client.controller.student;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.view.ControlerComponent.ErrorPopup;
import be.helha.clapetteChaussette.client.view.student.StudentInvitationViewFX.IStudentInvitationListener;
import be.helha.clapetteChaussette.client.view.student.StudentWaitingViewFX;
import be.helha.clapetteChaussette.common.models.events.JoinSessionRequest;
import be.helha.clapetteChaussette.common.models.events.JoinSessionResponse;
import be.helha.clapetteChaussette.common.models.events.LeaveSessionRequest;
import be.helha.clapetteChaussette.common.models.events.LoginRequest;
import be.helha.clapetteChaussette.common.models.events.ParticipantCountUpdate;
import be.helha.clapetteChaussette.common.models.events.RegisterRequest;

import javafx.application.Platform;

import java.io.IOException;

public class StudentInvitationController extends BaseController
        implements IStudentInvitationListener {

    private final NetworkController networkController;

    // Waiting screen shown once the session is joined, updated as students arrive.
    private StudentWaitingViewFX waitingView;

    public StudentInvitationController(NavigationService nav, NetworkController networkController) {
        super(nav);
        this.networkController = networkController;
    }

    @Override
    public void onJoinRequested(String code, String pseudo) throws IOException {
        if (!networkController.isConnected()) {
            networkController.connect();
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
        networkController.send(new LeaveSessionRequest());
        waitingView = null;
        navigationService.goBack();
    }

    @Override
    public void onParticipantCountUpdate(ParticipantCountUpdate update) {
        Platform.runLater(() -> {
            if (waitingView != null) {
                waitingView.setParticipantCount(update.getParticipantCount());
            }
        });
    }

    @Override
    public void onError(Exception e) {
        ErrorPopup.show(e.getMessage());
    }

    // Server-side only: nothing to do on the student client.
    @Override
    public void onLogin(ClientThreadHandler handler, LoginRequest request) {
    }

    @Override
    public void onRegister(ClientThreadHandler handler, RegisterRequest request) {
    }
}
