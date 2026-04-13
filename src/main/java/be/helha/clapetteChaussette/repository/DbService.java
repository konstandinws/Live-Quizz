package be.helha.clapetteChaussette.repository;

import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.enums.QuestionType;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

// Cette classe sert uniquement pour les tests vers la Database. Elle permet de voir si les
// commandes s'exécutent correctement
// Elle sera effacée une fois la DB finie

public class DbService {

    static void main() {
        List<String> answers = Arrays.asList("2", "4", "5", "8");

        List<String> correctAnswers = Arrays.asList("2");

        QuestionDTO test = new QuestionDTO(
                UUID.randomUUID(),
                UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"),
                "que vaut 1 + 1 ?",
                answers,
                correctAnswers,
                QuestionType.QCM);

        Connection con;

        QuestionRepository qRepo = new QuestionRepository();

        {
            try {
                qRepo.save(test);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
