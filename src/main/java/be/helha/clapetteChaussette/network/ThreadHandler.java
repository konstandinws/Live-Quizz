package be.helha.clapetteChaussette.network;


import java.io.IOException;

public class ThreadHandler extends Thread {
    private Thread thread;
    private final Listener listener;
    ObjectSocket objectSocket ;
    private Server serverSocket;
    private Client clientSocket;
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

        }
    }
    private interface  Listener {
        void onObjectReceived(Object obj);
    }
}
