package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.model.question.Question;

import java.sql.*;
import java.util.UUID;

public class QuestionRepository {

    // en gros on envoie la question la question et le l'uuid du questionnaire
    // car quand on fait save on pourra recuperer ce dernier
    // sinon on peut faire une variable quizzUuid dans question
    static void save(Question question) {

        String query = "INSERT INTO question (quuid, questionnaire_uuid, content, type) values (?, ?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, question.getQuuid());
            statement.setObject(2, question.getQuestionnaireUuid());
            statement.setString(3, question.getQuestion());
            statement.setString(4, question.getType().toString());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
