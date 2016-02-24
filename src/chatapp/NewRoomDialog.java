package chatapp;

import javax.swing.*;

/**
 * Created by Yitzi on 2/23/2016.
 */
public class NewRoomDialog {

    private JTextField roomName = new JTextField();
    private JTextField roomTopic = new JTextField();
    Object[] message = {"Name:", roomName, "Topic:", roomTopic};

    public NewRoomDialog() {
        JOptionPane.showConfirmDialog(null, message, "Create New Room", JOptionPane.OK_CANCEL_OPTION);
    }

    public String getRoomName() {
        return roomName.getText();
    }

    public String getRoomTopic() {
        return  roomTopic.getText();
    }



}
