package be.helha.clapetteChaussette.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import be.helha.clapetteChaussette.model.question.Question;

// on stocke le choix des questions du mode d'édition
public class Quizz {
    private UUID uuid;
    String name;
    private List<Question> questions;

    public Quizz() {
        this.questions = new ArrayList<Question>();
        this.uuid = UUID.randomUUID();
    }

    public Quizz(String name, List<Question> questions) {
        this.name = name;
        this.questions = questions;
        this.uuid = UUID.randomUUID();
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    // méthode pour ajouter une question de n'importe quel type car les types de
    // questions héritent de Question
    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "QuestionsEdit [questions=" + questions + "]";
    }
}
