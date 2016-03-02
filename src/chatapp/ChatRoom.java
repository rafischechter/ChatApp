package chatapp;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * All information for a chat room
 */
public class ChatRoom implements Serializable {

    private static int nextId = 1;

    public final int MAX_CLIENTS_ALLOWED = 10;

    private int id;
    private String roomName;
    private String discussionTopic;

    private int numOfClientsCurrentlyInRoom = 0;
    transient private ArrayList<ClientConnectionData> clientsInRoom = new ArrayList<ClientConnectionData>();

    private ArrayList<Message> messages = new ArrayList<Message>();

    public ChatRoom(int id, String roomName, String discussionTopic) {
        setId(id);
        setRoomName(roomName);
        setDiscussionTopic(discussionTopic);
    }

    /**
     * Gets a new id for a newly created room.
     * If there is overflow, the count resets to 1
     *
     * @return The id of the new ChatRoom
     */
    public static synchronized int getNextId() {
        int retValue = nextId;

        nextId++;

        if (nextId <= 0)
            nextId = 1;

        return retValue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setDiscussionTopic(String discussionTopic) {
        this.discussionTopic = discussionTopic;
    }

    public int getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getDiscussionTopic() {
        return discussionTopic;
    }

    /**
     * Gets the number of clients currently in the room
     * @return THe number of clients in the room
     */
    public int getNumOfClientsCurrentlyInRoom() {
        return numOfClientsCurrentlyInRoom;
    }

    /**
     * Add a new Message to the room
     * @param message The message being added
     */
    public void addMessage(Message message) {

        //store new message
        if (message != null)
            messages.add(message);

    }


    /**
     * Send newly added Messages to all clients currently in the room
     * @param message The new message that is being broadcast to all the clients
     *
     * TODO would this be better just returning the list of the clients and the server would alert all of them about the message?
     */
    public void alertClientsOfNewMessage(Message message) {
        for (ClientConnectionData client : clientsInRoom) {
            try {
                client.sendActionCode(Server.ActionCodes.NEW_MESSAGE);
                client.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Removes all the messages from the room
     */
    public void removeAllMessages() {
        messages.clear();
    }

    /**
     * Returns all the messages in the room
     * @return A list of all the messages
     */
    public ArrayList<Message> getMessageList() {
        return this.messages;
    }

    /**
     * Adds a client to the room
     * @param client The client being added
     */
    public void addClient(ClientConnectionData client) {
//        Message newUserNotification = new Message();
//        newUserNotification.setText("New user entered the room");
//        this.addMessage(newUserNotification);

        if (client != null) {
            this.clientsInRoom.add(client);
            this.numOfClientsCurrentlyInRoom++;
        }
    }

    /**
     * Removes a client from the room
     * @param client The client being removed
     */
    public void removeClient(ClientConnectionData client) {

        if (isClientInRoom(client)) {
            this.clientsInRoom.remove(client);
            this.numOfClientsCurrentlyInRoom--;
        }

    }

    /**
     * Checks if a specified client is in the room
     *
     * @param client The client being checked
     * @return boolean Whether or not the client is in the room
     */
    public boolean isClientInRoom(ClientConnectionData client) {
        return clientsInRoom.indexOf(client) >= 0;
    }

    /**
     * Closes the room
     */
    public void close() {
        this.removeAllMessages();
        this.messages = null;
        this.clientsInRoom = null;
    }
}
