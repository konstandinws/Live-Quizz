package be.helha.clapetteChaussette.common.models.events;

/**
 * Sent by the server to the teacher and the students of a session whenever the
 * participant count changes.
 */
public class ParticipantCountUpdate extends NetworkEvent {

    private final int participantCount;

    public ParticipantCountUpdate(int participantCount) {
        this.participantCount = participantCount;
    }

    public int getParticipantCount() {
        return participantCount;
    }
}
