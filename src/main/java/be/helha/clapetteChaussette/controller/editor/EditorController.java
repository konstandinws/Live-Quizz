package be.helha.clapetteChaussette.controller.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import be.helha.clapetteChaussette.model.Quizz;
import be.helha.clapetteChaussette.model.dto.QuestionDTO;
import be.helha.clapetteChaussette.model.dto.QuizzDTO;
import be.helha.clapetteChaussette.model.dto.TeacherDTO;
import be.helha.clapetteChaussette.model.question.Question;
import be.helha.clapetteChaussette.repository.TeacherRepository;
import be.helha.clapetteChaussette.service.DbService;
import be.helha.clapetteChaussette.service.NavigationService;
import be.helha.clapetteChaussette.view.OpenEditorFX;
import be.helha.clapetteChaussette.view.QcmEditorFX;
import be.helha.clapetteChaussette.view.QuizzFX;
import be.helha.clapetteChaussette.view.ViewFX;

public class EditorController {
    /**
     * This is the main controller for the editor features.
     * It will be used to navigate between the different editors and to share data
     * between them if needed.
     */
    NavigationService navigationService;
    Quizz quizz;
    TeacherDTO teacher;

    public EditorController(Quizz quizz) {
        this.quizz = quizz;
        teacher = new TeacherDTO(
                UUID.randomUUID(),
                "test_prof_1",
                "mot_de_passe_haché");
        TeacherRepository teacherRepository = new TeacherRepository();
        teacherRepository.save(teacher);
    }

    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    public void navigateToQuizzEditor() {
        QuizzFX quizzFX = new QuizzFX();
        QuizzEditorController controller = new QuizzEditorController();
        quizzFX.setOwnViewController(controller);
        controller.setController(this);
        navigateTo(quizzFX, "Éditeur de Questionnaire");
    }

    public void navigateToOpenQuestionEditor() {
        OpenEditorFX openEditorFX = new OpenEditorFX();
        OpenEditorController controller = new OpenEditorController();
        openEditorFX.setOwnViewController(controller);
        controller.setController(this);
        navigateTo(openEditorFX, "Éditeur de Question Libre");
    }

    public void navigateToPollQuestionEditor() {
        // TODO: implement poll editor
    }

    public void navigateToQcmQuestionEditor() {
        QcmEditorFX qcmEditorFX = new QcmEditorFX();
        QcmEditorController qcmEditorController = new QcmEditorController();
        qcmEditorFX.setOwnViewController(qcmEditorController);
        qcmEditorController.setController(this);
        navigateTo(qcmEditorFX, "Éditeur de Question QCM");
    }

    public void navigateTo(ViewFX view, String title) {
        navigationService.navigateTo(view, title);
    }

    public void addQuestionToQuizz(Question question) {
        quizz.addQuestion(question);
    }

    public void saveQuizz() {
        DbService dbService = new DbService();
        QuizzDTO quizzDTO = new QuizzDTO(quizz.getUuid(), quizz.getName(), teacher.uuid());
        dbService.saveQuizz(quizzDTO);
        dbService.saveQuestions(createQuestionDTOs(quizz.getQuestions()));
    }

    private List<QuestionDTO> createQuestionDTOs(List<Question> questions) {
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
}
