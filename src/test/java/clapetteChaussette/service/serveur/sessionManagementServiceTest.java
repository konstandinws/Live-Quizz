package clapetteChaussette.service.serveur;

import be.helha.clapetteChaussette.common.dto.QuizzDTO;
import be.helha.clapetteChaussette.server.models.Session;
import be.helha.clapetteChaussette.server.network.ClientThreadHandler;
import be.helha.clapetteChaussette.server.service.SessionManagementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class sessionManagementServiceTest {
    @InjectMocks
    private SessionManagementService sessionManagementService;

    @Mock
    private ClientThreadHandler host;

    @Mock
    private ClientThreadHandler client;

    @Mock
    private QuizzDTO quizz;
    @Test
    void createSession_shouldReturnSession_whenValid() {
        // WHEN
        Session session = sessionManagementService.createSession(quizz, host);

        // THEN
        assertAll(
                () -> assertNotNull(session),
                () -> assertNotNull(session.getCode()),
                () -> assertEquals(quizz, session.getQuizz()),
                () -> assertEquals(host, session.getHost())
        );
    }

    @Test
    void createSession_shouldAddSessionToMap() {
        // WHEN
        Session session = sessionManagementService.createSession(quizz, host);

        // THEN
        assertTrue(sessionManagementService.getAllSessions().containsKey(session.getCode()));
    }

    @Test
    void createSession_shouldGenerateUniqueCode_whenMultipleSessionsCreated() {
        // WHEN
        Session session1 = sessionManagementService.createSession(quizz, host);
        Session session2 = sessionManagementService.createSession(quizz, client);

        // THEN
        assertNotEquals(session1.getCode(), session2.getCode());
    }
    @Test
    void getSessionByCode_shouldReturnSession_whenCodeExists() {
        // GIVEN
        Session session = sessionManagementService.createSession(quizz, host);

        // WHEN
        Optional<Session> result = sessionManagementService.getSessionByCode(session.getCode());

        // THEN
        assertTrue(result.isPresent());
        assertEquals(session, result.get());
    }

    @Test
    void getSessionByCode_shouldReturnEmpty_whenCodeDoesNotExist() {
        // WHEN
        Optional<Session> result = sessionManagementService.getSessionByCode("INVALID");

        // THEN
        assertTrue(result.isEmpty());
    }

    @Test
    void getSessionByHost_shouldReturnSession_whenHostExists() {
        // GIVEN
        Session session = sessionManagementService.createSession(quizz, host);

        // WHEN
        Optional<Session> result = sessionManagementService.getSessionByHost(host);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(session, result.get());
    }

    @Test
    void getSessionByHost_shouldReturnEmpty_whenHostDoesNotExist() {
        // WHEN
        Optional<Session> result = sessionManagementService.getSessionByHost(host);

        // THEN
        assertTrue(result.isEmpty());
    }
    @Test
    void getSessionByClient_shouldReturnSession_whenClientIsInSession() {
        // GIVEN
        Session session = sessionManagementService.createSession(quizz, host);
        sessionManagementService.addClientToSession(session.getCode(), client, "pseudo");

        // WHEN
        Optional<Session> result = sessionManagementService.getSessionByClient(client);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(session, result.get());
    }

    @Test
    void getSessionByClient_shouldReturnEmpty_whenClientIsNotInAnySession() {
        // WHEN
        Optional<Session> result = sessionManagementService.getSessionByClient(client);

        // THEN
        assertTrue(result.isEmpty());
    }
    @Test
    void addClientToSession_shouldReturnTrue_whenSessionExists() {
        // GIVEN
        Session session = sessionManagementService.createSession(quizz, host);

        // WHEN
        boolean result = sessionManagementService.addClientToSession(session.getCode(), client, "pseudo");

        // THEN
        assertTrue(result);
    }

    @Test
    void addClientToSession_shouldReturnFalse_whenSessionDoesNotExist() {
        // WHEN
        boolean result = sessionManagementService.addClientToSession("INVALID", client, "pseudo");

        // THEN
        assertFalse(result);
    }

    @Test
    void addClientToSession_shouldAddClientToSession_whenSessionExists() {
        // GIVEN
        Session session = sessionManagementService.createSession(quizz, host);

        // WHEN
        sessionManagementService.addClientToSession(session.getCode(), client, "pseudo");

        // THEN
        assertTrue(session.getClients().contains(client));
    }
    @Test
    void removeClientFromSession_shouldRemoveClient_whenClientIsInSession() {
        // GIVEN
        Session session = sessionManagementService.createSession(quizz, host);
        sessionManagementService.addClientToSession(session.getCode(), client, "pseudo");

        // WHEN
        sessionManagementService.removeClientFromSession(client);

        // THEN
        assertFalse(session.getClients().contains(client));
    }

    @Test
    void removeClientFromSession_shouldDoNothing_whenClientIsNotInAnySession() {
        // GIVEN
        sessionManagementService.createSession(quizz, host);

        // THEN — ne doit pas lancer d'exception
        assertDoesNotThrow(() -> sessionManagementService.removeClientFromSession(client));
    }

    @Test
    void removeSessionByHost_shouldRemoveSession_whenHostExists() {
        // GIVEN
        Session session = sessionManagementService.createSession(quizz, host);

        // WHEN
        sessionManagementService.removeSessionByHost(host);

        // THEN
        assertFalse(sessionManagementService.getAllSessions().containsKey(session.getCode()));
    }

    @Test
    void removeSessionByHost_shouldDoNothing_whenHostHasNoSession() {
        // THEN — ne doit pas lancer d'exception
        assertDoesNotThrow(() -> sessionManagementService.removeSessionByHost(host));
    }
    @Test
    void getAllSessions_shouldReturnAllSessions() {
        // GIVEN
        sessionManagementService.createSession(quizz, host);
        sessionManagementService.createSession(quizz, client);

        // THEN
        assertEquals(2, sessionManagementService.getAllSessions().size());
    }

    @Test
    void getAllSessions_shouldReturnEmptyMap_whenNoSessionExists() {
        // THEN
        assertTrue(sessionManagementService.getAllSessions().isEmpty());
    }
}
