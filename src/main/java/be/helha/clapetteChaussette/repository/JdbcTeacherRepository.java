package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.exception.DatabaseException;
import be.helha.clapetteChaussette.model.dto.Credentials;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.model.dto.TeacherResponseDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;


/**
 * JDBC implementation of the {@link TeacherRepository}.
 * <p>
 * This repository is responsible for managing the persistence of Teacher entities
 * in a relational database using raw JDBC connections.
 * </p>
 */
public class JdbcTeacherRepository implements TeacherRepository{

    @Override
    public void save(TeacherDTO teacher) {

        String query = "INSERT INTO teacher (uuid, name, password_hash) values (?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, teacher.uuid());
            statement.setString(2, teacher.name());
            statement.setString(3, teacher.hashed_password());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error while saving the teacher in the Database", e);

        }
    }


    @Override
    public Optional<TeacherResponseDTO> find(Credentials cred) {

        String query = "SELECT uuid, name FROM teacher WHERE name = ? AND password_hash = ?";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, cred.name());
            statement.setString(2, cred.hashed_password());

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()) {
                    UUID uuid = resultSet.getObject("uuid", UUID.class);
                    String name = resultSet.getString("name");

                    return Optional.of(new TeacherResponseDTO(uuid, name));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error while finding the teacher in the Database", e);
        }

        return Optional.empty();
    }
}
