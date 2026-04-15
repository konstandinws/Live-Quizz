package be.helha.clapetteChaussette.controller;

import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.view.IQuestionListener;

public class PollController implements IQuestionListener<String>, IViewController {
    @Override
    public void validate(String answer, Question poll) {
        /**
         * by default, we return true cause a poll question is always valid, the answer
         * is not important
         */
        poll.setAnswer(answer);
    }
}
