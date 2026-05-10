package be.helha.clapetteChaussette.network;


import java.io.IOException;

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
    private Object transform(Object received) {
        //see how we're going to transform
        return received;
    }
    private interface  Listener {
        void onReceive(Object obj);
        void onDisconnect();
        void onError(Exception e);
    }
}
