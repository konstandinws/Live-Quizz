package clapetteChaussette.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clapetteChaussette.model.question.OpenQuestion;
import clapetteChaussette.model.question.Poll;
import clapetteChaussette.model.question.QcmQuestion;
import clapetteChaussette.model.question.Question;
import clapetteChaussette.service.NavigationService;
import clapetteChaussette.view.*;

public class MainController implements IMainListener, IController {

    private NavigationService navigationService;

    public MainController(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    @Override
    public void onQcmLaunch() {
        String question = "que vaut 12 + 5 ?";
        List<String> answers = new ArrayList<>();
        answers.add("4");
        answers.add("12");
        answers.add("15");
        answers.add("17");
        List<String> correctAnswer = Arrays.asList("17");
        QcmQuestion qcm = new QcmQuestion(question, answers, correctAnswer);
        try {
            afficherQuestion(qcm, new QcmController(), "QCM");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPollLaunch() {
        String question = "Quelle est votre couleur préférée ?";
        Question poll = new Poll(question, List.of("Rouge", "Bleu", "Vert", "Jaune"));
        try {
            afficherQuestion(poll, new PollController(), "Poll");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFreeLaunch() {
        OpenQuestion qst = new OpenQuestion("comment tu trouves brahim physiquement ?");
        try {
            afficherQuestion(qst, new OpenController(), "Free");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onQuestionnaireLaunch() {
        return;
    }

    private void afficherQuestion(Question question, IController controller, String title) {
        /**
         * TODO : faire une classe mere View de quel toute les vues héritent pour
         * factoriser le code de navigation et d'affichage des questions.
         */
        if (question instanceof QcmQuestion) {
            try {
                IDisplayable qcmView = new QcmViewFX();
                this.navigationService.navigateTo(qcmView, title);
                ((QcmViewFX) qcmView).setQcm((QcmQuestion) question);
                ((QcmViewFX) qcmView).setController((IQuestionListener<String>) controller);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (question instanceof Poll) {
            try {
                IDisplayable pollView = new PollViewFX();
                this.navigationService.navigateTo(pollView, title);
                ((PollViewFX) pollView).setPoll((Poll) question);
                ((PollViewFX) pollView).setController((IQuestionListener<String>) controller);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (question instanceof OpenQuestion) {
            try {
                IDisplayable freeView = new OpenQuestionFX();
                this.navigationService.navigateTo(freeView, title);
                ((OpenQuestionFX) freeView).setOpenQuestion((OpenQuestion) question);
                ((OpenQuestionFX) freeView).setController((IQuestionListener<String>) controller);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Type de question non supporté");
        }
    }
}
