package chatapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/**
 * The client user interface
 */
public class ClientGUI extends JFrame {

    private Client client;

    private JPanel mainPanel = new JPanel();
    private JPanel chatPanel = new JPanel();
    private JPanel sendMsgPanel = new JPanel();

    private JPanel roomsPanel = new JPanel();
    private JPanel roomTabsPanel = new JPanel();
    private JPanel roomInfoPanel = new JPanel();
    private JButton usersButton = new JButton("Users");
    private JButton roomsButton = new JButton("Rooms");
    private JLabel roomInfoLabel = new JLabel("room info");

    private JLabel userInfoLabel = new JLabel("user information goes here");
    private ChatRoomPanel chatRoomPanel = new ChatRoomPanel();
    private JTextField chatText = new JTextField();
    private JButton sendButton = new JButton("Send");
    Message m1;


    public ClientGUI(Client client) {
        this.client = client;
        createAndShowGUI();

    }

    private void createAndShowGUI() {

        //sets up panel for typing out and sending messages
        sendMsgPanel.setLayout(new BorderLayout());
        sendMsgPanel.add(chatText, BorderLayout.CENTER);
        sendMsgPanel.add(sendButton, BorderLayout.EAST);

        //sets up panel where chat messages are shown
        chatPanel.setLayout(new BorderLayout());
        chatPanel.add(userInfoLabel, BorderLayout.NORTH);
        chatPanel.add(chatRoomPanel, BorderLayout.CENTER);
        chatPanel.add(sendMsgPanel, BorderLayout.SOUTH);

        //sets up panel for tabs to switch between viewing rooms and users
        roomTabsPanel.add(usersButton);
        roomTabsPanel.add(roomsButton);

        //sets up panel for showing the available rooms and users
        roomInfoPanel.add(roomInfoLabel);

        //sets up right side panel
        roomsPanel.setLayout(new GridLayout(0, 1));
        roomsPanel.add(roomTabsPanel);
        roomsPanel.add(roomInfoPanel);

        //sets up left side panel
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(chatPanel, BorderLayout.WEST);
        mainPanel.add(roomsPanel);

        //sets up frame
        this.add(mainPanel);
        this.setTitle("ChatApp");
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

        //click listener for send buttons
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message message = new Message();
                message.setText(chatText.getText());
                try {
                    client.sendActionCode(Server.ActionCodes.NEW_MESSAGE);
                    client.sendMessage(message);
                    chatText.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    public void addNewMessage(Message message) {
        chatRoomPanel.addMessage(message);
    }




}
