import java.io.*;
import java.net.Socket;

/**
 * Aggregate connection data for a client
 */
public class ClientConnectionData {

    private Socket socket;
    private ObjectInputStream objectInput;
    private ObjectOutputStream objectOutput;
    private DataInputStream input;
    private DataOutputStream output;

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
            this.objectInput = new ObjectInputStream(socket.getInputStream());
            this.objectOutput = new ObjectOutputStream(socket.getOutputStream());

            this.input = new DataInputStream(socket.getInputStream());
            this.output = new DataOutputStream(socket.getOutputStream());
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
            this.objectInput.close();
            this.objectOutput.close();
            this.input.close();
            this.output.close();

            this.socket = null;
            this.objectInput = null;
            this.objectOutput = null;
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
    public Message getMessageFromInputStream() {
        Message message = null;

        try {
            message = (Message) objectInput.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return message;
    }

    /**
     * gets the action code from the client to tell the server what
     * action it wants to perform
     *
     * (Codes are found in the Server.ActionCodes class)
     *
     * @return the code of the action the client wants to perform
     */
    public int getActionCodeFromClient() {

        int actionCode = -1;

        try {
            actionCode = input.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return actionCode;
    }

}

