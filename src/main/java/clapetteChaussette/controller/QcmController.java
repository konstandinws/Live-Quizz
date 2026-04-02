package clapetteChaussette.controller;

import clapetteChaussette.view.IQuestionListener;
import clapetteChaussette.model.question.QcmQuestion;
import clapetteChaussette.model.question.Question;

class QcmController implements IQuestionListener<String> {
    @Override
    public boolean validate(String answer, Question qcm) {
        if (answer.equals(((QcmQuestion) qcm).getCorrectAnswer())) {
            return true;
        } else {
            return false;
        }
    }
}