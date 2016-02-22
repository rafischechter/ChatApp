package chatapp;

import java.io.*;
import java.net.Socket;

/**
 * client instance
 */
public class Client{

    private int port;
    private String serverAddress;
    private Socket socket;
    private ObjectInputStream inputFromServer;
    private ObjectOutputStream outputToServer;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    ClientConnectionDialog ccd;
    private ClientGUI clientGUI;

    public Client(){
        try {
            runClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runClient() throws IOException {
        getServerAddressAndPort();
        clientGUI = new ClientGUI(this);
        connectToServer();
        setupStreams();
        maintainConnection();
        closeConnection();

    }

    /**
     * Prompt the user for the servers IP Address
     * @return servers IP Address
     */
    /*
    private String getServerAddress() {
        return JOptionPane.showInputDialog(
                null, "Enter the servers IP address:");
    }
    */

    /**
     * Sets up input and output streams
     */
    public void setupStreams(){
        try {
            outputToServer = new ObjectOutputStream(socket.getOutputStream());
            inputFromServer = new ObjectInputStream(socket.getInputStream());

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            System.out.println("Streams connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendActionCode(int code) throws IOException {
        dataOutputStream.writeInt(code);
    }

    public void sendMessage(Message message) throws IOException {
        outputToServer.writeObject(message);
    }

    /**
     * Connects client to server
     */
    public void connectToServer(){
        try {
            socket = new Socket(serverAddress, port);
            System.out.println("Connected to server");
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
            try {
                Message message = (Message) inputFromServer.readObject();
                System.out.println(message.getText());
                clientGUI.addNewMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
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

    /**
     * Gets the server address and port from the client
     * connection dialog
     */
    private void getServerAddressAndPort() {
        ccd = new ClientConnectionDialog();
        serverAddress = ccd.getServerAddress();
        port = ccd.getServerPort();
    }

}
