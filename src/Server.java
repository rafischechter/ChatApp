import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Chat server manages all communication
 */
public class Server {

    private ServerSocket server;
    private Socket connection;
    private static boolean serverOn = true;
    private static final int PORT = 8000;
    private static final int MAX_CLIENTS = 10;
    private DataInputStream inputFromClient;
    private DataOutputStream outputToClient;


    LinkedList<Client> connectedClients = new LinkedList<Client>();

    public Server() {

        launchServer();

        runServer();

        closeServer();

    }

    /**
     * Method to launch the server
     */
    public void launchServer(){
        try {
            server = new ServerSocket(PORT, MAX_CLIENTS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to run the server
     */
    public void runServer() {
        while(serverOn){
            acceptConnections();
        }
    }

    /**
     * Method to close the server
     */
    public void closeServer() {
        try{
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method to accept connections to the server
     */
    public void acceptConnections() {
        try {
            while(true) {
                new ClientHandler(server.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Sets up input and output streams
     */
    public void setupStreams(){
        try {
            inputFromClient = new DataInputStream(connection.getInputStream());
            outputToClient = new DataOutputStream(connection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Method to send the message to all the clients
     */
    public void SendMessage(){

    }


    class ClientHandler extends Thread{
        private Socket socket;
        private DataInputStream inputFromServer;
        private DataOutputStream outputToServer;

        public ClientHandler(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            setupStreams();

        }

        /**
         * Sets up input and output streams
         */
        public void setupStreams(){
            try {
                inputFromServer = new DataInputStream(socket.getInputStream());
                outputToServer = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void closeConnection(){
            try {
                inputFromServer.close();
                outputToServer.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

