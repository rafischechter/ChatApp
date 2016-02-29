package unit_tests;

import chatapp.ChatRoom;
import chatapp.ClientConnectionData;
import chatapp.Message;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Yitzi on 2/28/2016.
 */
public class ChatRoom_Test {


    /*
     * ChatRoom id's can not be tested because the order of each test method
     * can't be predicted and it is therefore impossible to tell what the id
     * would be when a room is created
     */

    /*
        The method alertClientsOfNewMessage can't be tested because
        it requires socket connections to actual clients
     */

    @Test
    public void testGetAndSetRoomName() {
        ChatRoom room1 = new ChatRoom(ChatRoom.getNextId(), "TestRoom1", "TestRoom");
        room1.setRoomName("NewName");
        assertEquals("NewName", room1.getRoomName());
    }

    @Test
    public void testGetAndSetRoomDiscussionTopic() {
        ChatRoom room1 = new ChatRoom(ChatRoom.getNextId(), "TestRoom1", "TestRoom");
        assertEquals("TestRoom", room1.getDiscussionTopic());
        room1.setDiscussionTopic("NewTopic");
        assertEquals("NewTopic", room1.getDiscussionTopic());
    }

    @Test
    public void testNoClientInRoomOnCreation() {
        ChatRoom room1 = new ChatRoom(ChatRoom.getNextId(), "TestRoom1", "TestRoom");
        assertTrue(room1.getNumOfClientsCurrentlyInRoom() == 0);
    }

    @Test
    public void testNumberOfClientsChangeAsClientsJoinAndLeave() {
        ChatRoom room1 = new ChatRoom(ChatRoom.getNextId(), "TestRoom1", "TestRoom");
        ClientConnectionData testClient1 = new ClientConnectionData(null);
        ClientConnectionData testClient2 = new ClientConnectionData(null);

        room1.addClient(testClient1);
        assertTrue(room1.getNumOfClientsCurrentlyInRoom() == 1);
        room1.addClient(testClient2);
        assertTrue(room1.getNumOfClientsCurrentlyInRoom() == 2);
        room1.removeClient(testClient1);
        assertTrue(room1.getNumOfClientsCurrentlyInRoom() == 1);
        room1.removeClient(testClient2);
        assertTrue(room1.getNumOfClientsCurrentlyInRoom() == 0);
    }

    @Test
    public void testRemoveClientThatIsNotInRoom() {
        ClientConnectionData testClient1 = new ClientConnectionData(null);
        ClientConnectionData testClient2 = new ClientConnectionData(null);

        ChatRoom room1 = new ChatRoom(ChatRoom.getNextId(), "TestRoom1", "TestRoom");
        room1.addClient(testClient1);
        room1.removeClient(testClient2);

        assertEquals(1, room1.getNumOfClientsCurrentlyInRoom());
    }

    @Test
    public void testIsClientInRoomWhenClientIsInRoom() {
        ChatRoom room1 = new ChatRoom(ChatRoom.getNextId(), "TestRoom1", "TestRoom");
        ClientConnectionData testClient1 = new ClientConnectionData(null);
        room1.addClient(testClient1);

        assertTrue(room1.isClientInRoom(testClient1));
    }

    @Test
    public void testIsClientInRoomWhenClientIsNotInRoom() {
        ChatRoom room1 = new ChatRoom(ChatRoom.getNextId(), "TestRoom1", "TestRoom");
        ClientConnectionData testClient1 = new ClientConnectionData(null);

        assertFalse(room1.isClientInRoom(testClient1));
    }

    @Test
    public void testIsClientInRoomWhenClientIsNull() {
        ChatRoom room1 = new ChatRoom(ChatRoom.getNextId(), "TestRoom1", "TestRoom");
        ClientConnectionData testClient1 = null;

        assertFalse(room1.isClientInRoom(testClient1));
    }

    @Test
    public void testAddingAMessage() {
        ChatRoom room1 = new ChatRoom(ChatRoom.getNextId(), "TestRoom1", "TestRoom");
        Message testMessage1 = new Message();
        room1.addMessage(testMessage1);

        assertEquals(1, room1.getMessageList().size());
        assertSame(testMessage1, room1.getMessageList().get(0));

        Message testMessage2 = new Message();
        room1.addMessage(testMessage2);

        assertEquals(2, room1.getMessageList().size());
        assertSame(testMessage1, room1.getMessageList().get(0));
        assertSame(testMessage2, room1.getMessageList().get(1));
    }

    @Test
    public void testAddingMessageWhenMessageIsNull() {
        ChatRoom room1 = new ChatRoom(ChatRoom.getNextId(), "TestRoom1", "TestRoom");
        Message testMessage1 = null;
        room1.addMessage(testMessage1);

        assertEquals(0, room1.getMessageList().size());
    }

    @Test
    public void testRemoveAllMessages() {
        ChatRoom room1 = new ChatRoom(ChatRoom.getNextId(), "TestRoom1", "TestRoom");
        Message testMessage1 = new Message();
        Message testMessage2 = new Message();
        Message testMessage3 = new Message();

        room1.addMessage(testMessage1);
        room1.addMessage(testMessage2);
        room1.addMessage(testMessage3);
        assertEquals(3, room1.getMessageList().size());

        room1.removeAllMessages();
        assertEquals(0, room1.getMessageList().size());

    }

}
