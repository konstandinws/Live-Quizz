module clapelleChausette {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.github.cdimascio.dotenv.java;
    requires java.sql;

    opens be.helha.clapetteChaussette.view to javafx.fxml;

    exports be.helha.clapetteChaussette;
}