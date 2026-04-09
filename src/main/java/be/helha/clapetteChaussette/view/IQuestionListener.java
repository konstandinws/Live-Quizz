package be.helha.clapetteChaussette.view;

import be.helha.clapetteChaussette.model.question.Question;

public interface IQuestionListener<T> {
    void validate(T answer, Question question);
}
