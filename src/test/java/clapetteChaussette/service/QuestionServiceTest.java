package clapetteChaussette.service;

import be.helha.clapetteChaussette.client.service.question.QuestionService;
import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.models.enums.QuestionType;
import be.helha.clapetteChaussette.common.models.questions.Answer;
import be.helha.clapetteChaussette.common.models.questions.McqQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import be.helha.clapetteChaussette.common.models.questions.Question;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {
    private QuestionService questionService;

    @Mock
    private Question question;

    @Mock
    private Answer correctAnswer;

    @Mock
    private Answer wrongAnswer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        questionService = new QuestionService();
    }
    @Test
    public void isMcqCorrectTest() {
        Answer reponseA = new Answer("1", true);
        Answer reponseB = new Answer("2", false);
        List<Answer> listAnswers = Arrays.asList(reponseA, reponseB);
        System.out.println(listAnswers);
        Question testquestion = new McqQuestion("question", listAnswers);
        assertTrue(questionService.isMcqCorrect(testquestion,0));
        assertFalse(questionService.isMcqCorrect(testquestion,1));

    }
    @Test
    public void isReponseCorrectTest() {
        when(correctAnswer.isCorrect()).thenReturn(true);
        when(correctAnswer.getAnswer()).thenReturn("Paris");
        when(question.getAnswers()).thenReturn(List.of(correctAnswer));
        assertTrue(questionService.isReponseCorrect(question,"Paris"));
        assertFalse(questionService.isReponseCorrect(question,"londre"));
    }
    @Test
    public void transformQuestionToDTOTest() {
        UUID quuid = UUID.randomUUID();
        UUID questionnaireUuid = UUID.randomUUID();
        when(question.getQuuid()).thenReturn(quuid);
        when(question.getQuestionnaireUuid()).thenReturn(questionnaireUuid);
        when(question.getQuestion()).thenReturn("Quelle est la capitale de la France ?");
        when(question.getAnswers()).thenReturn(List.of(correctAnswer));
        when(question.getType()).thenReturn(QuestionType.MCQ);
        QuestionDTO dto = questionService.transformQuestionToDTO(question);
        assertNotNull(dto);
        assertEquals(quuid, dto.uuid());
        assertEquals(questionnaireUuid, dto.quizzUuid());
        assertEquals("Quelle est la capitale de la France ?", dto.titre());
        assertEquals(List.of(correctAnswer), dto.answers());
        assertEquals(QuestionType.MCQ, dto.type());
    }


}
