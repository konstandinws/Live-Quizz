package be.helha.clapetteChaussette.common.models.questions;

import be.helha.clapetteChaussette.common.models.enums.QuestionType;
import java.util.Collections;
import java.util.List;

public class McqQuestion extends Question {

    private boolean multipleChoice;

    // Vrai QCM avec plusieurs bonnes réponses
    public McqQuestion(String question, List<Answer> answers) {
        super(question, QuestionType.MCQ);
        this.answers = answers;
        this.multipleChoice = isMultipleAnswers();
    }

    public boolean isMultipleAnswers() {
        return this.answers.stream().filter(Answer::isCorrect).count() > 1;
    }

    public List<Answer> getCorrectAnswers() {
        return Collections.unmodifiableList(answers.stream().filter(Answer::isCorrect).toList());
    }

    public boolean isMultipleChoice() {
        return multipleChoice;
    }

}
