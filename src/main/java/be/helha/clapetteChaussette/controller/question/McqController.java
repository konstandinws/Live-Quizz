package be.helha.clapetteChaussette.controller.question;

import be.helha.clapetteChaussette.controller.BaseController;
import be.helha.clapetteChaussette.model.question.McqQuestion;
import be.helha.clapetteChaussette.view.IQuestionListener;

public class McqController extends BaseController implements IQuestionListener<String> {
    private McqQuestion question;

    public McqController(McqQuestion question) {
        this.question = question;
    }

    @Override
    public void validate(String answers) {
        question.setResponse(answers);
    }
}
