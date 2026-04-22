package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.exception.DatabaseException;
import be.helha.clapetteChaussette.model.dto.Credentials;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;


// TODO javadoc

// je l'appelle teacher repo mais on pourrait aussi l'appeler TeacherDAO (data access objet), en gros celui qui parle a la db (je crois)
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
    public Optional<TeacherDTO> find(Credentials cred) {

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
                    return Optional.of(new TeacherDTO(uuid, name, ""));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error while finding the teacher in the Database", e);

        }

        return Optional.empty();
    }
}
