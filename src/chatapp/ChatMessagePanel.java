package chatapp;

import javax.swing.*;

/**
 * Shows the information for a single Message object
 *
 * Allows images and text to be shown for a single message
 */
public class ChatMessagePanel extends JPanel{

    private Message message;

    private JLabel messageHeader = new JLabel();
    private JLabel messageText = new JLabel();
    private JLabel image = new JLabel();

    public ChatMessagePanel(Message message) {
        this.message = message;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        if (!message.isEmpty()){
            //messageHeader.setText(message.getFormattedTimeStamp());
            //this.add(messageHeader);

            messageText.setText(message.getFormattedTimeStamp() + " - " + message.getUserName() + ": ");
            if (message.hasText()) {
                messageText.setText(messageText.getText() + message.getText());
            }
            this.add(messageText);

            if (message.hasImage()) {
                image.setIcon(message.getImage());
                this.add(image);
            }

        }


    }

}
