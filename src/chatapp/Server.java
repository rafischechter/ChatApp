package chatapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Chat server manages all communication between clients
 */
public class Server {

    class ActionCodes {
        //todo is it better to use an enum?
        public static final int NEW_MESSAGE = 1;
        public static final int NEW_CHATROOM = 2;
        public static final int JOIN_NEW_ROOM = 3;
            public static final int JOIN_NEW_ROOM_ERROR = 31;
            public static final int JOIN_NEW_ROOM_FULL = 32;
            public static final int JOIN_NEW_ROOM_SUCCESS = 33;
        public static final int CLOSE_CONNECTION = 4;

    }

    public static final int PORT = 8000;
    public static final int BACKLOG = 15;

    private ServerSocket serverSocket;
    private ServerGUI serverGUI;

    //holds all the connection data for clients connected to the server
    private List<ClientConnectionData> clients = new ArrayList<ClientConnectionData>();
    private List<ChatRoom> chatRooms = new ArrayList<ChatRoom>();

    /**
     * constructor
     */
    Server() {
        serverGUI = new ServerGUI(this);
        startServer();
        startListeningForNewClients();

    }

    /**
     * starts up the server by creating the socket
     */
    private void startServer() {
        try {
            serverSocket = new ServerSocket(PORT, BACKLOG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts a thread that listens for new clients
     */
    private void startListeningForNewClients() {
        new ListenForNewClient(this).start();
    }

    /**
     * Get the socket after accepting the clients connection request
     *
     * @return socket from new client requesting connection
     */
    public Socket getNewClientSocket() throws IOException {
        return this.serverSocket.accept();
    }

    /**
     * Stores the client connection data in the servers list of clients
     *
     * @param ccd The new client being saved
     */
    public void storeNewClientConnectionData(ClientConnectionData ccd) {
        this.clients.add(ccd);
    }

    public void processNewChatRoom(ChatRoom room) {
        this.addNewChatRoom(room);

        //alert all clients about new room
        for (ClientConnectionData client : clients) {
            try {
                client.sendActionCode(Server.ActionCodes.NEW_CHATROOM);
                client.sendRoom(room);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendNewClientAllRooms(ClientConnectionData client) {
        for(ChatRoom room : chatRooms) {
            try {
                client.sendActionCode(ActionCodes.NEW_CHATROOM);
                client.sendRoom(room);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void addClientToNewRoom(ClientConnectionData client, int roomId) {
        for (ChatRoom room : chatRooms) {
            if (room.isClientInRoom(client)){
                room.removeClient(client);
            } else if (room.getId() == roomId) {
                room.addClient(client);
            }
        }
    }

    public void addNewChatRoom(ChatRoom room) {
        this.chatRooms.add(room);
    }

    /**
     *
     * Processes a newly received message
     * @param message New message received from the client
     */
    public void processNewMessage(int roomId, Message message) {

        for (ChatRoom room : chatRooms) {
            if (room.getId() == roomId) {
                room.addMessage(message);
                room.alertClientsOfNewMessage(message);
                break;
            }
        }
    }

    public void turnOnServer() {
        // Code to turn on the server
    }

    public void shutOffServer() {
        // code to shutdown the server
    }

    public ChatRoom getRoomById(int id) {
        for (ChatRoom room : chatRooms) {
            if (room.getId() == id)
                return room;
        }

        return null;
    }

    public void disconnectClient(ClientConnectionData client) {
        //remove client from any room they are in
        for (ChatRoom room : chatRooms) {
            if (room.isClientInRoom(client)) {
                room.removeClient(client);
                break;
            }
        }

        //remove client from list of clients
        clients.remove(client);

    }

}

