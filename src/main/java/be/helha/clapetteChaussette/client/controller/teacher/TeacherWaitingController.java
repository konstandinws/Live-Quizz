package be.helha.clapetteChaussette.client.controller.teacher;

import java.io.IOException;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.client.network.ClientThreadHandler;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.view.ControlerComponent.ErrorPopup;
import be.helha.clapetteChaussette.client.view.teacher.TeacherWaitingViewFX;
import be.helha.clapetteChaussette.client.view.teacher.TeacherWaitingViewFX.ITeacherWaitingListener;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.models.events.ParticipantCountUpdate;
import be.helha.clapetteChaussette.common.models.events.ParticipantNameUpdate;
import javafx.application.Platform;

public class TeacherWaitingController extends BaseController
        implements ITeacherWaitingListener, ClientThreadHandler.Listener {
    private final QuizzDTO quizz;
    private final TeacherWaitingViewFX teacherWaitingView;
    private final NetworkController networkController;

    public TeacherWaitingController(NavigationService nav, QuizzDTO quizz, NetworkController networkController,
            TeacherWaitingViewFX teacherWaitingView) {
        super(nav);
        this.quizz = quizz;
        this.networkController = networkController;
        this.teacherWaitingView = teacherWaitingView;

        try {
            networkController.addListener(this);
        } catch (IOException e) {
            ErrorPopup.show("Erreur de connexion au serveur : " + e.getMessage());
        }
    }

    @Override
    public void onParticipantCountUpdate(ParticipantCountUpdate update) {
        Platform.runLater(() -> {
            if (teacherWaitingView != null) {
                teacherWaitingView.setParticipantCount(update.getParticipantCount());
            }
        });
    }

    @Override
    public void onParticipantNameUpdate(ParticipantNameUpdate update) {
        Platform.runLater(() -> {
            if (teacherWaitingView != null) {
                teacherWaitingView.setParticipantNames(update.getParticipantNames());
            }
        });
    }

    @Override
    public void onStartQuizzRequested() {
        // TODO: lancer le quiz via le serveur omg je peu le faire c'est trop ezzzz

    }
}
