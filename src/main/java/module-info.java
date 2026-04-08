module clapelleChausette {
    requires javafx.controls;
    requires javafx.fxml;

    opens clapetteChaussette.view to javafx.fxml;

    exports clapetteChaussette;
}