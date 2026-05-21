package be.helha.clapetteChaussette.service.repository;

import be.helha.clapetteChaussette.exception.editor.InvalidQuestionException;
import be.helha.clapetteChaussette.exception.editor.InvalidQuizzException;
import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.repository.IQuestionRepository;
import be.helha.clapetteChaussette.repository.IQuizzRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service layer responsible for orchestrating business logic related to Quizzes
 * and Questions.
 * <p>
 * This service acts as an intermediary between the application's models/UI and
 * the data persistence layer.
 * It enforces business rules, ensures data integrity, and handles the
 * transformation between domain
 * models ({@link Quizz}, {@link Question}) and Data Transfer Objects (DTOs).
 * </p>
 */
public class QuizzService {

    private final IQuizzRepository quizzRepository;
    private final IQuestionRepository questionRepository;

    /**
     * Constructs a new QuizzService with the required repositories.
     *
     * @param quizzRepository    The repository for saving and retrieving quizzes.
     * @param questionRepository The repository for saving and retrieving questions.
     */
    public QuizzService(IQuizzRepository quizzRepository, IQuestionRepository questionRepository) {
        this.quizzRepository = quizzRepository;
        this.questionRepository = questionRepository;
    }

    /**
     * Registers a new quiz and all its associated questions into the database.
     * <p>
     * This method performs a complete validation of all questions before attempting
     * any database operations.
     * If validation passes, it transforms the domain models into DTOs and persists
     * them.
     * </p>
     *
     * @param quizz       The domain model representing the quiz to be saved.
     * @param teacherUuid The unique identifier of the teacher creating the quiz.
     * @throws InvalidQuestionException if any question within the quiz violates
     *                                  business rules (e.g., missing correct
     *                                  answers).
     * @throws InvalidQuizzException    if the provided teacher UUID is null.
     */
    public void registerNewQuizz(Quizz quizz, UUID teacherUuid) {
        if (teacherUuid == null) {
            throw new InvalidQuizzException("Un quiz doit être lié à un professeur.");
        }

        // save the quizz in the database
        QuizzDTO quizzDTO = transformQuizzInDTO(quizz, teacherUuid);
        quizzRepository.save(quizzDTO);

        // save all the questions of the quizz in the database
        List<QuestionDTO> questions = createQuestionDTOs(quizz);
        for (QuestionDTO q : questions) {
            questionRepository.save(q);
        }
    }

    /**
     * Factory method to create a new, empty Quizz domain object.
     *
     * @param name The display name of the quiz.
     * @return A newly instantiated {@link Quizz}.
     * @throws InvalidQuizzException if the provided name is null or entirely blank.
     */
    public Quizz createQuizz(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidQuizzException("Le nom du quiz ne peut pas être vide.");
        }
        return new Quizz(name);
    }

    /**
     * Retrieves all quizzes associated with a specific teacher.
     *
     * @param teacherUuid The unique identifier of the teacher.
     * @return A list of {@link QuizzDTO} representing the teacher's quizzes.
     * @throws InvalidQuizzException if the provided teacher UUID is null.
     */
    public List<QuizzDTO> findAllQuizzes(UUID teacherUuid) {
        if (teacherUuid == null) {
            throw new InvalidQuizzException("L'identifiant du professeur est requis.");
        }
        return quizzRepository.findAll(teacherUuid);
    }

    /**
     * Adds a question to an existing quiz in memory.
     * <p>
     * Note: This does not persist the question to the database.
     * </p>
     *
     * @param quizz    The quiz to which the question will be added.
     * @param question The question to add.
     */
    public void addQuestion(Quizz quizz, Question question) {
        quizz.addQuestion(question);
    }

    /**
     * Transforms a Quizz domain model into a Data Transfer Object.
     *
     * @param quizz       The domain model.
     * @param teacherUuid The teacher's unique identifier.
     * @return The resulting {@link QuizzDTO}.
     */
    private QuizzDTO transformQuizzInDTO(Quizz quizz, UUID teacherUuid) {
        return new QuizzDTO(quizz.getUuid(), quizz.getName(), teacherUuid);
    }

    /**
     * Converts a list of Question domain models into their corresponding DTOs.
     *
     * @param quizz The quiz containing the questions to transform.
     * @return A list of {@link QuestionDTO} ready for database insertion.
     */
    private List<QuestionDTO> createQuestionDTOs(Quizz quizz) {
        List<QuestionDTO> questionDTO = new ArrayList<>();
        for (Question q : quizz.getQuestions()) {
            questionDTO
                    .add(new QuestionDTO(q.getQuuid(), quizz.getUuid(), q.getQuestion(), q.getAnswers(), q.getType()));
        }
        return questionDTO;
    }
}
