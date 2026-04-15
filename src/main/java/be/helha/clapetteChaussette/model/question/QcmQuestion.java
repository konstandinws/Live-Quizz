package be.helha.clapetteChaussette.model.question;

import be.helha.clapetteChaussette.model.enums.QuestionType;

import java.util.Collections;
import java.util.List;

public class QcmQuestion extends Question {

    private boolean multipleChoice;

    // Vrai QCM avec plusieurs bonnes réponses
    public QcmQuestion(String question, List<Anwser> answers) {
        super(question, QuestionType.QCM);
        this.answers = answers;
        this.multipleChoice = isMultipleAnswers();
    }

    public boolean isMultipleAnswers() {
        return this.answers.stream().filter(Anwser::isCorrect).count() > 1;
    }

    public List<Anwser> getCorrectAnswers() {
        return Collections.unmodifiableList(answers.stream().filter(Anwser::isCorrect).toList());
    }

    public boolean isMultipleChoice() {
        return multipleChoice;
    }

}
