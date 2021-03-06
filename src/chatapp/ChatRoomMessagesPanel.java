package chatapp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *  A JScrollPane that shows the messages for each room
 */
public class ChatRoomMessagesPanel extends JScrollPane {

    private JPanel chatMessages = new JPanel();

    public ChatRoomMessagesPanel() {

        this.setViewportView(chatMessages);
        this.setPreferredSize(new Dimension(500,300));
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.getVerticalScrollBar().setUnitIncrement(4);

        chatMessages.setLayout(new BoxLayout(chatMessages, BoxLayout.Y_AXIS));

    }

    /**
     * Adds a new message to be shown in the GUI
     *
     * @param message The message to be shows
     */
    public void addMessage(Message message) {
        chatMessages.add(new ChatMessagePanel(message));
        this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
        this.validate();
    }

    /**
     * Adds multiple new message to be shown in the GUI from an ArrayList
     *
     * @param messages The array of messages to be shown
     */
    public void addMessagesFromList(ArrayList<Message> messages) {
        for(Message m : messages) {
            chatMessages.add(new ChatMessagePanel(m));
        }
    }

    /**
     * Removes all the messages from the GUI
     */
    public void removeAllMessages() {
        chatMessages.removeAll();
        this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
        this.validate();
    }

}
