package clapetteChaussette.controller;

import clapetteChaussette.model.question.QcmQuestion;
import clapetteChaussette.model.question.Poll;
import clapetteChaussette.model.question.QuestionFree;
import clapetteChaussette.model.question.Questionnaire; // importer le modèle
import clapetteChaussette.view.IQuestionnaireListener; // importer l'interface pour communiquer avec la vue
import java.util.ArrayList; // importer la classe ArrayList pour stocker les questions

// Controleur de l'édition du questionnaire, c'est lui qui manipulera le modèle
public class QuestionnaireController implements IQuestionnaireListener {
    private Questionnaire sessionEnCours; // le modele

    // au démarrage on crée un questionnaire vide
    public QuestionnaireController(){
        this.sessionEnCours = new Questionnaire(new ArrayList<>());
    }

    @Override
    public void onAddQcmRequested(){
        // préparer les listes vides car le constructeur de QcmQuestion l'exige
        ArrayList<String> réponsesVides = new ArrayList<>();
        // créer le QCM avec les 3 arguments requis
        QcmQuestion nouvelleQuestion = new QcmQuestion("Nouvelle question QCM", réponsesVides, "");
        // on l'ajoute à la liste de questions du modèle
        sessionEnCours.addQuestion(nouvelleQuestion);
    }

    @Override
    public void onAddPollRequested(){
        Poll nouvelleQuestion = new Poll("Nouvelle question Poll", new ArrayList<>());
        sessionEnCours.addQuestion(nouvelleQuestion);
    }

    @Override
    public void onAddQuestionFreeRequested(){
        QuestionFree nouvelleQuestion = new QuestionFree("Nouvelle question libre");
        sessionEnCours.addQuestion(nouvelleQuestion);
    }

    @Override
    public void onSaveRequested(){
        // appeler le main controller pour afficher le questionnaire
    }


}
