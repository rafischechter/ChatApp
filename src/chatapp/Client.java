package chatapp;

import java.io.*;
import java.net.Socket;
import java.util.Random;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;

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

    private User user;

    private ChatRoom currRoom = null;

    public Client(){
        try {
            Random random = new Random();
            int guestNumber = random.nextInt(899999) + 100000;
            this.user = new User("Guest " + guestNumber);
            runClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * For testing - to skip all login screens
     * @param user
     */
    public Client(User user){
            this.user = user;
            this.serverAddress = "localhost";
            this.port = 8000;
            clientGUI = new ClientGUI(this);
            clientGUI.setHeaderLabel(user.getUserName(), null, null);
            connectToServer();
            setupStreams();
            maintainConnection();
            requestShutDown();

    }

    /**
     * Gets the user who has signed in
     *
     * @return the user who is signed in
     */
    public User getUser() {
        return  this.user;
    }

    /**
     * Sets up the Client and related classes
     *
     * @throws IOException
     */
    public void runClient() throws IOException {
        ClientLoginDialog clientLoginDialog = new ClientLoginDialog();
        user.setUserName(clientLoginDialog.getUsername());
        getServerAddressAndPort();
        clientGUI = new ClientGUI(this);
        clientGUI.setHeaderLabel(null, null, null);
        connectToServer();
        setupStreams();
        maintainConnection();
        closeConnection();
    }

    /**
     *  Sends information to the Server to open a new room
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

    /**
     * Sends an ActionCode to the Server so it knows what action the Client wants to make
     *
     * @param code The Action Code being sent
     *
     * @throws IOException
     */
    public void sendActionCode(int code) throws IOException {
        dataOutputStream.writeInt(code);
        dataOutputStream.flush();
    }

    /**
     * Sends a Message object to the Server (along with what room it is meant for)
     *
     * @param message The message being sent
     * @throws IOException
     */
    public void sendMessage(Message message) throws IOException {
        dataOutputStream.writeInt(currRoom.getId());
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
                    Message message = getMessageFromServer();
                    playNewMessageSound();
                    currRoom.addMessage(message);
                    addNewMessageToGUI(message);
            }
            else if (actionCode == Server.ActionCodes.NEW_CHATROOM) {
                    ChatRoom room = getRoomFromServer();
                    addNewRoomToGUI(room);
            }
            else if (actionCode == Server.ActionCodes.JOIN_NEW_ROOM) {

                int responseCode = getActionCodeFromServer();

                System.out.println("response code is " + responseCode);
                if(responseCode == Server.ActionCodes.JOIN_NEW_ROOM_SUCCESS) {

                    ChatRoom room = getRoomFromServer();

                    if (room != null) {
                        this.joinNewRoom(room);
                    }

                }
                else if (responseCode == Server.ActionCodes.JOIN_NEW_ROOM_ERROR) {
                    JOptionPane.showMessageDialog(null, "There has been an error trying to connect to that room", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (responseCode == Server.ActionCodes.JOIN_NEW_ROOM_FULL) {
                    JOptionPane.showMessageDialog(null, "That room is currently full", "Full Room", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if (actionCode == Server.ActionCodes.CLOSE_CONNECTION) {
                this.shutDown();
            }
        }
    }

    /**
     * Puts the user into a new ChatRoom
     *
     * @param room The room being entered
     */
    private void joinNewRoom(ChatRoom room) {
        this.currRoom = room;
        this.clientGUI.setUpNewRoom(room);
    }

    /**
     * Adds a new message to be shown on the GUI
     *
     * @param message The message being displayed
     */
    public void addNewMessageToGUI(Message message) {
        clientGUI.addNewMessage(message);
    }

    /**
     * Adds a new room to be shown on the GUI
     *
     * @param room The room being displayed
     */
    public void addNewRoomToGUI(ChatRoom room) {
        clientGUI.addNewRoom(room);
    }

    /**
     * Receives a message from the server
     *
     * @return The message received from the server
     */
    public Message getMessageFromServer() {
        Message message = null;

        try {
            message = (Message) inputFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return message;
    }

    /**
     * Get a ChatRoom from the server
     *
     * @return The room being received from the server
     */
    public ChatRoom getRoomFromServer() {
        ChatRoom room = null;

        try {
            room = (ChatRoom) inputFromServer.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return room;
    }

    /**
     * Closes the clients socket connections
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

    /**
     * Checks if the client is in a room (not a specific room)
     * @return
     */
    public boolean isInRoom() {
        return this.currRoom != null;
    }

    /**
     * Plays a sound when a new message is recieved
     */
    public void playNewMessageSound() {
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

    /**
     * Receives an ActionCode from the server so that the client knows what action
     * the server wants it to perform
     *
     * @return The action code being received from the server
     */
    public int getActionCodeFromServer() {

        int actionCode = -1;

        try {
            actionCode = dataInputStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return actionCode;
    }

    /**
     * Sends a request to the server to join a room
     *
     * @param id The id of the room that the client is requesting to join
     */
    public void requestToJoinRoom(int id) {

        //check if the user is trying to get into the room they are already in
        if (currRoom != null && currRoom.getId() == id) {
            JOptionPane.showMessageDialog(null, "You are already in that room", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            //send the server a request to join the room

            try {
                sendActionCode(Server.ActionCodes.JOIN_NEW_ROOM);
                dataOutputStream.writeInt(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Shuts down the client
     */
    public void shutDown() {
        this.closeConnection();
        clientGUI.close();
        System.exit(0);
    }

    /**
     * Sends a request to the server to shut down. It allows the server to sever any
     * connections so that no errors are thrown
     */
    public void requestShutDown() {
        try {
            sendActionCode(Server.ActionCodes.CLOSE_CONNECTION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
