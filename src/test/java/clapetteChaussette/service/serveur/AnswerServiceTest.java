package clapetteChaussette.service.serveur;

import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.models.enums.QuestionType;
import be.helha.clapetteChaussette.server.models.Session;
import be.helha.clapetteChaussette.server.network.ClientThreadHandler;
import be.helha.clapetteChaussette.server.service.AnswerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceTest {
    @InjectMocks
    private AnswerService answerService;

    @Mock
    private Session session;

    @Mock
    private ClientThreadHandler clientHandler;

    @Mock
    private QuestionDTO currentQuestion;

    // ─── recordMcqOrPollAnswer ────────────────────────────────────

    @Test
    void recordMcqOrPollAnswer_shouldReturnTrue_whenQuestionMatchesCurrent() {
        // GIVEN
        UUID questionUuid = UUID.randomUUID();
        when(session.getCurrentQuestion()).thenReturn(Optional.of(currentQuestion));
        when(currentQuestion.uuid()).thenReturn(questionUuid);

        // WHEN
        boolean result = answerService.recordMcqOrPollAnswer(session, clientHandler, questionUuid, 1);

        // THEN
        assertTrue(result);
        verify(session, times(1)).recordAnswer(clientHandler, 1);
    }

    @Test
    void recordMcqOrPollAnswer_shouldReturnFalse_whenNoCurrentQuestion() {
        // GIVEN
        when(session.getCurrentQuestion()).thenReturn(Optional.empty());

        // WHEN
        boolean result = answerService.recordMcqOrPollAnswer(session, clientHandler, UUID.randomUUID(), 1);

        // THEN
        assertFalse(result);
        verify(session, never()).recordAnswer(any(), anyInt());
    }

    @Test
    void recordMcqOrPollAnswer_shouldReturnFalse_whenQuestionUuidDoesNotMatch() {
        // GIVEN
        UUID currentUuid = UUID.randomUUID();
        UUID otherUuid = UUID.randomUUID();

        when(session.getCurrentQuestion()).thenReturn(Optional.of(currentQuestion));
        when(currentQuestion.uuid()).thenReturn(currentUuid);

        // WHEN
        boolean result = answerService.recordMcqOrPollAnswer(session, clientHandler, otherUuid, 1);

        // THEN
        assertFalse(result);
        verify(session, never()).recordAnswer(any(), anyInt());
    }

    @Test
    void recordMcqOrPollAnswer_shouldRecordCorrectIndex() {
        // GIVEN
        UUID questionUuid = UUID.randomUUID();
        when(session.getCurrentQuestion()).thenReturn(Optional.of(currentQuestion));
        when(currentQuestion.uuid()).thenReturn(questionUuid);

        // WHEN
        answerService.recordMcqOrPollAnswer(session, clientHandler, questionUuid, 2);

        // THEN
        verify(session, times(1)).recordAnswer(clientHandler, 2);
    }

    // ─── recordOpenAnswer ─────────────────────────────────────────

    @Test
    void recordOpenAnswer_shouldReturnTrue_whenQuestionMatchesCurrent() {
        // GIVEN
        UUID questionUuid = UUID.randomUUID();
        when(session.getCurrentQuestion()).thenReturn(Optional.of(currentQuestion));
        when(currentQuestion.uuid()).thenReturn(questionUuid);

        // WHEN
        boolean result = answerService.recordOpenAnswer(session, clientHandler, questionUuid, "Paris");

        // THEN
        assertTrue(result);
        verify(session, times(1)).recordTextAnswer(clientHandler, "Paris");
    }

    @Test
    void recordOpenAnswer_shouldReturnFalse_whenNoCurrentQuestion() {
        // GIVEN
        when(session.getCurrentQuestion()).thenReturn(Optional.empty());

        // WHEN
        boolean result = answerService.recordOpenAnswer(session, clientHandler, UUID.randomUUID(), "Paris");

        // THEN
        assertFalse(result);
        verify(session, never()).recordTextAnswer(any(), any());
    }

    @Test
    void recordOpenAnswer_shouldReturnFalse_whenQuestionUuidDoesNotMatch() {
        // GIVEN
        UUID currentUuid = UUID.randomUUID();
        UUID otherUuid = UUID.randomUUID();

        when(session.getCurrentQuestion()).thenReturn(Optional.of(currentQuestion));
        when(currentQuestion.uuid()).thenReturn(currentUuid);

        // WHEN
        boolean result = answerService.recordOpenAnswer(session, clientHandler, otherUuid, "Paris");

        // THEN
        assertFalse(result);
        verify(session, never()).recordTextAnswer(any(), any());
    }

    @Test
    void recordOpenAnswer_shouldRecordCorrectText() {
        // GIVEN
        UUID questionUuid = UUID.randomUUID();
        when(session.getCurrentQuestion()).thenReturn(Optional.of(currentQuestion));
        when(currentQuestion.uuid()).thenReturn(questionUuid);

        // WHEN
        answerService.recordOpenAnswer(session, clientHandler, questionUuid, "Londres");

        // THEN
        verify(session, times(1)).recordTextAnswer(clientHandler, "Londres");
    }

    // ─── recordAnswer (switch) ────────────────────────────────────

    @Test
    void recordAnswer_shouldCallRecordMcqOrPollAnswer_whenTypeMCQ() {
        // GIVEN
        UUID questionUuid = UUID.randomUUID();
        when(session.getCurrentQuestion()).thenReturn(Optional.of(currentQuestion));
        when(currentQuestion.uuid()).thenReturn(questionUuid);

        // WHEN
        boolean result = answerService.recordAnswer(
                session, clientHandler, questionUuid, QuestionType.MCQ, 1, null);

        // THEN
        assertTrue(result);
        verify(session, times(1)).recordAnswer(clientHandler, 1);
        verify(session, never()).recordTextAnswer(any(), any());
    }

    @Test
    void recordAnswer_shouldCallRecordMcqOrPollAnswer_whenTypePOLL() {
        // GIVEN
        UUID questionUuid = UUID.randomUUID();
        when(session.getCurrentQuestion()).thenReturn(Optional.of(currentQuestion));
        when(currentQuestion.uuid()).thenReturn(questionUuid);

        // WHEN
        boolean result = answerService.recordAnswer(
                session, clientHandler, questionUuid, QuestionType.POLL, 0, null);

        // THEN
        assertTrue(result);
        verify(session, times(1)).recordAnswer(clientHandler, 0);
        verify(session, never()).recordTextAnswer(any(), any());
    }

    @Test
    void recordAnswer_shouldCallRecordOpenAnswer_whenTypeOPEN() {
        // GIVEN
        UUID questionUuid = UUID.randomUUID();
        when(session.getCurrentQuestion()).thenReturn(Optional.of(currentQuestion));
        when(currentQuestion.uuid()).thenReturn(questionUuid);

        // WHEN
        boolean result = answerService.recordAnswer(
                session, clientHandler, questionUuid, QuestionType.OPEN, 0, "Paris");

        // THEN
        assertTrue(result);
        verify(session, times(1)).recordTextAnswer(clientHandler, "Paris");
        verify(session, never()).recordAnswer(any(), anyInt());
    }

    @Test
    void recordAnswer_shouldReturnFalse_whenNoCurrentQuestion() {
        // GIVEN
        when(session.getCurrentQuestion()).thenReturn(Optional.empty());

        // WHEN
        boolean result = answerService.recordAnswer(
                session, clientHandler, UUID.randomUUID(), QuestionType.MCQ, 1, null);

        // THEN
        assertFalse(result);
        verify(session, never()).recordAnswer(any(), anyInt());
        verify(session, never()).recordTextAnswer(any(), any());
    }

}
