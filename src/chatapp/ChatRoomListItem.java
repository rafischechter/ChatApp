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

    private JLabel roomName;
    private JLabel roomTopic;

    Client client;

    ChatRoomListItem(Client client, final ChatRoom room) {

        this.client = client;

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
                    //send the user the id of the room so it can request to join
                    sendClientClickedRoomId(room.getId());
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

    private void sendClientClickedRoomId(int id) {
        client.requestToJoinRoom(id);
    }
}
