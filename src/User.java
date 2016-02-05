/**
 * Holds user information
 */
public class User {

    int id;
    String ScreenName;
    String email;
    String userName;
    String password;
    //Image


    public User(int id, String screenName, String email, String userName, String password) {
        this.id = id;
        ScreenName = screenName;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScreenName(String screenName) {
        ScreenName = screenName;
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
        return id;
    }

    public String getScreenName() {
        return ScreenName;
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
