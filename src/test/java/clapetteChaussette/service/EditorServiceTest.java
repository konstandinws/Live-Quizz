package clapetteChaussette.service;

import be.helha.clapetteChaussette.client.service.editor.EditorService;
import be.helha.clapetteChaussette.client.service.editor.ValidateQuestionEditorService;
import be.helha.clapetteChaussette.common.models.questions.Question;
import be.helha.clapetteChaussette.server.repository.IQuestionRepository;
import be.helha.clapetteChaussette.server.repository.IQuizzRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
public class EditorServiceTest {
    EditorService  editorService;
    @Mock
    private IQuizzRepository quizzRepository;
    @Mock
    private IQuestionRepository questionRepository;
    @Mock
    private ValidateQuestionEditorService validateQuestionEditorService;
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        editorService = new EditorService(quizzRepository, questionRepository, validateQuestionEditorService);
    }
    @Test
    public void createOpenQuestiontest(){
        List<String> listeanswerstring = new ArrayList<String>();
        listeanswerstring.add("1");
        listeanswerstring.add("2");
        listeanswerstring.add("3");
        ArrayList<Boolean> listeenswerbool = new ArrayList<Boolean>();
        listeenswerbool.add(true);
        listeenswerbool.add(false);
        listeenswerbool.add(false);
        Question question = editorService.createOpenQuestion("question",listeanswerstring,listeenswerbool,false );
        assertEquals(question.getQuestion(),"question");
        assertSame(question.getAnswers(),listeanswerstring);
        assertEquals(question.getAnswers(),listeenswerbool);
    }

}
