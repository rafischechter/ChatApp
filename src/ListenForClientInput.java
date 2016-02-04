/**
 * Listens and receives input from clients
 * Acts upon client instructions
 */
public class ListenForClientInput extends Thread {

    private ClientConnectionData connData;

    private boolean listen = true;

    /**
     * Creates a new thread to listen for input being sent to the
     * server from a client
     *
     * @param connData The connection data for the client the thread is listening for
     */
    public ListenForClientInput(ClientConnectionData connData) {
        this.connData = connData;
    }

    @Override
    public void run() {

        /*
         * continuously listen for input from the client
         */
        while (listen) {
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
