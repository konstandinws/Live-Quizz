package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.exception.DatabaseException;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

/**
 * JDBC implementation of the {@link ITeacherRepository}.
 * <p>
 * This repository is responsible for managing the persistence of Teacher
 * entities
 * in a relational database using raw JDBC connections.
 * </p>
 */
public class JdbcITeacherRepository implements ITeacherRepository {

    @Override
    public void save(TeacherDTO teacher) {

        String query = "INSERT INTO teacher (uuid, name, password_hash) values (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, teacher.uuid());
            statement.setString(2, teacher.name());
            statement.setString(3, teacher.hashedPassword());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error while saving the teacher in the Database", e);

        }
    }

    @Override
    public Optional<TeacherDTO> findByName(String name) {
        String query = "SELECT uuid, name, password_hash FROM teacher WHERE name = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                    UUID uuid = resultSet.getObject("uuid", UUID.class);
                    String teacherName = resultSet.getString("name");
                    String hashedPassword = resultSet.getString("password_hash");

                    return Optional.of(new TeacherDTO(uuid, name, hashedPassword));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error while finding the teacher in the Database", e);
        }

        return Optional.empty();
    }
}
