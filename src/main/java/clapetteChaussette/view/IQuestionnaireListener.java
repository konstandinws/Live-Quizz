package clapetteChaussette.view;

// interface qui permet à la vue de prévenir le contrôleur du questionnaire
public interface IQuestionnaireListener {
    void onAddQcmRequested();
    void onAddPollRequested();
    void onAddQuestionFreeRequested();
    void onSaveRequested(); // quand l'éditeur a fini d'ajouter des questions.


}
