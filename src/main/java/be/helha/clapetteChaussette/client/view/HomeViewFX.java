package be.helha.clapetteChaussette.client.view;

import be.helha.clapetteChaussette.client.controller.HomeController;
import javafx.fxml.FXML;

public class HomeViewFX extends ViewFX<HomeController> {

    @FXML
    private void handleTeacher() {
        listener.onTeacherSelected();
    }

    @FXML
    private void handleStudent() {
        listener.onStudentSelected();
    }

    @Override
    protected String getFXMLPath() {
        return "home.fxml";
    }

    public interface IHomeListener extends IListener {
        void onTeacherSelected();
        void onStudentSelected();
    }
}
