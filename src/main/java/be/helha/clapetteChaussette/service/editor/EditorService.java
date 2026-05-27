package be.helha.clapetteChaussette.service.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.clapetteChaussette.exception.editor.InvalidQuestionException;
import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.question.McqFactory;
import be.helha.clapetteChaussette.model.question.OpenFactory;
import be.helha.clapetteChaussette.model.question.PollFactory;
import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.repository.IQuestionRepository;
import be.helha.clapetteChaussette.repository.IQuizzRepository;

public class EditorService {
    /**
     * This service will be responsible for all the business logic related to the
     * editor features.
     * It will create the question objects and save them to the database
     */

    // #region Fields

    private IQuizzRepository quizzRepository;
    private IQuestionRepository questionRepository;
    private ValidateQuestionEditorService validateQuestionEditorService;

    // endregion

    // #region Constructor

    public EditorService(IQuizzRepository quizzRepository,
            IQuestionRepository questionRepository,
            ValidateQuestionEditorService validateQuestionEditorService) {
        this.quizzRepository = quizzRepository;
        this.questionRepository = questionRepository;
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

    public List<QuizzDTO> getQuizzesForTeacher(UUID teacherUuid) {
        return quizzRepository.findAll(teacherUuid);
    }

    public void saveQuizz(Quizz quizz, UUID teacherUuid) {
        QuizzDTO quizzDTO = new QuizzDTO(quizz.getUuid(), quizz.getName(), teacherUuid);
        quizzRepository.save(quizzDTO);
        questionRepository.saveQuestions(createQuestionDTOs(quizz.getQuestions(), quizz.getUuid()));
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
