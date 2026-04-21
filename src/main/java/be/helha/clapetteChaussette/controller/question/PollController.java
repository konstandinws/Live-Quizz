package be.helha.clapetteChaussette.controller.question;

import be.helha.clapetteChaussette.controller.BaseController;
import be.helha.clapetteChaussette.model.question.PollQuestion;
import be.helha.clapetteChaussette.view.IQuestionListener;

public class PollController extends BaseController implements IQuestionListener<String> {
    private PollQuestion poll;

    public PollController(PollQuestion poll) {
        this.poll = poll;
    }

    @Override
    public void validate(String answer) {
        /**
         * by default, we return true cause a poll question is always valid, the answer
         * is not important
         */
        poll.setResponse(answer);
    }
}
