package be.helha.clapetteChaussette.model;

import java.util.List;

import be.helha.clapetteChaussette.model.question.Question;

// on stocke le choix des questions du mode d'édition
public class Questionnaire {
    private List<Question> questions;

    public Questionnaire(List<Question> questions) {
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

    @Override
    public String toString() {
        return "QuestionsEdit [questions=" + questions + "]";
    }
}
