package clapetteChaussette.service.serveur;

import be.helha.clapetteChaussette.common.dto.QuestionDTO;
import be.helha.clapetteChaussette.common.models.events.NextQuestionResponse;
import be.helha.clapetteChaussette.common.models.events.ParticipantCountUpdate;
import be.helha.clapetteChaussette.common.models.events.ParticipantNameUpdate;
import be.helha.clapetteChaussette.server.models.Session;
import be.helha.clapetteChaussette.server.network.ClientThreadHandler;
import be.helha.clapetteChaussette.server.service.QuestionService;
import be.helha.clapetteChaussette.server.service.ServerMessagingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServeurMessagingServeurTest {
    @InjectMocks
    private ServerMessagingService serverMessagingService;

    @Mock
    private QuestionService questionService;

    @Mock
    private Session session;

    @Mock
    private ClientThreadHandler host;

    @Mock
    private ClientThreadHandler client1;

    @Mock
    private ClientThreadHandler client2;

    @Mock
    private QuestionDTO question;

    @Mock
    private QuestionDTO hiddenQuestion;

    // ─── send ─────────────────────────────────────────────────────

    @Test
    void send_shouldSendEvent_whenClientIsReachable() throws IOException {
        // GIVEN
        Object event = new Object();

        // WHEN
        serverMessagingService.send(host, event);

        // THEN
        verify(host, times(1)).send(event);
    }

    @Test
    void send_shouldNotThrowException_whenIOExceptionOccurs() throws IOException {
        // GIVEN
        Object event = new Object();
        doThrow(new IOException("Connexion perdue")).when(host).send(event);

        // THEN — l'exception est catchée, ne doit pas remonter
        assertDoesNotThrow(() -> serverMessagingService.send(host, event));
    }

    // ─── broadcastParticipantUpdate ───────────────────────────────

    @Test
    void broadcastParticipantUpdate_shouldSendCountAndNamesToHost() throws IOException {
        // GIVEN
        when(session.getHost()).thenReturn(host);
        when(session.getNumberParticipants()).thenReturn(2);
        when(session.getParticipantPseudos()).thenReturn(List.of("Alice", "Bob"));
        when(session.getClients()).thenReturn(List.of());

        // WHEN
        serverMessagingService.broadcastParticipantUpdate(session);

        // THEN — host reçoit 2 messages (count + names)
        verify(host, times(2)).send(any());
    }

    @Test
    void broadcastParticipantUpdate_shouldSendCountToEachClient() throws IOException {
        // GIVEN
        when(session.getHost()).thenReturn(host);
        when(session.getNumberParticipants()).thenReturn(2);
        when(session.getParticipantPseudos()).thenReturn(List.of("Alice", "Bob"));
        when(session.getClients()).thenReturn(List.of(client1, client2));

        // WHEN
        serverMessagingService.broadcastParticipantUpdate(session);

        // THEN — chaque client reçoit 1 message (count uniquement)
        verify(client1, times(1)).send(any());
        verify(client2, times(1)).send(any());
    }

    @Test
    void broadcastParticipantUpdate_shouldSendCountUpdate_withCorrectValue() throws IOException {
        // GIVEN
        when(session.getHost()).thenReturn(host);
        when(session.getNumberParticipants()).thenReturn(3);
        when(session.getParticipantPseudos()).thenReturn(List.of("Alice", "Bob", "Charlie"));
        when(session.getClients()).thenReturn(List.of());

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);

        // WHEN
        serverMessagingService.broadcastParticipantUpdate(session);

        // THEN
        verify(host, times(2)).send(captor.capture());
        List<Object> capturedEvents = captor.getAllValues();

        assertInstanceOf(ParticipantCountUpdate.class, capturedEvents.get(0));
        assertInstanceOf(ParticipantNameUpdate.class, capturedEvents.get(1));

        ParticipantCountUpdate countUpdate = (ParticipantCountUpdate) capturedEvents.get(0);
        assertEquals(3, countUpdate.getParticipantCount());
    }

    // ─── broadcastNextQuestion ────────────────────────────────────

    @Test
    void broadcastNextQuestion_shouldSendFullQuestionToHost() throws IOException {
        // GIVEN
        when(session.getHost()).thenReturn(host);
        when(session.getClients()).thenReturn(List.of());
        when(questionService.hideCorrectAnswers(question)).thenReturn(hiddenQuestion);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);

        // WHEN
        serverMessagingService.broadcastNextQuestion(session, question, 0);

        // THEN
        verify(host, times(1)).send(captor.capture());
        NextQuestionResponse response = (NextQuestionResponse) captor.getValue();

        assertAll(
                () -> assertEquals(question, response.getQuestion()),
                () -> assertEquals(0, response.getNextQuestionIndex()),
                () -> assertEquals("OK", response.getErrorMessage())
        );
    }

    @Test
    void broadcastNextQuestion_shouldSendHiddenQuestionToClients() throws IOException {
        // GIVEN
        when(session.getHost()).thenReturn(host);
        when(session.getClients()).thenReturn(List.of(client1, client2));
        when(questionService.hideCorrectAnswers(question)).thenReturn(hiddenQuestion);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);

        // WHEN
        serverMessagingService.broadcastNextQuestion(session, question, 1);

        // THEN — les clients reçoivent la question cachée
        verify(client1, times(1)).send(captor.capture());
        NextQuestionResponse studentResponse = (NextQuestionResponse) captor.getValue();

        assertEquals(hiddenQuestion, studentResponse.getQuestion());
    }

    @Test
    void broadcastNextQuestion_shouldCallHideCorrectAnswers() {
        // GIVEN
        when(session.getHost()).thenReturn(host);
        when(session.getClients()).thenReturn(List.of(client1));
        when(questionService.hideCorrectAnswers(question)).thenReturn(hiddenQuestion);

        // WHEN
        serverMessagingService.broadcastNextQuestion(session, question, 0);

        // THEN — hideCorrectAnswers doit être appelé exactement 1 fois
        verify(questionService, times(1)).hideCorrectAnswers(question);
    }

    // ─── broadcastQuizzFinished ───────────────────────────────────

    @Test
    void broadcastQuizzFinished_shouldSendFinishedEventToHost() throws IOException {
        // GIVEN
        when(session.getHost()).thenReturn(host);
        when(session.getClients()).thenReturn(List.of());

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);

        // WHEN
        serverMessagingService.broadcastQuizzFinished(session, 5);

        // THEN
        verify(host, times(1)).send(captor.capture());
        NextQuestionResponse response = (NextQuestionResponse) captor.getValue();

        assertAll(
                () -> assertNull(response.getQuestion()),   // null = signal de fin
                () -> assertEquals("FIN", response.getErrorMessage()),
                () -> assertEquals(5, response.getNextQuestionIndex())
        );
    }

    @Test
    void broadcastQuizzFinished_shouldSendFinishedEventToAllClients() throws IOException {
        // GIVEN
        when(session.getHost()).thenReturn(host);
        when(session.getClients()).thenReturn(List.of(client1, client2));

        // WHEN
        serverMessagingService.broadcastQuizzFinished(session, 5);

        // THEN
        verify(client1, times(1)).send(any());
        verify(client2, times(1)).send(any());
    }

    @Test
    void broadcastQuizzFinished_shouldSendNullQuestion_asEndSignal() throws IOException {
        // GIVEN
        when(session.getHost()).thenReturn(host);
        when(session.getClients()).thenReturn(List.of(client1));

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);

        // WHEN
        serverMessagingService.broadcastQuizzFinished(session, 3);

        // THEN
        verify(client1, times(1)).send(captor.capture());
        NextQuestionResponse response = (NextQuestionResponse) captor.getValue();
        assertNull(response.getQuestion()); // null = signal de fin pour le client
    }

}
