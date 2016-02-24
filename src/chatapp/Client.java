package chatapp;

import java.io.*;
import java.net.Socket;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

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
    private ClientConnectionDialog ccd;
    private ClientGUI clientGUI;
    private ClientLoginDialog clientLoginDialog;
    private String userName;

    public Client(){
        try {
            runClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runClient() throws IOException {
        clientLoginDialog = new ClientLoginDialog();
        userName = clientLoginDialog.getUsername();
        getServerAddressAndPort();
        clientGUI = new ClientGUI(this);
        clientGUI.updateUserInfo(userName);
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
     *
     */
    public void sendNewRoomInfo(String name, String topic) throws IOException {
        dataOutputStream.writeUTF(name);
        dataOutputStream.flush();
        dataOutputStream.writeUTF(topic);
        dataOutputStream.flush();
    }

    /**
     * Sets up input and output streams
     */
    public void setupStreams(){
        try {

            this.outputToServer = new ObjectOutputStream(socket.getOutputStream());
            this.inputFromServer = new ObjectInputStream(socket.getInputStream());

            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.dataInputStream = new DataInputStream(socket.getInputStream());

            System.out.println("Streams connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendActionCode(int code) throws IOException {
        dataOutputStream.writeInt(code);
        dataOutputStream.flush();
    }

    public void sendMessage(Message message) throws IOException {
        outputToServer.writeObject(message);
        outputToServer.flush();
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
        int actionCode;

        while (true) {
            actionCode = getActionCodeFromServer();

            if (actionCode == Server.ActionCodes.NEW_MESSAGE) {
                Message message = null;

                try {
                    message = getMessageFromServer();
                    playSound();
                    System.out.println(message.getText());
                    addNewMessageToGUI(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if (actionCode == Server.ActionCodes.NEW_CHATROOM) {
                try {
                    ChatRoom room = getRoomFromServer();
                    addNewRoomToGUI(room);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addNewMessageToGUI(Message message) {
        clientGUI.addNewMessage(message);
    }

    public void addNewRoomToGUI(ChatRoom room) {
        clientGUI.addNewRoom(room);
    }

    public Message getMessageFromServer() throws IOException, ClassNotFoundException {
        return (Message) inputFromServer.readObject();
    }

    public ChatRoom getRoomFromServer() throws IOException, ClassNotFoundException {
        return (ChatRoom) inputFromServer.readObject();
    }

    /**
     * Close connections
     */
    private void closeConnection() {
        try {
            inputFromServer.close();
            outputToServer.close();
            dataInputStream.close();
            dataOutputStream.close();
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

    public void playSound() {
        String beep = "beep.wav";
        InputStream in;
        AudioStream audioStream = null;
        try {
            in = new FileInputStream(beep);
            audioStream = new AudioStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AudioPlayer.player.start(audioStream);
    }

    public int getActionCodeFromServer() {

        int actionCode = -1;

        try {
            actionCode = dataInputStream.readInt();
            System.out.println("action code " + actionCode + " read by client");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return actionCode;
    }

}
