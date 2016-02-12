import java.util.ArrayList;

/**
 * All information for a chat room
 */
public class ChatRoom {

    private int chatroomID;
    private String roomName;
    private String discussionTopic;
    private final int MAX_CLIENTS_ALLOWED = 10;
    private int clientsCurrentlyInRoom;
    private ArrayList<Client> clientsInRoom = new ArrayList<Client>();

    public ChatRoom(int chatroomID, String roomName, String discussionTopic) {
        setChatroomID(chatroomID);
        setRoomName(roomName);
        setDiscussionTopic(discussionTopic);
    }

    public void setChatroomID(int chatroomID) {
        this.chatroomID = chatroomID;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setDiscussionTopic(String discussionTopic) {
        this.discussionTopic = discussionTopic;
    }

    public int getChatroomID() {
        return chatroomID;
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

    public int getClientsCurrentlyInRoom() {
        return clientsCurrentlyInRoom;
    }

}
