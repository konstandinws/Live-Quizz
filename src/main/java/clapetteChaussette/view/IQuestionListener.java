package clapetteChaussette.view;

import clapetteChaussette.model.question.Question;

public interface IQuestionListener<T> {
    boolean validate(T answer, Question question);
}
