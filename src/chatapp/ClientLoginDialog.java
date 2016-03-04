package chatapp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rafi on 2/12/2016.
 */
public class ClientLoginDialog {

    JTextField username = new JTextField();
    JTextField password = new JPasswordField();
    JButton signUp = new JButton("Sign up");
    Object[] message = {"Username:", username, "Password:", password, signUp};
    SignUpDialog signUpDialog;


    /**
     * Constructor for the login
     */
    public ClientLoginDialog() {
        CreateAuthenticationDialog();
        AuthorizeClient();
    }

    /**
     * Method to create the input dialog
     */
    private void CreateAuthenticationDialog() {

        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpDialog = new SignUpDialog();

                signUpDialog.createUser(signUpDialog.getUsername(),
                        signUpDialog.getPassword(), signUpDialog.getScreenName());
            }
        });

        JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);

    }

    /**
     * Mehtod to get the username
     *
     * @return username
     */
    public String getUsername() {
        return username.getText();
    }

    /**
     * Method to get password
     *
     * @return password
     */
    private String getPassword() {
        return password.getText();
    }

    /**
     * Mehtod to check if client is authorized to access
     *
     * @return is access granted
     */
    public boolean AuthorizeClient() {
        boolean accessGranted = false;
        String userName = this.getUsername();
        String password = this.getPassword();
        /*
        //Add code to check if username and password is correct
        if(true){
            accessGranted = true
        }
        */
        return accessGranted;
    }


}
