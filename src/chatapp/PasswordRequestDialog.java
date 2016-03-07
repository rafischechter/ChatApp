package chatapp;

import javax.swing.*;

/**
 * A dialog for entering the password for a locked room
 */
public class PasswordRequestDialog {

    private JPasswordField password = new JPasswordField();
    Object[] message = {"Please enter the password to enter this room:", password};
    int result;

    public PasswordRequestDialog() {
        result = JOptionPane.showConfirmDialog(null, message, "Password Protected", JOptionPane.OK_CANCEL_OPTION);
    }

    public int getResult() {
        return this.result;
    }

    public String getPassword() {

        StringBuilder pw = new StringBuilder("");

        for (char c : password.getPassword()){
            pw.append(c);
        }

        return pw.toString();
    }

}
