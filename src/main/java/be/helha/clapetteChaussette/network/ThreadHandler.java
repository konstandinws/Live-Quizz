package be.helha.clapetteChaussette.network;


import java.io.IOException;
import be.helha.clapetteChaussette.model.question.Answer;
import be.helha.clapetteChaussette.model.question.Question;

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
                Object transformed = transformAnsword(received);
                listener.onReceive(transformed);
            }
        }catch (IOException | ClassNotFoundException e){
            listener.onError(e);
            listener.onDisconnect();
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
        void onReceive(Object obj);
        void onDisconnect();
        void onError(Exception e);
    }
}
