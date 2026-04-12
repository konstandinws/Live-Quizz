package be.helha.clapetteChaussette.repository;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbService {

    static void main() {
        Dotenv dotenv = Dotenv.load();

        String url = "jdbc:postgresql://%s:%s/%s".formatted(dotenv.get("DATABASEHOST"), dotenv.get("DATABASEPORT"), dotenv.get("DATABASENAME"));
        String user = dotenv.get("DATABASEUSER");
        String password = dotenv.get("DATABASEPASSWORD");

        Connection con;

        {
            try {
                con = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
