module clapelleChausette {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires io.github.cdimascio.dotenv.java;
    requires java.sql;
    requires jbcrypt;
    requires java.net.http;

    opens be.helha.clapetteChaussette.view to javafx.fxml;
    opens be.helha.clapetteChaussette.view.editor to javafx.fxml;
    opens be.helha.clapetteChaussette.view.question to javafx.fxml;

    exports be.helha.clapetteChaussette;
}