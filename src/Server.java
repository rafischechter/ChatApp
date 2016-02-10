import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Chat server manages all communication between clients
 */
public class Server {

    public static final int PORT = 8000;
    public static final int BACKLOG = 15;

    private ServerSocket serverSocket;

    //holds all the connection data for clients connected to the server
    private List<ClientConnectionData> clients = new ArrayList<ClientConnectionData>();

    /**
     * constructor
     */
    Server() {
        startServer();
        startListeningForNewClients();

    }

    /**
     * starts up the server by creating the socket
     */
    private void startServer() {
        try {
            serverSocket = new ServerSocket(PORT, BACKLOG);
        } catch (IOException e) {
            e.printStackTrace();
        }    }

    /**
     * Starts a thread that listens for new clients
     */
    private void startListeningForNewClients() {
        new ListenForNewClient(this).start();
    }

    /**
     * Get the socket after accepting the clients connection request
     *
     * @return socket from new client requesting connection
     */
    public Socket getNewClientSocket() throws IOException {
        return this.serverSocket.accept();
    }

    /**
     * Stores the client connection data in the servers list of clients
     *
     * @param ccd The new client being saved
     */
    public void storeNewClientConnectionData(ClientConnectionData ccd) {
        this.clients.add(ccd);
    }

}

