import javax.swing.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private String userID;
    private Date timeStamp;
    private String messageText;
    private ImageIcon img;
    private File file;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    private void setTimeStamp() {
        this.timeStamp = getCurrentDate();
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    /**
     * Gets a date stamp from the system
     * @return date stamp
     */
    public Date getCurrentDate(){
        return new Date();
    }

    /**
     * Converts the date stamp and returns just the current time
     * @return current time
     */
    public String getCurrentTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(getCurrentDate());
    }

}
