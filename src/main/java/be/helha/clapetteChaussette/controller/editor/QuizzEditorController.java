package be.helha.clapetteChaussette.controller.editor;

import java.util.ArrayList;

import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.question.Poll;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.view.QuizzFX.IQuestionnaireListener;
import be.helha.clapetteChaussette.view.OpenEditorFX;
import be.helha.clapetteChaussette.view.QcmEditorFX;

// Controleur de l'édition du questionnaire, c'est lui qui manipulera le modèle
public class QuizzEditorController implements IQuestionnaireListener {
    private Quizz sessionEnCours; // le modele
    private NavigationService navigationService;

    // au démarrage on crée un questionnaire vide
    public QuizzEditorController(NavigationService navigationService) {
        this.navigationService = navigationService;
        this.sessionEnCours = new Quizz();
    }

    @Override
    public void onAddQcmRequested() {
        QcmEditorController qcmController = new QcmEditorController();
        QcmEditorFX QcmEditorFX = new QcmEditorFX();
        QcmEditorFX.setOwnViewController(qcmController);
        navigationService.navigateTo(QcmEditorFX, "QCM Editor");
    }

    @Override
    public void onAddPollRequested() {
        Poll nouvelleQuestion = new Poll("Nouvelle question Poll", new ArrayList<>());
        sessionEnCours.addQuestion(nouvelleQuestion);
    }

    @Override
    public void onAddQuestionFreeRequested() {
        OpenEditorController openCtrl = new OpenEditorController();
        OpenEditorFX view = new OpenEditorFX();
        view.setOwnViewController(openCtrl);
        navigationService.navigateTo(view, "Éditeur Question Libre");
    }

    @Override
    public void onSaveRequested() {
        // appeler le main controller pour afficher le questionnaire
    }

}
