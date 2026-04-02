package clapetteChaussette.controller;

import clapetteChaussette.view.IQcmListener;
import clapetteChaussette.model.question.QcmQuestion;
import clapetteChaussette.model.question.Question;

class QcmController implements IQcmListener {
    @Override
    public boolean validate(String answer, Question qcm) {
        if (answer.equals(((QcmQuestion) qcm).getCorrectAnswer())) {
            return true;
        } else {
            return false;
        }
    }
}