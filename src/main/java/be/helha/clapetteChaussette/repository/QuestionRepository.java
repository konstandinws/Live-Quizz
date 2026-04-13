package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.model.enums.QuestionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO ameliorer la gestion des exceptions + javadoc

// Je pense que c'est possible de faire un grosse requete avec un join au lieu de faire plusieurs petites requetes comme ici

public class QuestionRepository {

    public void save(QuestionDTO question) {

        String query = "INSERT INTO question (quuid, questionnaire_uuid, content, type) values (?, ?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, question.uuid());
            statement.setObject(2, question.quizzUuid());
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

    public Optional<QuestionDTO> findByUuid(UUID questionUuid) {

        String query = "SELECT quuid, questionnaire_uuid, content, type FROM question WHERE quuid = ?";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, questionUuid);

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()) {
                    UUID quuid = resultSet.getObject("quuid", UUID.class);
                    UUID quizzUuid = resultSet.getObject("questionnaire_uuid", UUID.class);
                    String content = resultSet.getString("content");
                    String type = resultSet.getString("type");

                    List<String> answers = new ArrayList<>();
                    List<String> correctAnswers = new ArrayList<>();
                    // on remplit les 2 listes avec les choix et les bonne réponses
                    findAnswers(questionUuid, connection, answers, correctAnswers);

                    return Optional.of(new QuestionDTO(questionUuid, quizzUuid, content, answers, correctAnswers, QuestionType.valueOf(type)));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    // on va aller chercher les choix et les bonnes reponses pour chaque question
    public void findAnswers(UUID questionUuid, Connection connection, List<String> answers, List<String> correctAnswers) throws SQLException {
        String query = "SELECT reponse, bonne_reponse FROM reponse_question WHERE question_uuid = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, questionUuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    String answer = resultSet.getString("reponse");
                    boolean correctAnswer = resultSet.getBoolean("bonne_reponse");
                    answers.add(answer);
                    if (correctAnswer) correctAnswers.add(answer);
                }
            }
        }
    }
}
