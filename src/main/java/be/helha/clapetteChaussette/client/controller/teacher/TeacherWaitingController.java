package be.helha.clapetteChaussette.client.controller.teacher;

import java.io.IOException;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.client.network.ClientThreadHandler;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.network.ClientThreadHandler.Listener;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.view.ControlerComponent.ErrorPopup;
import be.helha.clapetteChaussette.client.view.teacher.TeacherWaitingViewFX;
import be.helha.clapetteChaussette.client.view.teacher.TeacherWaitingViewFX.ITeacherWaitingListener;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.models.events.NextQuestionRequest;
import be.helha.clapetteChaussette.common.models.events.NextQuestionResponse;
import be.helha.clapetteChaussette.common.models.events.ParticipantCountUpdate;
import be.helha.clapetteChaussette.common.models.events.ParticipantNameUpdate;
import javafx.application.Platform;

public class TeacherWaitingController extends BaseController
        implements ITeacherWaitingListener, ClientThreadHandler.Listener {
    private final QuizzDTO quizz;
    private final TeacherWaitingViewFX teacherWaitingView;
    private final NetworkController networkController;
    private int participantCount = 0;

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
        this.participantCount = update.getParticipantCount();
        Platform.runLater(() -> {
            if (teacherWaitingView != null) {
                teacherWaitingView.setParticipantCount(participantCount);
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
        try {
            networkController.send(new NextQuestionRequest(quizz, 0));
        } catch (IOException e) {
            ErrorPopup.show("Erreur lors de l'envoi de la requête de lancement : " + e.getMessage());
        }
    }

    @Override
    public void onNextQuestionResponse(NextQuestionResponse nextQuestionResponse) {
        Platform.runLater(() -> {
            if (!nextQuestionResponse.isSuccess()) {
                ErrorPopup.show("Erreur lors du lancement du quizz : " + nextQuestionResponse.getErrorMessage());
                return;
            }

            QuestionDTO question = nextQuestionResponse.getQuestion();
            if (question == null) {
                // No question to show (e.g. an empty quizz): stay on the waiting screen.
                return;
            }

            // Hand over from the waiting screen to the live quizz screen.
            networkController.removeListener(this);
            TeacherLiveController liveController = new TeacherLiveController(navigationService, networkController,
                    quizz, participantCount);
            liveController.showQuestion(question);
        });
    }
}
