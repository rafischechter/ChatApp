import javax.swing.*;

/**
 * Created by Yitzi on 2/11/2016.
 */
public class ChatMessagePanel extends JPanel{

    Message message;

    JLabel messageText = new JLabel();
    JLabel image = new JLabel();

    public ChatMessagePanel(Message message) {
        this.message = message;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        if (message.isText()) {
            messageText.setText(message.getMessageText());
            this.add(messageText);
        }

        if (message.isImage()) {
            image.setIcon(message.getImage());
            this.add(image);
        }
    }



}
