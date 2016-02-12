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

    public ImageIcon getImage() {
        return this.img;
    }

    public void setImage(ImageIcon image) {
        this.img = image;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
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

    /**
     * checks if the message sent is text
     * @return whether the message is text
     */
    public boolean isText() {
        return this.messageText != null;
    }

    /**
     * checks if the message sent is an image
     * @return whether the message is an image
     */
    public boolean isImage() {
        return this.img != null;
    }

    /**
     * checks if the message sent is a file
     * @return whether the message is a file
     */
    public boolean isFile() {
        return this.file != null;
    }

}
