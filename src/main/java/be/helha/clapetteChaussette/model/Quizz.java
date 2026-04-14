package be.helha.clapetteChaussette.model;

import java.util.List;
import java.util.UUID;

import be.helha.clapetteChaussette.model.question.Question;

// on stocke le choix des questions du mode d'édition
public class Quizz {
    private UUID uuid;
    private List<Question> questions;

    public Quizz(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    // méthode pour ajouter une question de n'importe quel type car les types de
    // questions héritent de Question
    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public UUID getUuid() { return uuid;}

    @Override
    public String toString() {
        return "QuestionsEdit [questions=" + questions + "]";
    }
}
