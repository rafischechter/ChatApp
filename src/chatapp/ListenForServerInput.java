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

    }
}
