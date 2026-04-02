package clapetteChaussette.controller;

import clapetteChaussette.model.question.Question;
import clapetteChaussette.view.IQuestionListener;

public class PollController implements IQuestionListener<String> {
    @Override
    public void validate(String answer, Question poll) {
        /**
         * by default, we return true cause a poll question is always valid, the answer
         * is not important
         */
        poll.setAnswer(answer);
    }
}
