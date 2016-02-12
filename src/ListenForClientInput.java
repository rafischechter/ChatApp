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

            Message message = null;

            /*
                current code is assuming only messages will be received, otherwise there
                needs to be a way to differentiate what the client wants to do
             */
            try {
                message = connData.getMessageFromInputStream();
                server.processNewMessage(message);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            /*
                TODO how will different instructions be recognized so they can be
                executed differently?

                Easiest way is probably to have the client send an instruction code from
                a static variable preceding any required data that is needed for
                the instruction to be executed.
             */
        }


    }
}

