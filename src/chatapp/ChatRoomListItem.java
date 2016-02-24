package chatapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Yitzi on 2/23/2016.
 */
public class ChatRoomListItem extends JPanel {

    private ChatRoom room;

    JLabel roomName;
    JLabel roomTopic;

    ChatRoomListItem(ChatRoom room) {

        this.room = room;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        roomName = new JLabel("Name: " + this.room.getRoomName());
        roomTopic = new JLabel("Topic:  " + this.room.getDiscussionTopic());

        this.setPreferredSize(new Dimension(180, 40));
        this.setMaximumSize(new Dimension(180, 40));
        this.setMinimumSize(new Dimension(180, 40));

        this.add(roomName);
        this.add(roomTopic);

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //check if it is a double click
                if (e.getClickCount() == 2) {
                    //add user to the room and make it visible

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
}
