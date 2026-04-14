package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.exception.DatabaseException;
import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.enums.QuestionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO modifier les parametres des questions pour voir si il faut mettre des DTO specifiques au quizz

public class QuizzRepository {

    // il faudra rajouter un nom pour le quizz dans sa table dans la DB, comme ca quand on recupere grace au réseau on
    // afficher le nom du quizz.
    public void save(UUID uuid, UUID teacherUuid, String name) {

        String query = "INSERT INTO questionnaire(quuid, teacher_uuid, name) values (?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setObject(1, uuid);
            statement.setObject(2, teacherUuid);
            statement.setString(3, name);

            statement.executeUpdate();

        } catch ( SQLException e) {
            throw new DatabaseException("Error while saving the quizz in the Database", e);
        }
    }


    // cette methode sert peut etre a rien
    // elle retourne le nom d'un questionnaire en prenant son uuid (mais pour avoir son uuid, on deja recu le nom)
    public Optional<String> findNameByUuid(UUID uuid) {
        String query = "Select name FROM questionnaire WHERE quuid = ?";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setObject(1, uuid);

            statement.executeUpdate();

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()) {
                    String name = resultSet.getString("name");

                    return Optional.of(name);
                }
            }

        } catch ( SQLException e) {
            throw new DatabaseException("Error while finding the name of quizz in the Database", e);
        }

        return Optional.empty();
    }

    // obligé de retourner un optionnal ? au pire la liste est juste vide
    // je laisse comme ca dans pour avoir "protocole" pour traiter les données

    // retourne tous les questionnaires qui appartiennent a un prof
    public Optional<List<QuizzDTO>> findAll(UUID teacherUuid) {
        String query = "Select quuid, name FROM questionnaire WHERE teacher_uuid = ?";
        List<QuizzDTO> quizzes = new ArrayList<>();

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setObject(1, teacherUuid);

            statement.executeQuery();

            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()) {
                    UUID quuid = resultSet.getObject("quuid", UUID.class);
                    String name = resultSet.getString("name");

                    quizzes.add(new QuizzDTO(quuid, name));
                }
            }

        } catch ( SQLException e) {
            throw new DatabaseException("Error while finding all the quizzes of a teacher in the Database", e);
        }
        return Optional.of(quizzes);
    }

}
