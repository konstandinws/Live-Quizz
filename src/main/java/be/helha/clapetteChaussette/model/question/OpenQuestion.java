package be.helha.clapetteChaussette.model.question;

import java.util.ArrayList;
import java.util.List;

public class OpenQuestion extends Question {

    // List of answers expected by the teacher
    private List<String> correctAnswers;
    // Indicates if the question is worth points
    private boolean pointsEnabled;

    public OpenQuestion(String question) {
        super(question);
        this.correctAnswers = new ArrayList<>();
        this.pointsEnabled = false;
    }

    public OpenQuestion(String question, List<String> correctAnswers, boolean pointsEnabled) {
        super(question); // Pass the title to the parent class 'Question'
        this.correctAnswers = correctAnswers;
        this.pointsEnabled = pointsEnabled;
    }

    // Getters and setters to manage correct answers

    // Retrieves the list of correct answers defined by the teacher
    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    // Stores the correct answers defined by the teacher
    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    // Returns whether the question is for points or not
    public boolean isPointsEnabled() {
        return pointsEnabled;
    }

    // Sets whether the question should award points
    public void setPointsEnabled(boolean pointsEnabled) {
        this.pointsEnabled = pointsEnabled;
    }
}