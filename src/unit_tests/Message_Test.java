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
    public void testSetAndGetSetText() throws Exception {
        Message m = new Message();
        m.setText("Hello World");
        assertEquals("testing setter and getter for text", "Hello World", m.getText());
    }

    @Test
    public void testSetAndGetUserId() throws Exception {
        Message m = new Message();
        m.setUserId("5");
        assertEquals("testing setter and getter for userId", "5", m.getUserId());
    }


    @Test
    public void testSetAndGetTimeStamp() throws Exception {
        /*
            can't be tested as it works based off the current time that
            the Message is created
         */
    }


    @Test
    public void testSetAndGetImage() throws Exception {
        /*
            unsure how to test beyond checking if the variable is not null
            which is tested in another test
         */
    }

    @Test
    public void testSetAndGetFile() throws Exception {
        /*
            unsure how to test beyond checking if the variable is not null
            which is tested in another test
         */
    }

    @Test
    public void testGetCurrentDate() throws Exception {
        /*
         * No test that can currently be run on this method
         *
         * It created a new Date object from the Date class
         */
    }

    @Test
    public void tesGetFormattedTimeStamp() throws Exception {
        /*
            No way to test this method without being able to
            test getCurrentDate
         */
    }

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
    public void testHasImage() throws Exception {
        Message m = new Message();
        assertEquals("test message has no image immediately after creation", false, m.hasImage());
        m.setImage(new ImageIcon());
        assertEquals("test message has an image after one is set", true, m.hasImage());
        m.removeImage();
        assertEquals("test message has no image after it is removed", false, m.hasImage());
    }

    @Test
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
