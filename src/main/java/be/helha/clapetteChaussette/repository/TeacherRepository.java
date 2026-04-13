package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.model.Teacher;
import be.helha.clapetteChaussette.model.dao.TeacherDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class TeacherRepository {

    public void save(Teacher teacher) {

        String query = "INSERT INTO teacher (uuid, name, password_hash) values (?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, teacher.getUuid());
            statement.setString(2, teacher.getName());
            statement.setString(3, teacher.getHashed_password());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<TeacherDAO> find(String user, String hashed_password) {

        String query = "SELECT uuid, name, password_hash FROM teacher WHERE name = ? AND password_hash = ?";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user);
            statement.setString(2, hashed_password);

            ResultSet resultSet = statement.executeQuery();

            UUID uuid;
            String name;
            String h_password;

            if(resultSet.next()) {
                uuid = resultSet.getObject("uuid", UUID.class);
                name = resultSet.getString("name");
                h_password = resultSet.getString("hashed_password");

                return Optional.of(new TeacherDAO(uuid, name, h_password));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}
