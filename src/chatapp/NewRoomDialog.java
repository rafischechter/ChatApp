package chatapp;

import javax.swing.*;

/**
 * A dialog for creating a new room
 */
public class NewRoomDialog {

    private JTextField roomName = new JTextField();
    private JTextField roomTopic = new JTextField();
    private JTextField roomPassword = new JTextField();
    Object[] message = {"Name:", roomName, "Topic:", roomTopic, "(Optional)", "Password: ", roomPassword};

    public NewRoomDialog() {
        JOptionPane.showConfirmDialog(null, message, "Create New Room", JOptionPane.OK_CANCEL_OPTION);
    }

    public String getRoomName() {
        return roomName.getText().trim();
    }

    public String getRoomTopic() {
        return  roomTopic.getText().trim();
    }

    public String getRoomPassword() {
        if(roomPassword.getText().trim().equals(""))
            return null;
        else
            return roomPassword.getText().trim();

    }

}
