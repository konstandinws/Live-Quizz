package be.helha.clapetteChaussette.server.models;

import be.helha.clapetteChaussette.common.models.Teacher;
import be.helha.clapetteChaussette.server.network.ClientThreadHandler;

import java.util.ArrayList;
import java.util.List;

public class Session {

    Teacher teacher;
    List<ClientThreadHandler> clients = new ArrayList<>();

    int getNumberParticipants() {
        return clients.size();
    }
}
