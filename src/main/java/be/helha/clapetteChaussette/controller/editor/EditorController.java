package be.helha.clapetteChaussette.controller.editor;

import be.helha.clapetteChaussette.controller.BaseController;
import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.service.EditorService;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.service.ValidateQuestionEditorService;
import java.util.List;

public class EditorController extends BaseController {
    private Quizz quizz;
    private EditorService editorService;
    private ValidateQuestionEditorService validateService;

    public EditorController(Quizz quizz,
                            EditorService editorService,
                            NavigationService navigationService,
                            ValidateQuestionEditorService validateService) {
        super(navigationService);
        this.quizz = quizz;
        this.editorService = editorService;
        this.validateService = validateService;
    }

    //  MÉTHODES POUR LES BOUTONS DU QUIZZ EDITOR

    public void onAddMcqRequested() {
        System.out.println("Ouverture éditeur QCM");
    }

    public void onAddPollRequested() {
        System.out.println("Ouverture éditeur Sondage");
    }

    public void onAddQuestionFreeRequested() {
        System.out.println("Ouverture éditeur Question Ouverte");
    }

    public void onSaveRequested() {
        System.out.println("Sauvegarde du Quiz complet");
    }



    public void createOpenQuestion(String title, List<String> answers, boolean hasPoints) {
        System.out.println("Question reçue : " + title);
        System.out.println("Réponses possibles : " + answers);
        System.out.println("Points activés : " + hasPoints);


    }



    /**
     * Appelée par McqEditorFX pour créer un QCM
     */
    public void createMcqQuestion(String title, List<String> answers, List<Boolean> correctAnswers) {
        System.out.println("QCM reçu !");
        System.out.println("Titre : " + title);
        System.out.println("Nombre de choix : " + answers.size());


    }

    /**
     * Cette méthode est appelée par le PollEditorFX quand on clique sur sauvegarder
     * @param title Le titre du sondage
     * @param answers La liste des options de réponse
     */
    public void createPoll(String title, List<String> answers) {
        System.out.println("Sondage reçu dans le contrôleur !");
        System.out.println("Titre : " + title);
        System.out.println("Options : " + answers);


    }
}