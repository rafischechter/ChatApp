package chatapp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Yitzi on 2/23/2016.
 */
public class ChatRoomListPanel extends JScrollPane {

    private JPanel rooms = new JPanel();
    private Client client;

    ChatRoomListPanel(Client client) {

        this.client = client;

        this.setViewportView(rooms);
        this.setPreferredSize(new Dimension(100, 300));
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.getVerticalScrollBar().setUnitIncrement(4);

        rooms.setLayout(new BoxLayout(rooms, BoxLayout.Y_AXIS));
    }

    /**
     * Adds a chatroom
     * @param room room to be added to the list
     */
    public void addRoom(ChatRoom room) {
        this.rooms.add(new ChatRoomListItem(client, room));
        this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
        this.revalidate();
    }

    /**
     * Adds all rooms to the list from a list
     *
     * @param rooms The rooms to be added.
     */
    public void addRoomsFromList(ArrayList<ChatRoom> rooms) {
        for(ChatRoom cr : rooms) {
            addRoom(cr);
        }
    }

    /**
     * Removes all the rooms from the panel
     */
    public void removeAllRooms() {
        rooms.removeAll();
        this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
        this.revalidate();
    }

}
