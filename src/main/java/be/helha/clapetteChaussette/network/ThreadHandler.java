package be.helha.clapetteChaussette.network;


import java.io.IOException;
import be.helha.clapetteChaussette.model.question.Answer;

public class ThreadHandler implements Runnable {
    private final Listener listener;

    ObjectSocket objectSocket ;
    public ThreadHandler(ObjectSocket objectSocket, Listener listener) {
        this.objectSocket = objectSocket;
        this.listener = listener;
    }
    public void run(){
        try {
            while (true) {
                Object received = objectSocket.receive();
                Object transformed = transform(received);
                listener.onReceive(transformed);
            }
        }catch (IOException | ClassNotFoundException e){
            listener.onError(e);
            listener.onDisconnect();
            objectSocket.close();
        }
    }
    private Answer transform(Object received) {
        if (received instanceof Answer answer) {
            return answer;
        }
        throw new IllegalArgumentException("L'objet reçu n'est pas un Answer : " + received.getClass());
    }
    private interface  Listener {
        void onReceive(Object obj);
        void onDisconnect();
        void onError(Exception e);
    }
}
