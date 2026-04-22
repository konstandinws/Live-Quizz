package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.exception.DatabaseException;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * JDBC implementation of the {@link IQuizzRepository}.
 * <p>
 * This repository handles the storage and retrieval of Quiz entities
 * in a relational database using JDBC.
 * </p>
 */
public class JdbcIQuizzRepository implements IQuizzRepository {

    @Override
    public void save(QuizzDTO quizz) {

        String query = "INSERT INTO questionnaire(quuid, teacher_uuid, name) values (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, quizz.uuid());
            statement.setObject(2, quizz.teacherUuid());
            statement.setString(3, quizz.name());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error while saving the quizz in the Database", e);
        }
    }

    @Override
    public List<QuizzDTO> findAll(UUID teacherUuid) {
        String query = "Select quuid, name FROM questionnaire WHERE teacher_uuid = ?";
        List<QuizzDTO> quizzes = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, teacherUuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    UUID quuid = resultSet.getObject("quuid", UUID.class);
                    String name = resultSet.getString("name");

                    quizzes.add(new QuizzDTO(quuid, name, teacherUuid));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error while finding all the quizzes of a teacher in the Database", e);
        }
        return quizzes;
    }

}
