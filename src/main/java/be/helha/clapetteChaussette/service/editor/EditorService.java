package be.helha.clapetteChaussette.service.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.clapetteChaussette.exception.editor.InvalidQuestionException;
import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.question.Answer;
import be.helha.clapetteChaussette.model.question.McqQuestion;
import be.helha.clapetteChaussette.model.question.OpenQuestion;
import be.helha.clapetteChaussette.model.question.PollQuestion;
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
    public OpenQuestion createOpenQuestion(String title, List<String> answerTexts, boolean hasPoints)
            throws InvalidQuestionException {
        validateQuestionEditorService.validateOpenQuestion(title, answerTexts);

        List<Answer> answers = answerTexts.stream()
                .map(String::trim)
                .filter(text -> !text.isBlank())
                .map(text -> new Answer(text, true)) // by default all the answers given are correct
                .toList();

        return new OpenQuestion(title, answers, hasPoints);
    }

    public McqQuestion createMcqQuestion(String title, List<String> answers, List<Boolean> correctAnswers)
            throws InvalidQuestionException {
        validateQuestionEditorService.validateMcqQuestion(title, answers, correctAnswers);
        List<Answer> answerObjects = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            answerObjects.add(new Answer(answers.get(i), correctAnswers.get(i)));
        }
        return new McqQuestion(title, answerObjects);
    }

    public PollQuestion createPollQuestion(String title, List<String> answers) throws InvalidQuestionException {
        validateQuestionEditorService.validatePollQuestion(title, answers);
        List<Answer> answerObjects = answers.stream()
                .map(String::trim)
                .filter(text -> !text.isBlank())
                .map(text -> new Answer(text, false)) // in a poll, there are no correct answers
                .toList();
        return new PollQuestion(title, answerObjects);
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
