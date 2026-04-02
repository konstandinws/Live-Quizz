package clapetteChaussette.controller;

import clapetteChaussette.model.question.Question;
import clapetteChaussette.view.IOpenListener;

public class OpenController implements IOpenListener {
    @Override
    public boolean validate(String aws, Question question) {
        System.out.println("Réponse enregistré : " + aws);
        return true;
    }
}
