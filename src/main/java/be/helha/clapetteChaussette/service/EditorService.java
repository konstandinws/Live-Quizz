package be.helha.clapetteChaussette.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.clapetteChaussette.exception.InvalidQuestionException;
import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.question.Answer;
import be.helha.clapetteChaussette.model.question.McqQuestion;
import be.helha.clapetteChaussette.model.question.OpenQuestion;
import be.helha.clapetteChaussette.model.question.Question;

public class EditorService {
    /**
     * This service will be responsible for all the business logic related to the
     * editor features.
     * It will create the question objects and save them to the database
     */

    // #region Fields

    private DbService dbService;
    private ValidateQuestionEditorService validateQuestionEditorService;

    // endregion

    // #region Constructor

    public EditorService(DbService dbService, ValidateQuestionEditorService validateQuestionEditorService) {
        this.dbService = dbService;
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

    public void saveQuizz(Quizz quizz, UUID teacherUuid) {
        QuizzDTO quizzDTO = new QuizzDTO(quizz.getUuid(), quizz.getName(), teacherUuid);
        dbService.saveQuizz(quizzDTO);
        dbService.saveQuestions(createQuestionDTOs(quizz.getQuestions(), quizz.getUuid()));
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
