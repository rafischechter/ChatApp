package chatapp;

import java.io.IOException;

/**
 * Listens and receives input from clients
 * Acts upon client instructions
 */
public class ListenForClientInput extends Thread {

    private ClientConnectionData connData;
    private Server server;

    private boolean listen = true;

    /**
     * Creates a new thread to listen for input being sent to the
     * server from a client
     *
     * @param connData The connection data for the client the thread is listening for
     */
    public ListenForClientInput(Server server, ClientConnectionData connData) {
        this.server = server;
        this.connData = connData;
    }

    @Override
    public void run() {

        /*
         * continuously listen for input from the client
         */
        while (listen) {

            int actionCode = connData.getActionCode();
            System.out.println("server receives action code " + actionCode + " from the client");

            if (actionCode == Server.ActionCodes.NEW_MESSAGE) {
                //process new message
                int roomId = connData.getRoomId();
                Message message = connData.getMessageFromInputStream();
                server.processNewMessage(roomId, message);
            }
            else if (actionCode == Server.ActionCodes.NEW_CHATROOM) {
                //create a new chat room
                int id = ChatRoom.getNextId();
                ChatRoom room = null;
                try {
                    String roomName = connData.getNewRoomName().trim();
                    String roomTopic = connData.getNewRoomTopic().trim();
                    String roomPassword = connData.getPassword().trim();
                    room = new ChatRoom(id, roomName, roomTopic, roomPassword);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (room != null) {
                    server.processNewChatRoom(room);
                }
            }
            else if (actionCode == Server.ActionCodes.JOIN_NEW_ROOM) {
                int id = -1;

                try {
                    id = connData.getRoomIdForJoinRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //get the room trying to be entered
                ChatRoom room = server.getRoomById(id);

                try {
                    connData.sendActionCode(Server.ActionCodes.JOIN_NEW_ROOM);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //ensure the room exists
                if (!roomExists(room)) {

                    //let client know room doesnt exist
                    try {
                        connData.sendActionCode(Server.ActionCodes.JOIN_NEW_ROOM_ERROR);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    continue;

                }
                //check if the room is full
                else if (room.getNumOfClientsCurrentlyInRoom() >= room.MAX_CLIENTS_ALLOWED) {
                    // let client know room is full
                    try {
                        connData.sendActionCode(Server.ActionCodes.JOIN_NEW_ROOM_FULL);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    continue;
                }
                //if not remove the user from their old room, add the user to the new room and then send the room to them
                else {
                    server.addClientToNewRoom(connData, room.getId());
                    try {
                        connData.sendActionCode(Server.ActionCodes.JOIN_NEW_ROOM_SUCCESS);
                        connData.sendRoom(room);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (actionCode == Server.ActionCodes.JOIN_NEW_PASSWORD_PROTECTED_ROOM) {
                int roomId = connData.getRoomId();
                String password = connData.getPassword();

                ChatRoom room = server.getRoomById(roomId);

                try {
                    connData.sendActionCode(Server.ActionCodes.JOIN_NEW_ROOM);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //ensure the room exists
                if (!roomExists(room)) {

                    //let client know room doesnt exist
                    try {
                        connData.sendActionCode(Server.ActionCodes.JOIN_NEW_ROOM_ERROR);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    continue;

                }
                //check if the password is not a match
                else if (!room.getPassword().equals(password)) {
                    System.out.println("client entered: " + password + ", but password was: " + room.getPassword());
                    try {
                        connData.sendActionCode(Server.ActionCodes.JOIN_NEW_ROOM_PASSWORD_INCORRECT);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //check if the room is full
                else if (room.getNumOfClientsCurrentlyInRoom() >= room.MAX_CLIENTS_ALLOWED) {
                    // let client know room is full
                    try {
                        connData.sendActionCode(Server.ActionCodes.JOIN_NEW_ROOM_FULL);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    continue;
                }
                //if not remove the user from their old room, add the user to the new room and then send the room to them
                else {
                    server.addClientToNewRoom(connData, room.getId());
                    try {
                        connData.sendActionCode(Server.ActionCodes.JOIN_NEW_ROOM_SUCCESS);
                        connData.sendRoom(room);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (actionCode == Server.ActionCodes.CLOSE_CONNECTION) {
                server.disconnectClient(connData);
                try {
                    connData.sendActionCode(Server.ActionCodes.CLOSE_CONNECTION);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                connData.closeConnections();
                this.listen = false;
            }

        }

    }

    private boolean roomExists(ChatRoom room) {
        return room != null;
    }
}

