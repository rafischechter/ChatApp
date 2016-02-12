import java.io.*;
import java.net.Socket;

/**
 * Aggregate connection data for a client
 */
public class ClientConnectionData {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    /**
     * Constructor, sets up data streams for the socket
     *
     * @param socket the socket connecting the client and server
     */
    public ClientConnectionData(Socket socket) {
        this.socket = socket;
        setupDataStreams();
        startListeningForInputFromClient();
    }

    /**
     * Sets up input and output streams
     */
    private void setupDataStreams() {
        try {
            this.input = new ObjectInputStream(socket.getInputStream());
            this.output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a thread to continuously listen for data being
     * sent from the client to the server
     */
    private void startListeningForInputFromClient() {
        new ListenForClientInput(this).start();
    }
}

