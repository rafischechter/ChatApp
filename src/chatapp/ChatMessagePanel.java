package chatapp;

import javax.swing.*;

/**
 * Shows the information for a single Message object
 *
 * Allows images and text to be shown for a single message
 */
public class ChatMessagePanel extends JPanel{

    private Message message;

    private JLabel messageText = new JLabel();
    private JLabel image = new JLabel();

    public ChatMessagePanel(Message message) {
        this.message = message;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /**
         * checks if there is any text in the message
         */
        if (message.hasText()) {
            messageText.setText(message.getFormattedTimeStamp() + " - " + message.getText());

            this.add(messageText);
        }

        /**
         * checks if there is an image being sent as part of the message
         */
        if (message.hasImage()) {
            image.setIcon(message.getImage());
            this.add(image);
        }

    }

}
