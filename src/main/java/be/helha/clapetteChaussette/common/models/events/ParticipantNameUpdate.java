package be.helha.clapetteChaussette.common.models.events;

import java.util.List;

public class ParticipantNameUpdate extends NetworkEvent {
    private final List<String> participantPseudos;

    public ParticipantNameUpdate(List<String> participantPseudos) {
        this.participantPseudos = participantPseudos;
    }

    public List<String> getParticipantNames() {
        return participantPseudos;
    }
}
