package clapetteChaussette.controller;

import clapetteChaussette.model.question.Question;
import clapetteChaussette.view.IQuestionListener;

public class OpenController implements IQuestionListener<String> {
    @Override
    public void validate(String aws, Question question) {
        question.setAnswer(aws);
    }
}
