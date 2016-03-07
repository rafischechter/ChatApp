package chatapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The GUI for a single room that the client can join by double clicking on.
 */
public class ChatRoomListItem extends JPanel {

    private ChatRoom room;

    private JLabel iconsLabel = new JLabel();
    private JLabel roomName;
    private JLabel roomTopic;

    private Client client;

    ChatRoomListItem(Client client, ChatRoom room) {

        this.client = client;
        this.room = room;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        roomName = new JLabel("Name: " + this.room.getRoomName());
        roomTopic = new JLabel("Topic:  " + this.room.getDiscussionTopic());

        int width = 180;
        int height = 40;
        if (room.isPasswordProtected())
            height += 20;

        this.setPreferredSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));


        if (room.isPasswordProtected()) {
            iconsLabel.setIcon(new ImageIcon("images/lock_icon.png"));
            this.add(iconsLabel);
        }
        this.add(roomName);
        this.add(roomTopic);

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //check if it is a double click
                if (e.getClickCount() == 2) {
                    //send the user the id of the room so it can request to join
                    sendClientClickedRoomId();
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    /**
     * Sends the client the id of the room and whether or not it requires a password
     * when they double click on it
     */
    private void sendClientClickedRoomId() {
        client.requestToJoinRoom(this.room.getId(), this.room.isPasswordProtected());
    }
}
