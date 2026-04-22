package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.exception.DatabaseException;
import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.enums.QuestionType;
import be.helha.clapetteChaussette.model.question.Answer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//TODO add a way to find all questions of a quizz from the quizz uuid

/**
 * JDBC implementation of the {@link IQuestionRepository}.
 * <p>
 * This repository is responsible for managing the persistence of Question entities
 * and their corresponding Answers in a relational database using raw JDBC connections.
 * </p>
 */
public class JdbcIQuestionRepository implements IQuestionRepository {

    @Override
    public void save(QuestionDTO question) {

        String query = "INSERT INTO question (quuid, questionnaire_uuid, content, type) values (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, question.uuid());
            statement.setObject(2, question.quizzUuid());
            statement.setString(3, question.titre());
            statement.setString(4, question.type().name());
            statement.executeUpdate();

            saveAnswers(question, connection);

        } catch (SQLException e) {
            throw new DatabaseException("Error while saving the question in the Database", e);

        }
    }

    private void saveAnswers(QuestionDTO question, Connection connection) throws SQLException {
        String query = "INSERT INTO reponse_question (question_uuid, reponse, bonne_reponse) values (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            for (Answer answer : question.answers()) {
                statement.setObject(1, question.uuid());
                statement.setString(2, answer.getAnswer());
                statement.setBoolean(3, answer.isCorrect());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    @Override
    public Optional<QuestionDTO> findByUuid(UUID questionUuid) {

        String query = "SELECT quuid, questionnaire_uuid, content, type FROM question WHERE quuid = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, questionUuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    UUID quuid = resultSet.getObject("quuid", UUID.class);
                    UUID quizzUuid = resultSet.getObject("questionnaire_uuid", UUID.class);
                    String content = resultSet.getString("content");
                    String type = resultSet.getString("type");

                    List<Answer> answers = new ArrayList<>();

                    // on remplit les 2 listes avec les choix et les bonne réponses
                    findAnswers(questionUuid, connection, answers);

                    return Optional.of(new QuestionDTO(questionUuid, quizzUuid, content, answers,
                            QuestionType.valueOf(type)));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error while finding the question in the Database", e);

        }

        return Optional.empty();
    }

    // on va aller chercher les choix et les bonnes reponses pour chaque question
    private void findAnswers(UUID questionUuid, Connection connection, List<Answer> answers) throws SQLException {
        String query = "SELECT reponse, bonne_reponse FROM reponse_question WHERE question_uuid = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, questionUuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String answer = resultSet.getString("reponse");
                    boolean correctAnswer = resultSet.getBoolean("bonne_reponse");
                    answers.add(new Answer(answer, correctAnswer));
                }
            }
        }
    }
}
