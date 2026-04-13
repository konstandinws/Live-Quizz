package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.model.dto.QuestionDTO;

import java.sql.*;

public class QuestionRepository {

    public void save(QuestionDTO question) {

        String query = "INSERT INTO question (quuid, questionnaire_uuid, content, type) values (?, ?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, question.uuid());
            statement.setObject(2, question.quuid());
            statement.setString(3, question.titre());
            statement.setString(4, question.type().name());
            statement.executeUpdate();

            saveAnswers(question, connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveAnswers(QuestionDTO question, Connection connection) throws SQLException {
        String query = "INSERT INTO reponse_question (question_uuid, reponse, bonne_reponse) values (?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            for(String answer : question.answers()) {
                statement.setObject(1, question.uuid());
                statement.setString(2, answer);
                statement.setBoolean(3, question.correctAnswers().contains(answer));
                statement.executeUpdate();
            }
        }
    }
}
