package chatapp;

import javax.swing.*;

/**
 * Created by rafi on 3/4/2016.
 */
public class SignUpDialog {

    private JTextField username = new JTextField();
    private JTextField password = new JPasswordField();
    private JTextField screenName = new JTextField();

    private Object[] message = {"Username:", username, "Password:", password,
            "Screen Name", screenName};

    private Object[] buttons = {"Sign up", "Cancel"};

    public SignUpDialog() {
        createSignUpDialog();
    }

    private void createSignUpDialog() {
        JOptionPane.showConfirmDialog(null, message, "Sign Up", JOptionPane.CANCEL_OPTION);
    }

    public String getUsername() {
        return username.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public String getScreenName() {
        return screenName.getText();
    }

    public void createUser(String userName, String password, String screenName) {
        User user = new User(userName, password, screenName);
    }

}
