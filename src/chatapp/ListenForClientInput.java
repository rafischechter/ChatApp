package chatapp;

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

            int actionCode = connData.getActionCodeFromClient();

            if (actionCode == Server.ActionCodes.NEW_MESSAGE) {
                //process new message

                Message message = connData.getMessageFromInputStream();
                server.processNewMessage(message);
            }
            else if (actionCode == Server.ActionCodes.NEW_CHATROOM) {
                //create a new chat room
            }

        }

    }
}

