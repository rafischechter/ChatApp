import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * client instance
 */
public class Client{

    private final int PORT = 8000;
    private String serverAddress;
    private Socket socket;
    private ObjectInputStream inputFromServer;
    private ObjectOutputStream outputToServer;

    public Client(){
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        connectToServer();
        setupStreams();
        maintainConnection();
        closeConnection();

    }

    /**
     * Prompt the user for the servers IP Address
     * @return servers IP Address
     */

    private String getServerAddress() {
        return JOptionPane.showInputDialog(
                null, "Enter the servers IP address:");
    }

    /**
     * Sets up input and output streams
     */
    public void setupStreams(){
        try {
            inputFromServer = new ObjectInputStream(socket.getInputStream());
            outputToServer = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connects client to server
     */
    public void connectToServer(){
        serverAddress = getServerAddress();
        try {
            socket = new Socket(serverAddress, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maintain an active connection
     * send and receive messages
     */
    public void maintainConnection(){
        while(true){

        }
    }

    /**
     * Close connections
     */
    private void closeConnection() {
        try {
            inputFromServer.close();
            outputToServer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
