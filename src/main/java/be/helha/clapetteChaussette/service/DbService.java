package be.helha.clapetteChaussette.service;

import be.helha.clapetteChaussette.model.dto.Credentials;
import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.model.enums.QuestionType;
import be.helha.clapetteChaussette.repository.QuestionRepository;
import be.helha.clapetteChaussette.repository.QuizzRepository;
import be.helha.clapetteChaussette.repository.TeacherRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Cette classe sert uniquement pour les tests vers la Database. Elle permet de voir si les
// commandes s'exécutent correctement
// Elle sera effacée une fois la DB finie

public class DbService {

    static void main() {
        List<String> answers = Arrays.asList("2", "4", "5", "8");

        List<String> correctAnswers = Arrays.asList("2");

        QuestionDTO test = new QuestionDTO(
                // UUID.randomUUID(),
                UUID.fromString("209e3561-99f6-44b8-ac81-3a59925eece1"), // permet d'avoir le meme uuid pour faire les
                                                                         // tests sinon je recreer un question
                UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"),
                "que vaut 1 + 1 ?",
                answers,
                correctAnswers,
                QuestionType.QCM);

        String name = "test_prof_1";
        String password = "mot_de_passe_haché";

        TeacherDTO teacher = new TeacherDTO(
                // UUID.randomUUID(),
                UUID.fromString("fa57430b-54ed-4dc0-b02d-3aa4d4aa126f"),
                name,
                password);

        Credentials login = new Credentials(name, password);

        // quizz sans DTO specifique
        // UUID quizzUUID = UUID.randomUUID();
        UUID quizzUUID = UUID.fromString("d751e24a-a231-4fc6-ab65-3e7b5de682de");
        UUID teacherUUID = UUID.fromString("fa57430b-54ed-4dc0-b02d-3aa4d4aa126f");
        String quizzName = "quizz_test";

        Connection con;

        QuestionRepository qRepo = new QuestionRepository();
        TeacherRepository tRepo = new TeacherRepository();
        QuizzRepository quizzRepo = new QuizzRepository();

        {
            try {
                // teacher
                // tRepo.save(teacher);
                // Optional<TeacherDTO> teacherDTO = tRepo.find(login);
                // IO.println(teacherDTO.get()); N'AFFICHE PAS LE MDP HACHE MAIS C"EST MON FIX
                // DEGEU CAR INUTILE DE RENVOYER CA EN REPONSE

                // question
                // qRepo.save(test);
                // Optional<QuestionDTO> question = qRepo.findByUuid(test.uuid());
                // IO.print(question.get());

                // quizz
                // quizzRepo.save(quizzUUID, teacherUUID, quizzName);

                // Optional<String> nameOfTheQuizz = quizzRepo.findNameByUuid(quizzUUID);
                // IO.println(nameOfTheQuizz.get());

                // Optional<List<QuizzDTO>> allQuizzes = quizzRepo.findAll(teacherUUID);
                // allQuizzes.get().forEach( q -> IO.println(q));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

/*
 * VALIDATION DES COMMANDES
 * 
 * - QuestionRepository
 * 
 * save() - Ok
 * findByUuid -Ok
 * 
 * les methodes secondaires fonctionnent aussi (saveAnswers et findAnswers)
 * 
 * - QuizzRepository
 * 
 * save() - Ok
 * findNameByUuid() - Ok
 * findAll() - Ok
 * 
 * - TeacherRepository
 * 
 * save() - Ok
 * find() - Ok
 * 
 */
