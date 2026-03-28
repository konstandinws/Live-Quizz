package clapetteChaussette.controller;

import clapetteChaussette.model.question.QcmQuestion;

public interface IListenerMain {
    void onQCMLaunch();

    void onPollLaunch();

    void onFreeLaunch();
}
