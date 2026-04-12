package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.model.question.Answerable;
import be.helha.clapetteChaussette.model.question.Question;

import java.sql.*;
import java.util.UUID;

public class QuestionRepository {

    public void save(Question question) {

        String query = "INSERT INTO question (quuid, questionnaire_uuid, content, type) values (?, ?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, question.getQuuid());
            statement.setObject(2, question.getQuestionnaireUuid());
            statement.setString(3, question.getQuestion());
            statement.setString(4, question.getType().toString());
            statement.executeUpdate();

            // we verify because open question can have no correct answers
            if(!question.getAnswers().isEmpty()) {

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveAnswers(Question question, Connection connection) throws SQLException {
        String query = "INSERT INTO reponse_question (question_uuid, reponse, bonne_reponse) values (?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            for(String answer : question.getAnswers()) {
                statement.setObject(1, question.getQuuid());
                statement.setString(2, answer);

                // a continuer c'est chaud la faut le modele
            }
        }
    }
}
