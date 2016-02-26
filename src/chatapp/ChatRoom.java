package chatapp;

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
    private ArrayList<ClientConnectionData> clientsInRoom = new ArrayList<ClientConnectionData>();

    private ArrayList<Message> messages = new ArrayList<Message>();

    public ChatRoom(int id, String roomName, String discussionTopic) {
        setId(id);
        setRoomName(roomName);
        setDiscussionTopic(discussionTopic);
    }

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

    public int getNumOfClientsCurrentlyInRoom() {
        return numOfClientsCurrentlyInRoom;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeAllMessages() {
        messages.clear();
    }

    public ArrayList<Message> getMessageList() {
        return this.messages;
    }

    public void addClient(ClientConnectionData client) {
        this.clientsInRoom.add(client);
    }

    public void removeClient(ClientConnectionData client) {
        this.clientsInRoom.remove(client);
    }

}
