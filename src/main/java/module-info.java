module clapelleChausette {
    requires javafx.controls;
    requires javafx.fxml;

    opens be.helha.clapetteChaussette.view to javafx.fxml;

    exports be.helha.clapetteChaussette;
}