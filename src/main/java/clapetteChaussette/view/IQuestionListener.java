package clapetteChaussette.view;

import clapetteChaussette.model.question.Question;

public interface IQuestionListener<T> {
    void validate(T answer, Question question);
}
