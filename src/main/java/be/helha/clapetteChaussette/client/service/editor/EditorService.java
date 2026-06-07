package be.helha.clapetteChaussette.client.service.editor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.clapetteChaussette.common.exception.editor.InvalidQuestionException;
import be.helha.clapetteChaussette.common.models.Quizz;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.common.models.events.SaveQuizzRequest;
import be.helha.clapetteChaussette.client.model.question.McqFactory;
import be.helha.clapetteChaussette.client.model.question.OpenFactory;
import be.helha.clapetteChaussette.client.model.question.PollFactory;
import be.helha.clapetteChaussette.client.network.NetworkController;
import be.helha.clapetteChaussette.common.models.questions.Question;

public class EditorService {
    /**
     * This service will be responsible for all the business logic related to the
     * editor features.
     * It creates the question objects and asks the server to persist them.
     */

    // #region Fields

    private NetworkController networkController;
    private ValidateQuestionEditorService validateQuestionEditorService;

    // endregion

    // #region Constructor

    public EditorService(NetworkController networkController,
            ValidateQuestionEditorService validateQuestionEditorService) {
        this.networkController = networkController;
        this.validateQuestionEditorService = validateQuestionEditorService;
    }

    // endregion

    // #region Public Methods
    public Question createOpenQuestion(String title, List<String> answerTexts, List<Boolean> correctAnswers,
            boolean hasPoints)
            throws InvalidQuestionException {
        validateQuestionEditorService.validateOpenQuestion(title, answerTexts);

        return new OpenFactory().createQuestion(title, answerTexts,
                correctAnswers, hasPoints);
    }

    public Question createMcqQuestion(String title, List<String> answers, List<Boolean> correctAnswers)
            throws InvalidQuestionException {
        validateQuestionEditorService.validateMcqQuestion(title, answers, correctAnswers);

        return new McqFactory().createQuestion(title, answers, correctAnswers, true);
    }

    public Question createPollQuestion(String title, List<String> answers) throws InvalidQuestionException {
        validateQuestionEditorService.validatePollQuestion(title, answers);

        return new PollFactory().createQuestion(title, answers, null, false);
    }

    /**
     * Sends the quizz and its questions to the server for persistence. The actual
     * database writes are performed server-side; the refreshed quizz list comes
     * back asynchronously as a {@code SaveQuizzResponse}.
     *
     * @param quizz       the quizz being edited.
     * @param teacherUuid the owning teacher's UUID.
     * @throws IOException if the request cannot be sent to the server.
     */
    public void saveQuizz(Quizz quizz, UUID teacherUuid) throws IOException {
        QuizzDTO quizzDTO = new QuizzDTO(quizz.getUuid(), quizz.getName(), teacherUuid);
        List<QuestionDTO> questions = createQuestionDTOs(quizz.getQuestions(), quizz.getUuid());
        networkController.send(new SaveQuizzRequest(quizzDTO, questions));
    }

    // endregion

    // #region Private Methods

    /**
     * Just to the lissibility of the code.
     * This method will create all the question DTOs from the quizz questions and
     * return them as a list.
     * 
     * @param quizz
     * @return
     */
    private List<QuestionDTO> createQuestionDTOs(List<Question> questions, UUID quizzUuid) {
        List<QuestionDTO> questionsDTO = new ArrayList<QuestionDTO>();
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO(
                    UUID.randomUUID(),
                    quizzUuid,
                    question.getQuestion(),
                    question.getAnswers(),
                    question.getType());
            questionsDTO.add(questionDTO);
        }
        return questionsDTO;
    }

    // endregion
}
