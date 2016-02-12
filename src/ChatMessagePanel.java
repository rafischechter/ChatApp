import javax.swing.*;

/**
 * Created by Yitzi on 2/11/2016.
 */
public class ChatMessagePanel extends JPanel{

    private Message message;

    private JLabel messageText = new JLabel();
    private JLabel image = new JLabel();

    public ChatMessagePanel(Message message) {
        this.message = message;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        if (message.hasText()) {
            messageText.setText(message.getMessageText());
            this.add(messageText);
        }

        if (message.hasImage()) {
            image.setIcon(message.getImage());
            this.add(image);
        }
    }



}
