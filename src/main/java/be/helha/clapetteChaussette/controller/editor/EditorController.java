package be.helha.clapetteChaussette.controller.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.clapetteChaussette.exception.InvalidQuestionException;
import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.model.question.Answer;
import be.helha.clapetteChaussette.model.question.McqQuestion;
import be.helha.clapetteChaussette.model.question.OpenQuestion;
import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.repository.TeacherRepository;
import be.helha.clapetteChaussette.service.DbService;
import be.helha.clapetteChaussette.service.EditorService;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.view.ViewFX;
import be.helha.clapetteChaussette.view.editor.McqEditorFX;
import be.helha.clapetteChaussette.view.editor.OpenEditorFX;
import be.helha.clapetteChaussette.view.editor.QuizzEditorFX;
import be.helha.clapetteChaussette.view.editor.McqEditorFX.IMcqEditorListener;
import be.helha.clapetteChaussette.view.editor.OpenEditorFX.IOpenEditorListener;
import be.helha.clapetteChaussette.view.editor.QuizzEditorFX.IQuizzListener;

public class EditorController implements IQuizzListener, IOpenEditorListener, IMcqEditorListener {
    /**
     * This is the main controller for the editor features.
     * It will be used to navigate between the different editors and to share data
     * between them if needed.
     */
    EditorService editorService;
    NavigationService navigationService;
    Quizz quizz;
    TeacherDTO teacher;

    public EditorController(Quizz quizz, EditorService editorService, NavigationService navigationService) {
        this.quizz = quizz;
        this.editorService = editorService;
        this.navigationService = navigationService;
        teacher = new TeacherDTO(
                UUID.randomUUID(),
                "test_prof_1",
                "mot_de_passe_haché");
        TeacherRepository teacherRepository = new TeacherRepository(); // i know this is bad but this gonna move when we
                                                                       // implement the login system
        teacherRepository.save(teacher);
    }

    /*
     * /!\ rien n'est dans les services adéquats pour le moment
     * TODO: déplacer la logique métier dans les services appropriés et ne laisser
     * que la logique de navigation et de communication entre les vues dans le
     * controller
     */

    @Override
    public void onAddMcqRequested() {
        this.navigateToMcqQuestionEditor();
    }

    @Override
    public void onAddPollRequested() {
        this.navigateToPollQuestionEditor();
    }

    @Override
    public void onAddQuestionFreeRequested() {
        this.navigateToOpenQuestionEditor();
    }

    @Override
    public void onSaveRequested() {
        this.saveQuizz();
    }

    public void navigateToQuizzEditor() {
        QuizzEditorFX quizzFX = new QuizzEditorFX();
        quizzFX.setOwnViewController(this);
        navigateTo(quizzFX, "Éditeur de Questionnaire");
    }

    public void navigateToOpenQuestionEditor() {
        OpenEditorFX openEditorFX = new OpenEditorFX();
        openEditorFX.setOwnViewController(this);
        navigateTo(openEditorFX, "Éditeur de Question Libre");
    }

    public void navigateToPollQuestionEditor() {
        // TODO: implement poll editor
    }

    public void navigateToMcqQuestionEditor() {
        McqEditorFX mcqEditorFX = new McqEditorFX();
        mcqEditorFX.setOwnViewController(this);
        navigateTo(mcqEditorFX, "Éditeur de Question QCM");
    }

    public void navigateTo(ViewFX view, String title) {
        navigationService.navigateTo(view, title);
    }

    public void addQuestionToQuizz(Question question) {
        quizz.addQuestion(question);
    }

    public void saveQuizz() { // a faire dans un service
        DbService dbService = new DbService();
        QuizzDTO quizzDTO = new QuizzDTO(quizz.getUuid(), quizz.getName(), teacher.uuid());
        dbService.saveQuizz(quizzDTO);
        dbService.saveQuestions(createQuestionDTOs(quizz.getQuestions()));
    }

    private List<QuestionDTO> createQuestionDTOs(List<Question> questions) { // a faire dans un service
        UUID quizzUuid = quizz.getUuid();
        List<QuestionDTO> questionsDTO = new ArrayList<QuestionDTO>();
        for (Question question : quizz.getQuestions()) {
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

    public void createOpenQuestion(String title, List<String> answerTexts, boolean hasPoints)
            throws InvalidQuestionException {

        if (title == null || title.isBlank()) {
            throw new InvalidQuestionException("La question ne peut pas être vide.");
        }

        List<Answer> answers = answerTexts.stream()
                .map(String::trim)
                .filter(text -> !text.isBlank())
                .map(text -> new Answer(text, true)) // by default all the answers given are correct
                .toList();

        OpenQuestion oq = new OpenQuestion(title, answers, hasPoints);
        this.addQuestionToQuizz(oq);
        this.navigateToQuizzEditor();
    }

    public void createMcq(String title, List<String> answers, List<Boolean> correctAnswers)
            throws InvalidQuestionException {

        validateTitle(title);
        List<Answer> answerObjects = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            answerObjects.add(new Answer(answers.get(i), correctAnswers.get(i)));
        }

        validateAnswers(answerObjects);

        List<String> correctAnswerTexts = answerObjects.stream()
                .filter(Answer::isCorrect)
                .map(Answer::getAnswer)
                .toList();
        validateCorrectAnswer(correctAnswerTexts);

        // remplacer le return par un appel au service
        this.addQuestionToQuizz(new McqQuestion(title, answerObjects));
        this.navigateToQuizzEditor();
    }

    /**
     * Vérifie que le titre de la question n'est pas vide ou composé uniquement
     * d'espaces.
     *
     * @param title Le titre à valider.
     * @throws InvalidQuestionException Si le titre est invalide.
     */
    public void validateTitle(String title) throws InvalidQuestionException {
        if (title.isBlank())
            throw new InvalidQuestionException("il ne faut pas que la question posée soit vide");
    }

    /**
     * Vérifie qu'aucune des réponses proposées n'est vide ou composée uniquement
     * d'espaces.
     *
     * @param answers La liste des réponses à valider.
     * @throws InvalidQuestionException Si au moins une réponse est invalide.
     */
    public void validateAnswers(List<Answer> answers) throws InvalidQuestionException {
        for (Answer answer : answers) {
            if (answer.getAnswer().isBlank())
                throw new InvalidQuestionException("une reponse ne peut pas etre vide");
        }
    }

    /**
     * Vérifie qu'au moins une réponse a été sélectionnée comme correcte.
     *
     * @param correctAnswers La liste des bonnes réponses.
     * @throws InvalidQuestionException Si la liste est vide.
     */
    public void validateCorrectAnswer(List<String> correctAnswers) throws InvalidQuestionException {
        if (correctAnswers.isEmpty())
            throw new InvalidQuestionException("il faut au moins une réponse correcte");
    }
}
