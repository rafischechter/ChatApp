package chatapp;

import java.io.*;
import java.net.Socket;

/**
 * Aggregate connection data for a client
 */
public class ClientConnectionData {

    private Socket socket;
    private ObjectInputStream objectInput;
    private ObjectOutputStream objectOutput;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;

    /**
     * Constructor, sets up data streams for the socket
     *
     * @param socket the socket connecting the client and server
     */
    public ClientConnectionData(Socket socket) {
        this.socket = socket;
        if (socket != null)
            setupStreams();
        //announceClientConnected();
    }

    /**
     * Sets up input and output streams
     */
    private void setupStreams() {
        try {
            this.objectOutput = new ObjectOutputStream(socket.getOutputStream());
            this.objectInput = new ObjectInputStream(socket.getInputStream());

            this.dataOutput = new DataOutputStream(socket.getOutputStream());
            this.dataInput = new DataInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getRoomIdForJoinRequest() throws IOException {
        int id;

        id = dataInput.readInt();

        return id;
    }

    public String getPassword() {
        String pw = "";

        try {
            pw = dataInput.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pw;
    }

    /**
     * Closes all connections and nulls their corresponding variables
     */
    public void closeConnections() {
        try {
            this.socket.close();
            this.objectInput.close();
            this.objectOutput.close();
            this.dataInput.close();
            this.dataOutput.close();

            this.socket = null;
            this.objectInput = null;
            this.objectOutput = null;
            this.dataInput = null;
            this.dataOutput = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a chatapp.Message from the client
     *
     * @return chatapp.Message sent from the client
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
     * (Codes are found in the chatapp.Server.ActionCodes class)
     *
     * @return the code of the action the client wants to perform
     */
    public int getActionCode() {

        int actionCode = -1;

        try {
            actionCode = dataInput.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return actionCode;
    }

    public int getRoomId() {

        int roomId = -1;

        try {
            roomId = dataInput.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return roomId;
    }

    public void sendActionCode(int code) throws IOException {
        dataOutput.writeInt(code);
    }

    public String getNewRoomName() throws IOException {
        return dataInput.readUTF();
    }

    public String getNewRoomTopic() throws IOException {
        return dataInput.readUTF();
    }

    public void sendRoom(ChatRoom room) throws IOException {
        objectOutput.writeObject(room);
        objectOutput.flush();
    }

    public void sendMessage(Message message) throws IOException {
        objectOutput.writeObject(message);
        objectOutput.flush();
    }

//    public void announceClientConnected() {
//        Message m = new Message();
//        m.setText(socket.getInetAddress() + " connected");
//        try {
//            sendMessage(m);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}

