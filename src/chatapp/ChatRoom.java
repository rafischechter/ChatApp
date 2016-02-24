package chatapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * All information for a chat room
 */
public class ChatRoom implements Serializable {

    private static int nextId = 1;

    private int chatRoomId;
    private String roomName;
    private String discussionTopic;

    private final int MAX_CLIENTS_ALLOWED = 10;
    private int numOfClientsCurrentlyInRoom;
    private ArrayList<Client> clientsInRoom = new ArrayList<Client>();

    private ArrayList<Message> messages = new ArrayList<Message>();

    public ChatRoom(int chatRoomId, String roomName, String discussionTopic) {
        setChatRoomId(chatRoomId);
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

    public void setChatRoomId(int chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setDiscussionTopic(String discussionTopic) {
        this.discussionTopic = discussionTopic;
    }

    public int getChatRoomId() {
        return chatRoomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getDiscussionTopic() {
        return discussionTopic;
    }

    public int getMAX_CLIENTS_ALLOWED() {
        return MAX_CLIENTS_ALLOWED;
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

}
