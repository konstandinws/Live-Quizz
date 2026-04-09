package be.helha.clapetteChaussette.controller;

import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.view.IQuestionListener;

public class OpenController implements IQuestionListener<String>, IController {
    @Override
    public void validate(String aws, Question question) {
        question.setAnswer(aws);
    }
}
