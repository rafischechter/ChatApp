package chatapp;

import java.util.ArrayList;

/**
 * All information for a chat room
 */
public class ChatRoom {

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
