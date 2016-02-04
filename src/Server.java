import java.io.IOException;
import java.net.ServerSocket;

/**
 * Chat server manages all communication between clients
 */
public class Server {

    public static final int PORT = 22222;
    public static final int BACKLOG = 15;

    ServerSocket serverSocket;

    /**
     * constructor
     */
    Server() {
        try {
            serverSocket = new ServerSocket(PORT, BACKLOG);
        } catch (IOException e) {
            e.printStackTrace();
        }

        startListeningForNewClients();

    }

    /**
     * Starts a thread that listens for new clients
     */
    private void startListeningForNewClients() {
        new ListenForNewClient(this).start();
    }

}
