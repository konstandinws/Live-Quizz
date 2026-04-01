package clapetteChaussette.controller;
import clapetteChaussette.view.IFreeListener;

public class FreeController implements IFreeListener {
    @Override
    public void validate(String aws) {
        System.out.println("Réponse enregistré : " + aws);
    }
}
