package unit_tests;

import chatapp.Message;
import org.junit.Test;

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

    }


    @Test
    public void testSetAndGetImage() throws Exception {

    }

    @Test
    public void testSetAndGetFile() throws Exception {

    }

    @Test
    public void testGetCurrentDate() throws Exception {
        /**
         * No test that can currently be run on this method
         *
         * It created a new Date object from the Date class
         */
    }

    @Test
    public void testGetCurrentTime() throws Exception {


    }

    @Test
    public void testHasText() throws Exception {
        Message m = new Message();
        assertEquals("test message has no text immediately after creation", false, m.hasText());
        m.setText("Hello World");
        assertEquals("test message has text", true, m.hasText());
        m.setText("");
        assertEquals("test message has no text with empty string", false, m.hasText());

    }

    @Test
    public void testHasImage() throws Exception {

    }

    @Test
    public void testHasFile() throws Exception {

    }
}
