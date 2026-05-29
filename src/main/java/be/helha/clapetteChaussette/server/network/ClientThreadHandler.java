package be.helha.clapetteChaussette.server.network;


import java.io.IOException;

import be.helha.clapetteChaussette.common.models.events.LoginRequest;
import be.helha.clapetteChaussette.common.models.events.NetworkEvent;
import be.helha.clapetteChaussette.common.models.events.RegisterRequest;
import be.helha.clapetteChaussette.common.network.ObjectSocket;
import be.helha.clapetteChaussette.common.models.questions.Answer;

public class ClientThreadHandler extends Thread {
    private final Listener listener;

    ObjectSocket objectSocket ;

    public ClientThreadHandler(ObjectSocket objectSocket, Listener listener) {
        this.objectSocket = objectSocket;
        this.listener = listener;
    }

    public void run(){
        try {
            while (true) {
                    // wait for a message from the client
                    NetworkEvent event = this.objectSocket.read();
                    if (event instanceof LoginRequest) {
                        listener.onLogin(this, (LoginRequest) event);
                    } else if (event instanceof RegisterRequest) {
                        listener.onRegister(this, (RegisterRequest) event);
                    }
            }
        }catch (IOException | ClassNotFoundException e){
//            listener.onError(e);
//            listener.onDisconnect();
            objectSocket.close();
        }
    }
    private Object transformAnsword(Object received) {
        if (received instanceof Answer answer) {
            return answer;
        }
        throw new IllegalArgumentException(
                "Objet reçu non supporté : " + received.getClass().getName()
        );
    }
    public interface  Listener {

        void onLogin(ClientThreadHandler clientThreadHandler, LoginRequest loginRequest);
        void onRegister(ClientThreadHandler clientThreadHandler, RegisterRequest registerRequest);
//        void onDisconnect();
//        void onError(Exception e);
    }
}
