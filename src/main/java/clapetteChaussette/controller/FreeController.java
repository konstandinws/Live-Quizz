package clapetteChaussette.controller;
import clapetteChaussette.view.IOpenListener;

public class FreeController implements IOpenListener {
    @Override
    public void validate(String aws) {
        System.out.println("Réponse enregistré : " + aws);
    }
}
