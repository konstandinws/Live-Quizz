package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.model.dto.QuestionDTO;

import java.sql.*;
import java.util.Optional;

public class QuestionRepository {

    public void save(QuestionDTO question) {

        String query = "INSERT INTO question (quuid, questionnaire_uuid, content, type) values (?, ?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, question.uuid());
            statement.setObject(2, question.questionnaireUuid());
            statement.setString(3, question.titre());
            statement.setString(4, question.type().name());
            statement.executeUpdate();

            saveAnswers(question, connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // cette methode va etre appele dans save pour ecrire en cascade les choix possibles d'une question
    // QuestionDTO est super important ici c'est ca qui m'a permis de generaliser la creation sans devoir modifier tous les models
    private void saveAnswers(QuestionDTO question, Connection connection) throws SQLException {
        String query = "INSERT INTO reponse_question (question_uuid, reponse, bonne_reponse) values (?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            for(String answer : question.answers()) {
                statement.setObject(1, question.uuid());
                statement.setString(2, answer);
                statement.setBoolean(3, question.correctAnswers().contains(answer));
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }


}
