package chatapp;

import javax.swing.*;

/**
 * Created by rafi on 2/19/2016.
 */
public class ClientConnectionDialog {
    JComboBox serverAddress = new JComboBox();
    JTextField serverPort = new JTextField();
    Object[] message = {"Server Address:", serverAddress, "Server Port:", serverPort};

    public ClientConnectionDialog() {
        createConnectionDialog();
    }

    public void createConnectionDialog() {
        serverAddress.setEditable(true);
        JOptionPane.showConfirmDialog(null, message, "Connect", JOptionPane.OK_CANCEL_OPTION);
    }

    public String getServerAddress() {
        return serverAddress.getSelectedItem().toString();
    }

    public String getServerPort() {
        return serverPort.getText();
    }

}
