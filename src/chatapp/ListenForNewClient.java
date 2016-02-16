package chatapp;

import java.io.IOException;
import java.net.Socket;

/**
 * Listens for new connection requests
 */
public class ListenForNewClient extends Thread {

    private Server server;

    private boolean lookForNewClients = true;

    public ListenForNewClient(Server server) {
        this.server = server;
    }

    @Override
    public void run(){

        Socket socket = null;

        /*
         * continuous loop to accept new clients
         */
        while (lookForNewClients) {

            //get socket connecting to the new client
            try {
                socket = getNewClientSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //create a new chatapp.ClientConnectionData to hold the necessary fields for
            //getting and sending information from and to the client
            ClientConnectionData ccd = new ClientConnectionData(socket);

            //store the new chatapp.ClientConnectionData in the server
            server.storeNewClientConnectionData(ccd);

            //start listening for new messages from the client
            startListeningForInputFromClient(server, ccd);

        }

    }

    /**
     * Gets the socket connecting to a client
     *
     * @return socket connecting the server to the client
     */
    private Socket getNewClientSocket() throws IOException {
        return server.getNewClientSocket();
    }

    /**
     * Starts a thread to continuously listen for data being
     * sent from the client to the server
     */
    private void startListeningForInputFromClient(Server server, ClientConnectionData ccd) {
        new ListenForClientInput(server, ccd).start();
    }
}

