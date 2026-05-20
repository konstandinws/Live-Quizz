package be.helha.clapetteChaussette.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client {

    private final String host;
    private final int port;

    private Socket socket;
    private ObjectSocket objectSocket;
    private Thread networkThread;
    private boolean isConnected = false;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }
    }

