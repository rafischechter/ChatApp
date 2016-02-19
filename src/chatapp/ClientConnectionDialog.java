package chatapp;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by rafi on 2/19/2016.
 */
public class ClientConnectionDialog {
    JComboBox serverAddress = new JComboBox();
    JTextField serverPort = new JTextField();
    Object[] message = {"Server Address:", serverAddress, "Server Port:", serverPort};
    ArrayList<String> savedAddresses = new ArrayList<String>();

    public ClientConnectionDialog() {
        createConnectionDialog();
    }

    public void createConnectionDialog() {
        serverAddress.setEditable(true);
        populateServerAddress();
        JOptionPane.showConfirmDialog(null, message, "Connect", JOptionPane.OK_CANCEL_OPTION);
    }

    public String getServerAddress() {
        return serverAddress.getSelectedItem().toString();
    }

    public int getServerPort() {
        return Integer.parseInt(serverPort.getText());
    }

    public void addToSavedAddresses(String address) {
        savedAddresses.add(address);
    }

    public void populateServerAddress() {
        savedAddresses.add("localhost");
        serverAddress.removeAllItems();
        for (String x : savedAddresses) {
            serverAddress.addItem(x);
        }
    }

}
