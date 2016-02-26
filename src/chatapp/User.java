package chatapp;

import javax.swing.*;
import java.io.Serializable;

/**
 * Holds user information
 */
public class User implements Serializable{

    private int userID;
    private String screenName;
    private String email;
    private String userName;
    private String password;
    private ImageIcon profilePhoto;

    public User (String screenName) {
        this.userName = screenName;
    }

    public User(int id, String screenName, String email, String userName, String password) {
        this.userID = id;
        this.screenName = screenName;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public void setId(int id) {
        this.userID = id;
    }

    public void setScreenName(String screenName) {
        screenName = screenName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return userID;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
