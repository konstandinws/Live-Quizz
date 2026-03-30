package clapetteChaussette.view;

import clapetteChaussette.view.MainViewFX.Listener;

import javafx.event.ActionEvent;

public class MainView {

    private Listener listener;

    public void launchQCM(ActionEvent event) {
        listener.onQcmLaunch();
    }

    public void launchPoll(ActionEvent event) {
        listener.onPollLaunch();
    }

    public void launchFree(ActionEvent event) {
        listener.onFreeLaunch();
    }

}
