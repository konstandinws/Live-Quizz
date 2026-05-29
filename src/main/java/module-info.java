module clapelleChausette {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires io.github.cdimascio.dotenv.java;
    requires java.sql;
    requires jbcrypt;
    requires java.net.http;

    opens be.helha.clapetteChaussette.client.view to javafx.fxml;
    opens be.helha.clapetteChaussette.client.view.editor to javafx.fxml;
    opens be.helha.clapetteChaussette.client.view.question to javafx.fxml;
    opens be.helha.clapetteChaussette.client.view.student to javafx.fxml;
    opens be.helha.clapetteChaussette.client.view.teacher to javafx.fxml;

    exports be.helha.clapetteChaussette;
}