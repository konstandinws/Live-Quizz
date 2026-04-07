package clapetteChaussette.controller;

import clapetteChaussette.model.question.Poll;
import clapetteChaussette.model.question.OpenQuestion;
import clapetteChaussette.model.question.Questionnaire; // importer le modèle
import clapetteChaussette.service.NavigationService;
import clapetteChaussette.view.IQuestionnaireListener;
import clapetteChaussette.view.QcmEditorFX;
import java.util.ArrayList;

// Controleur de l'édition du questionnaire, c'est lui qui manipulera le modèle
public class QuestionnaireController implements IQuestionnaireListener, IController {
    private Questionnaire sessionEnCours; // le modele
    private NavigationService navigationService;

    // au démarrage on crée un questionnaire vide
    public QuestionnaireController(NavigationService navigationService) {
        this.navigationService = navigationService;
        this.sessionEnCours = new Questionnaire(new ArrayList<>());
    }

    @Override
    public void onAddQcmRequested() {
        QcmEditorController qcmController = new QcmEditorController();
        QcmEditorFX QcmEditorFX = new QcmEditorFX();
        QcmEditorFX.setController(qcmController);
        navigationService.navigateTo(QcmEditorFX, "QCM Editor");
    }

    @Override
    public void onAddPollRequested() {
        Poll nouvelleQuestion = new Poll("Nouvelle question Poll", new ArrayList<>());
        sessionEnCours.addQuestion(nouvelleQuestion);
    }

    @Override
    public void onAddQuestionFreeRequested() {
        OpenQuestion nouvelleQuestion = new OpenQuestion("Nouvelle question libre");
        sessionEnCours.addQuestion(nouvelleQuestion);
    }

    @Override
    public void onSaveRequested() {
        // appeler le main controller pour afficher le questionnaire
    }

}
