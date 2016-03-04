package unit_tests;

import chatapp.ChatRoom;
import chatapp.Client;
import chatapp.ClientConnectionData;
import chatapp.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafi on 3/4/2016.
 */
public class Server_Test {

    Socket socket;
    List<ClientConnectionData> clients;
    ChatRoom chatRoom;
    List<ChatRoom> chatRooms;


    @Before
    public void SetUP() {
        clients = new ArrayList<ClientConnectionData>();
        chatRooms = new ArrayList<ChatRoom>();
    }

    @After
    public void CleanUp() {
        clients = null;
        chatRooms = null;
        System.gc();
    }

    @Test
    public void TestAddingClientConnectionDataToListOfClient() {
        ClientConnectionData ccd = new ClientConnectionData(socket);
        clients.add(ccd);
        assertEquals(1, clients.size());
    }

    @Test
    public void TestNewChatRoomBeingAddedToListOfChatRooms() {
        chatRoom = new ChatRoom(1, "roomName", "discussion");
        chatRooms.add(chatRoom);
        assertEquals(1, chatRooms.size());
    }

}
