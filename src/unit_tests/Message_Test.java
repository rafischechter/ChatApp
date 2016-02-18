package unit_tests;

import chatapp.Message;
import org.junit.Test;

import javax.swing.*;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Unit tests for the chatapp.Message Class
 */
public class Message_Test {


    @Test
    public void testSetAndGetUserId() throws Exception {
        Message m = new Message();
        m.setUserId("5");
        assertEquals("testing setter and getter for userId", "5", m.getUserId());
    }

    /*
        The getter and setter for the timestamp can't be tested
        because it works based off the current time that the Message is created
    */

    @Test
    public void testSetAndGetSetText() throws Exception {
        Message m = new Message();
        m.setText("Hello World");
        assertEquals("testing setter and getter for text", "Hello World", m.getText());
    }

    /*
        The getCurrentDate() method doesn't need to be tested because it
        relies on the testing of the Date class
     */

    /*
        The getFormattedTimeStamp() method can't be tested because it uses the
        timeStamp variable is only set upon creation. It relies on the testing of the
        SimpleDateFormat class
     */


    @Test
    public void testHasText() throws Exception {
        Message m = new Message();
        assertEquals("test message has no text immediately after creation", false, m.hasText());
        m.setText("Hello World");
        assertEquals("test message has text", true, m.hasText());
        m.setText("");
        assertEquals("test message has no text with empty string", false, m.hasText());
        m.setText("           ");
        assertEquals("test message has no text with only whitespace", false, m.hasText());

    }

    @Test
    public void testRemoveImage() {
        Message m = new Message();
        m.setImage(new ImageIcon());
        m.removeImage();
        assertEquals("test remove image works", null, m.getImage());
    }

    @Test
    /*
        This also tests the getImage and setImage methods
     */
    public void testHasImage() throws Exception {
        Message m = new Message();
        assertEquals("test message has no image immediately after creation", false, m.hasImage());
        m.setImage(new ImageIcon());
        assertEquals("test message has an image after one is set", true, m.hasImage());
        m.removeImage();
        assertEquals("test message has no image after it is removed", false, m.hasImage());
    }

    @Test
    /*
        This also tests the getFile and setFile methods
     */
    public void testHasFile() throws Exception {
        Message m = new Message();
        assertEquals("test message has no file immediately after creation", false, m.hasFile());
        m.setFile(new File(""));
        assertEquals("test message has a file after one is set", true, m.hasFile());
        m.removeFile();
        assertEquals("test message has no file after it is removed", false, m.hasFile());
    }

    @Test
    public void testRemoveFile() {
        Message m = new Message();
        m.setFile(new File(""));
        m.removeFile();
        assertEquals("test remove file works", null, m.getFile());
    }

}
