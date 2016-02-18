package chatapp;

import javax.swing.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private String userId;
    private Date timeStamp;
    private String text;
    private ImageIcon img;
    private File file;

    public Message() {
        setTimeStamp();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userID) {
        this.userId = userID;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * Used to set the time stamp of the message.
     *
     * Should only be called by the class constructor
     */
    private void setTimeStamp() {
        this.timeStamp = getCurrentDate();
    }

    public String getText() {
        return text;
    }

    public void setText(String messageText) {
        this.text = messageText;
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
    public String getFormattedTimeStamp(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(getTimeStamp());
    }

    /**
     * checks if the message sent is text
     * @return whether the message is text
     */
    public boolean hasText() {
        return text != null && !text.trim().equals("");
    }


    /**
     * Removes the image attached to the message
     */
    public void removeImage() {
        this.img = null;
    }

    /**
     * checks if the message sent is an image
     * @return whether the message is an image
     */
    public boolean hasImage() {
        return this.img != null;
    }


    /**
     * Removes the file attached to the message
     */
    public void removeFile() {
        this.file = null;
    }

    /**
     * checks if the message sent is a file
     * @return whether the message is a file
     */
    public boolean hasFile() {
        return this.file != null;
    }

}
