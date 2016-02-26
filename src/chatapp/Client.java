package chatapp;

import java.io.*;
import java.net.Socket;

import jdk.nashorn.internal.scripts.JO;
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
    private ClientLoginDialog clientLoginDialog;

    private String userName;

    private ChatRoom currRoom = null;

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
        clientGUI.setHeaderLabel(null, null, null);
        connectToServer();
        setupStreams();
        maintainConnection();
        closeConnection();

    }

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
                    playSound();
                    System.out.println(message.getText());
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
        }
    }

    private void joinNewRoom(ChatRoom room) {
        this.currRoom = room;
        this.clientGUI.setUpNewRoom(room);
    }

    public void addNewMessageToGUI(Message message) {
        clientGUI.addNewMessage(message);
    }

    public void addNewRoomToGUI(ChatRoom room) {
        clientGUI.addNewRoom(room);
    }

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

    public boolean isInRoom() {
        return this.currRoom != null;
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return actionCode;
    }

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

}
