package be.helha.clapetteChaussette.client.controller.student;

import java.io.IOException;

import be.helha.clapetteChaussette.client.controller.BaseController;
import be.helha.clapetteChaussette.client.network.ClientThreadHandler;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.client.service.NavigationService;
import be.helha.clapetteChaussette.client.view.ControlerComponent.ErrorPopup;
import be.helha.clapetteChaussette.client.view.question.McqStudentViewFX;
import be.helha.clapetteChaussette.client.view.question.OpenStudentViewFX;
import be.helha.clapetteChaussette.client.view.question.StudentQuestionViewFX;
import be.helha.clapetteChaussette.client.view.question.StudentQuestionViewFX.IStudentQuestionListener;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.models.events.NextQuestionResponse;
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
            ErrorPopup.show("Erreur de connexion au serveur : " + e.getMessage());
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
            default -> ErrorPopup.show("Type de question non supporté : " + question.type());
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
            ErrorPopup.show("Erreur lors de l'envoi de la réponse : " + e.getMessage());
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
            ErrorPopup.show("Erreur lors de l'envoi de la réponse : " + e.getMessage());
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
                ErrorPopup.show("Le quiz est terminé.");
                return;
            }
            showQuestion(question);
        });
    }
}
