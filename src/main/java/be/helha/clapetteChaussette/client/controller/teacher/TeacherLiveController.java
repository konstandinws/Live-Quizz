package be.helha.clapetteChaussette.client.controller.teacher;

import java.io.IOException;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.client.network.ClientThreadHandler;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.view.ControlerComponent.ErrorPopup;
import be.helha.clapetteChaussette.client.view.question.McqTeacherViewFX;
import be.helha.clapetteChaussette.client.view.question.McqTeacherViewFX.ITeacherLiveListener;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.models.events.AnswerReceivedEvent;
import be.helha.clapetteChaussette.common.models.events.NextQuestionRequest;
import be.helha.clapetteChaussette.common.models.events.NextQuestionResponse;
import be.helha.clapetteChaussette.common.models.events.ParticipantCountUpdate;
import javafx.application.Platform;

/**
 * Drives the teacher's live quizz screen: shows the current question and keeps
 * the participant counter up to date. The server is authoritative on which
 * question is current, so "next" only asks it to advance.
 */
public class TeacherLiveController extends BaseController
        implements ITeacherLiveListener, ClientThreadHandler.Listener {

    private final NetworkController networkController;
    private final QuizzDTO quizz;
    private int participantCount;
    private McqTeacherViewFX currentView;

    public TeacherLiveController(NavigationService nav, NetworkController networkController, QuizzDTO quizz,
            int participantCount) {
        super(nav);
        this.networkController = networkController;
        this.quizz = quizz;
        this.participantCount = participantCount;

        try {
            networkController.addListener(this);
        } catch (IOException e) {
            ErrorPopup.show("Erreur de connexion au serveur : " + e.getMessage());
        }
    }

    /** Displays a question on the teacher's live screen (MCQ only for now). */
    public void showQuestion(QuestionDTO question) {
        switch (question.type()) {
            case MCQ -> {
                this.currentView = new McqTeacherViewFX(question, participantCount);
                navigateToView(currentView, "Question en cours", this);
            }
            default -> ErrorPopup.show("Type de question non supporté : " + question.type());
        }
    }

    @Override
    public void onNextQuestionRequested() {
        try {
            // The server owns the index; it only needs to locate the session (via this
            // connection), so the index sent here is ignored server-side.
            networkController.send(new NextQuestionRequest(quizz, 0));
        } catch (IOException e) {
            ErrorPopup.show("Erreur lors du passage à la question suivante : " + e.getMessage());
        }
    }

    @Override
    public void onNextQuestionResponse(NextQuestionResponse response) {
        Platform.runLater(() -> {
            if (!response.isSuccess()) {
                ErrorPopup.show("Erreur : " + response.getErrorMessage());
                return;
            }
            QuestionDTO question = response.getQuestion();
            if (question == null) {
                // A null question signals the end of the quizz.
                ErrorPopup.show("Le quiz est terminé.");
                return;
            }
            showQuestion(question);
        });
    }

    @Override
    public void onParticipantCountUpdate(ParticipantCountUpdate update) {
        Platform.runLater(() -> {
            this.participantCount = update.getParticipantCount();
            if (currentView != null) {
                currentView.setParticipantCount(participantCount);
            }
        });
    }

    @Override
    public void onAnswerReceived(AnswerReceivedEvent event) {
        Platform.runLater(() -> {
            if (currentView != null) {
                currentView.setVoteProgress(event.getAnsweredCount(), event.getParticipantCount());
                currentView.setVotes(event.getVotesPerAnswer());
            }
        });
    }
}
