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
        setupStreams();
    }

    /**
     * Sets up input and output streams
     */
    private void setupStreams() {
        try {
            this.input = new ObjectInputStream(socket.getInputStream());
            this.output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Closes all connections and nulls their corresponding variables
     */
    public void closeConnections() {
        try {
            this.socket.close();
            this.input.close();
            this.output.close();

            this.socket = null;
            this.input = null;
            this.output = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a Message from the client
     *
     * @return Message sent from the client
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Message getMessageFromInputStream() throws IOException, ClassNotFoundException {
        return (Message) input.readObject();
    }

    public void alertServerAboutNewMessage() {
    }
}

