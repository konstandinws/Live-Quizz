package clapetteChaussette.controller;

import clapetteChaussette.view.IQuestionListener;
import clapetteChaussette.model.question.Question;

public class QcmController implements IQuestionListener<String>, IController {
    @Override
    public void validate(String answer, Question qcm) {
        qcm.setAnswer(answer);
    }
}