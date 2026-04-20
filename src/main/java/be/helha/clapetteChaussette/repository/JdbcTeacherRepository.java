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

    /**
     * Saves a new teacher record into the database.
     *
     * @param teacher The {@link TeacherDTO} object containing the teacher's unique identifier,
     * name, and hashed password.
     * @throws DatabaseException if a database access error occurs or the SQL insertion fails.
     */
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

    /**
     * Retrieves a teacher from the database based on the provided credentials.
     * <p>
     * Returns a {@link TeacherResponseDTO} which safely excludes sensitive data
     * such as the password hash.
     * </p>
     *
     * @param cred The {@link Credentials} object containing the teacher's name and hashed password to match.
     * @return An {@link Optional} containing the {@link TeacherResponseDTO} if the credentials match an existing record,
     * or an empty {@link Optional} if no match is found.
     * @throws DatabaseException if a database access error occurs during the query execution.
     */
    @Override
    public Optional<TeacherResponseDTO> find(Credentials cred) {

        String query = "SELECT uuid, name, password_hash FROM teacher WHERE name = ? AND password_hash = ?";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, cred.name());
            statement.setString(2, cred.hashed_password());

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()) {
                    UUID uuid = resultSet.getObject("uuid", UUID.class);
                    String name = resultSet.getString("name");
                    // je veux pas renvoyer le hash du mdp fix degeu pour l'instant
                    // je pourrait faire 2 DTO dinstinct un pour l'envoi et un pour la reception
                    return Optional.of(new TeacherResponseDTO(uuid, name));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error while finding the teacher in the Database", e);

        }

        return Optional.empty();
    }
}
