package be.helha.clapetteChaussette.client.controller.student;

import java.io.IOException;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.client.network.ClientThreadHandler;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.view.student.StudentInvitationViewFX;
import be.helha.clapetteChaussette.client.view.question.McqStudentViewFX;
import be.helha.clapetteChaussette.client.view.question.OpenStudentViewFX;
import be.helha.clapetteChaussette.client.view.question.PollStudentViewFX;
import be.helha.clapetteChaussette.client.view.question.StudentQuestionViewFX;
import be.helha.clapetteChaussette.client.view.question.StudentQuestionViewFX.IStudentQuestionListener;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.models.events.NextQuestionResponse;
import be.helha.clapetteChaussette.common.models.events.RevealAnswerEvent;
import be.helha.clapetteChaussette.common.models.events.SubmitAnswerRequest;
import javafx.application.Platform;

/**
 * Drives the student's live quizz screen: shows each question pushed by the
 * server and lets the student answer it.
 */
public class StudentLiveController extends BaseController
        implements IStudentQuestionListener, ClientThreadHandler.Listener {

    private final NetworkController networkController;
    private StudentQuestionViewFX currentView;
    private QuestionDTO currentQuestion;

    public StudentLiveController(NavigationService nav, NetworkController networkController) {
        super(nav);
        this.networkController = networkController;

        try {
            networkController.addListener(this);
        } catch (IOException e) {
            showError("Erreur de connexion au serveur : " + e.getMessage());
        }
    }

    /** Displays a question on the student's screen (MCQ only for now). */
    public void showQuestion(QuestionDTO question) {
        this.currentQuestion = question;
        switch (question.type()) {
            case MCQ -> {
                this.currentView = new McqStudentViewFX(question);
                navigateToView(currentView, "Question", this);
            }
            case OPEN -> {
                this.currentView = new OpenStudentViewFX(question);
                navigateToView(currentView, "Question", this);
            }
            case POLL -> {
                this.currentView = new PollStudentViewFX(question);
                navigateToView(currentView, "Sondage", this);
            }
            default -> showError("Type de question non supporté : " + question.type());
        }
    }

    @Override
    public void onAnswerSelected(int index) {
        if (currentQuestion == null) {
            return;
        }
        try {
            networkController
                    .send(new SubmitAnswerRequest(currentQuestion.uuid(), index, null, currentQuestion.type()));
            if (currentView != null) {
                currentView.lockAfterAnswer();
            }
        } catch (IOException e) {
            showError("Erreur lors de l'envoi de la réponse : " + e.getMessage());
        }
    }

    @Override
    public void onAnswerSelected(String answer) {
        if (currentQuestion == null) {
            return;
        }
        try {
            networkController
                    .send(new SubmitAnswerRequest(currentQuestion.uuid(), -1, answer, currentQuestion.type()));
            if (currentView != null) {
                currentView.lockAfterAnswer();
            }
        } catch (IOException e) {
            showError("Erreur lors de l'envoi de la réponse : " + e.getMessage());
        }
    }

    @Override
    public void onRevealAnswers(RevealAnswerEvent response) {
        Platform.runLater(() -> {
            if (currentView != null) {
                currentView.revealCorrectAnswers(response.getQuestion(), response.getVotesPerAnswer());
            }
        });
    }

    @Override
    public void onNextQuestionResponse(NextQuestionResponse response) {
        Platform.runLater(() -> {
            if (!response.isSuccess()) {
                showError("Erreur : " + response.getErrorMessage());
                return;
            }
            QuestionDTO question = response.getQuestion();
            if (question == null) {
                // End of the quizz: send the student back to the join (code) screen.
                networkController.removeListener(this);
                showError("Le quiz est terminé.");
                StudentInvitationController invitationCtrl = new StudentInvitationController(navigationService,
                        networkController);
                navigateToView(new StudentInvitationViewFX(), "Rejoindre un Quiz", invitationCtrl);
                return;
            }
            showQuestion(question);
        });
    }
}
