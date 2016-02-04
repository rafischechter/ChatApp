import java.io.IOException;
import java.net.Socket;

/**
 * Listens for new connection requests
 */
public class ListenForNewClient extends Thread {

    private Server server;

    private boolean lookForNewClients = true;

    ListenForNewClient(Server server) {
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

            //create a new ClientConnectionData to hold the necessary fields for
            //getting and sending information from and to the client
            ClientConnectionData ccd = new ClientConnectionData(socket);

            //store the new ClientConnectionData in the server
            server.storeNewClientConnectionData(ccd);
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
}
