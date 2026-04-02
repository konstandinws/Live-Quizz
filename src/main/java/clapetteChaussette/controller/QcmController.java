package clapetteChaussette.controller;

import clapetteChaussette.view.IQuestionListener;
import clapetteChaussette.model.question.Question;

class QcmController implements IQuestionListener<String> {
    @Override
    public void validate(String answer, Question qcm) {
        qcm.setAnswer(answer);
    }
}
