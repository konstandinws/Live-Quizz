package be.helha.clapetteChaussette.controller;

import be.helha.clapetteChaussette.model.question.OpenQuestion;
import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.view.IQuestionListener;

public class OpenController implements IQuestionListener<String>, IViewController {
    private OpenQuestion question;

    public OpenController(Question question) {
        this.question = (OpenQuestion) question;
    }

    @Override
    public void validate(String aws) {
        question.setResponse(aws);
    }
}
