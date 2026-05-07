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
                Object obj = objectSocket.read();
                listener.onObjectReceived(obj);
            }
        }catch (IOException | ClassNotFoundException e){
            objectSocket.close();

        }
    }
    private interface  Listener {
        void onObjectReceived(Object obj);
    }
}
