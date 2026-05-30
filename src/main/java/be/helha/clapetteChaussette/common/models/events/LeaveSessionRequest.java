package be.helha.clapetteChaussette.common.models.events;

/**
 * Sent by a student who leaves the waiting screen on purpose (e.g. "Retour").
 * The server identifies the session from the sender's connection.
 */
public class LeaveSessionRequest extends NetworkEvent {
}
