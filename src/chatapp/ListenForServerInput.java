package chatapp;

import java.io.IOException;

/**
 * Used by the client to receive data from the server
 */
public class ListenForServerInput extends Thread {

    private Client client;
    private boolean listen = true;

    public ListenForServerInput(Client client) {
        this.client = client;
    }

    @Override
    public void run() {

        int actionCode;

        while (listen) {
            actionCode = client.getActionCodeFromServer();

            System.out.println("action code " + actionCode + " read by client");
            if (actionCode == Server.ActionCodes.NEW_MESSAGE) {
                try {
                    Message message = client.getMessageFromServer();
                    client.playSound();
                    System.out.println(message.getText());
                    client.addNewMessageToGUI(message);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (actionCode == Server.ActionCodes.NEW_CHATROOM) {
                try {
                    ChatRoom room = client.getRoomFromServer();
                    client.addNewRoomToGUI(room);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
