package be.helha.clapetteChaussette.repository.db;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// TODO javadoc

public class DatabaseConnection {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String url = "jdbc:postgresql://%s:%s/%s".formatted(dotenv.get("DATABASEHOST"),
            dotenv.get("DATABASEPORT"), dotenv.get("DATABASENAME"));
    private static final String user = dotenv.get("DATABASEUSER");
    private static final String password = dotenv.get("DATABASEPASSWORD");

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
}
