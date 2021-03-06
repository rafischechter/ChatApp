package unit_tests;

import chatapp.Message;
import org.junit.Test;

import javax.swing.*;
import java.io.File;

import static org.junit.Assert.*;

/**
 * Unit tests for the chatapp.Message Class
 */
public class Message_Test {



    /*
        The getter and setter for the timestamp can't be tested
        because it works based off the current time that the Message is created
    */

    @Test
    public void testSetAndGetSetText() throws Exception {
        Message m = new Message();
        m.setText("Hello World");
        assertEquals("testing setter and getter for standard text", "Hello World", m.getText());
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
        assertEquals("test if message has text immediately after creation", false, m.hasText());
        m.setText("Hello World");
        assertEquals("test if message has text with standard text", true, m.hasText());
        m.setText("");
        assertEquals("test if message has text when the string is  empty", false, m.hasText());
        m.setText("           ");
        assertEquals("test if message has text when the text is only whitespace", false, m.hasText());

    }

    @Test
    public void testRemoveImage() {
        Message m = new Message();
        m.setImage(new ImageIcon());
        m.removeImage();
        assertEquals("test if remove image works", null, m.getImage());
    }

    @Test
    /*
        This also tests the getImage and setImage methods
     */
    public void testHasImage() throws Exception {
        Message m = new Message();
        assertEquals("test if message has an image immediately after creation", false, m.hasImage());
        m.setImage(new ImageIcon());
        assertEquals("test if message has an image after one is set", true, m.hasImage());
        m.removeImage();
        assertEquals("test if message has an image after it is removed", false, m.hasImage());
    }

    @Test
    /*
        This also tests the getFile and setFile methods
     */
    public void testHasFile() throws Exception {
        Message m = new Message();
        assertEquals("test if message has a file immediately after creation", false, m.hasFile());
        m.setFile(new File(""));
        assertEquals("test if message has a file after one is set", true, m.hasFile());
        m.removeFile();
        assertEquals("test if message has a file after it is removed", false, m.hasFile());
    }

    @Test
    public void testRemoveFile() {
        Message m = new Message();
        m.setFile(new File(""));
        m.removeFile();
        assertEquals("test if file variable is null after it is removed", null, m.getFile());
    }

    @Test
    public void testSetImageWithNullValueRemovesTheCurrentImage() {
        ImageIcon actualImage = new ImageIcon();
        ImageIcon nullImage = null;

        Message message = new Message();
        message.setImage(actualImage);
        assertSame(actualImage, message.getImage());
        message.setImage(nullImage);
        assertNull(message.getImage());
    }


    @Test
    public void testSetFileWithNullValueRemovesTheCurrentFile() {
        File actualFile = new File("");
        File nullFile = null;

        Message message = new Message();
        message.setFile(actualFile);
        assertSame(actualFile, message.getFile());
        message.setFile(nullFile);
        assertNull(message.getFile());
    }

}
