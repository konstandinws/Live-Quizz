package be.helha.clapetteChaussette.controller.question;

import be.helha.clapetteChaussette.controller.BaseController;
import be.helha.clapetteChaussette.model.question.OpenQuestion;
import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.view.IQuestionListener;

public class OpenController extends BaseController implements IQuestionListener<String> {
    private OpenQuestion question;

    public OpenController(Question question) {
        this.question = (OpenQuestion) question;
    }

    @Override
    public void validate(String aws) {
        question.setResponse(aws);
    }
}
