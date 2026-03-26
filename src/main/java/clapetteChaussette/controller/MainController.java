package clapetteChaussette.controller;

public class MainController implements IListenerMain {

    @Override
    public void onQCMLaunch() {
        System.out.println("QCM launched");
        // launch new view
    }

    @Override
    public void onPollLaunch() {
        System.out.println("Poll launched");
        // launch new view
    }

    @Override
    public void onFreeLaunch() {
        System.out.println("Free launched");
        // launch new view
    }
}