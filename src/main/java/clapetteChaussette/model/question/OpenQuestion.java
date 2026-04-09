package clapetteChaussette.model.question;

import java.util.ArrayList;
import java.util.List;

public class OpenQuestion extends Question {

    // La liste de réponses que le prof attend
    private List<String> correctAnswers;
    // Savoir si la question compte pour des point
    private boolean pointsEnabled;

    public OpenQuestion(String question) {
        super(question);
        this.correctAnswers = new ArrayList<>();
        this.pointsEnabled = false;
    }

    // ici j'ai mets "guetteur" et "setteur" qui gères les bonne réponses

    // cette methode va permettre de lire la liste de bonne réponses du prof
    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    // cette ligne va permettre d'enregistrer les bonne réponses du prof
    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    // si cette methode qui dit si oui ou non c'est pour des point
    public boolean isPointsEnabled() {
        return pointsEnabled;
    }

    // si je coche la case avec des point cette methode renvoi true
    public void setPointsEnabled(boolean pointsEnabled) {
        this.pointsEnabled = pointsEnabled;
    }
}
