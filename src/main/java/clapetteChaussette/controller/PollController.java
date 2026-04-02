package clapetteChaussette.controller;

import clapetteChaussette.view.IPoolListener;
import clapetteChaussette.model.question.Question;

public class PollController implements IPoolListener {
    @Override
    public boolean validate(String answer, Question question) {
        /**
         * by default, we return true cause a poll question is always valid, the answer
         * is not important
         */
        return true;
    }
}
