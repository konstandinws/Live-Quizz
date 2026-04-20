package be.helha.clapetteChaussette.controller;

import be.helha.clapetteChaussette.model.question.PollQuestion;
import be.helha.clapetteChaussette.view.IQuestionListener;

public class PollController implements IQuestionListener<String>, IViewController {
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
