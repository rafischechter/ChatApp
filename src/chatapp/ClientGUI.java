package chatapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    //private JLabel roomsListHeaderLabel = new JLabel("Rooms", SwingConstants.CENTER);
    private ChatRoomListPanel roomsList;
    private JButton addRoomButton = new JButton("Create New Room");

    private JLabel userInfoLabel = new JLabel();
    private ChatRoomMessagesPanel chatRoomMessagesPanel = new ChatRoomMessagesPanel();
    private JTextField chatText = new JTextField();
    private JButton sendButton = new JButton("Send");
    private JButton addRemoveImgButton = new JButton("Add Image");


    public ClientGUI(Client client) {
        this.client = client;
        createAndShowGUI();

    }

    private void createAndShowGUI() {

        //sets up panel for typing out and sending messages
        sendMsgPanel.setLayout(new BoxLayout(sendMsgPanel, BoxLayout.X_AXIS));
        sendMsgPanel.add(chatText);
        sendMsgPanel.add(addRemoveImgButton);
        sendMsgPanel.add(sendButton);

        //sets up left side panel where chat messages are shown
        chatPanel.setLayout(new BorderLayout());
        userInfoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        chatPanel.add(userInfoLabel, BorderLayout.NORTH);
        chatPanel.add(chatRoomMessagesPanel, BorderLayout.CENTER);
        chatPanel.add(sendMsgPanel, BorderLayout.SOUTH);

        //sets up panel for showing the available rooms and users
        roomInfoPanel.setLayout(new BoxLayout(roomInfoPanel, BoxLayout.Y_AXIS));
        roomsList = new ChatRoomListPanel(client);
        //roomsListHeaderLabel.setPreferredSize(new Dimension(120, 50));
        //roomsListHeaderLabel.setFont(new Font("Times Roman", Font.BOLD, 24));
        //roomInfoPanel.add(roomsListHeaderLabel);
        roomInfoPanel.add(roomsList);
        roomInfoPanel.add(addRoomButton);

        //sets up right side panel
        roomsPanel.setLayout(new GridLayout(0, 1));
        //roomsPanel.add(roomTabsPanel);
        roomsPanel.add(roomInfoPanel);

        //sets up main panel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.add(chatPanel);
        mainPanel.add(roomsPanel);

        //sets up frame
        this.add(mainPanel);
        this.setTitle("ChatApp");
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        //click listener for new room button
        addRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewRoomDialog dialog = new NewRoomDialog();

                try {
                    client.sendActionCode(Server.ActionCodes.NEW_CHATROOM);
                    client.sendNewRoomInfo(dialog.getRoomName(), dialog.getRoomTopic());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        //click listener for send buttons
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // send message by pressing the return key
        chatText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                client.requestShutDown();

            }
        });

    }

    public void close() {
        this.dispose();
    }

    private void sendMessage() {
        if (client.isInRoom()) {
            if (!chatText.getText().trim().equals("")) {
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
            else
                JOptionPane.showMessageDialog(null, "You can't send empty messages", "Error", JOptionPane.ERROR_MESSAGE);

        }
        else {
            JOptionPane.showMessageDialog(null, "You must be in a room to send a message", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addNewRoom(ChatRoom room) {
        roomsList.addRoom(room);
    }

    public void setUpNewRoom(ChatRoom room) {
        this.chatRoomMessagesPanel.removeAllMessages();
        for (Message m : room.getMessageList()) {
           addNewMessage(m);
        }

        //change header label
        setHeaderLabel(null, room.getRoomName(), room.getDiscussionTopic());

    }

    public void addNewMessage(Message message) {
        chatRoomMessagesPanel.addMessage(message);
    }

    public void setHeaderLabel(String userName, String roomName, String roomTopic) {
        if (userName == null || userName.equals("")) {
            userName = "none";
        }

        if (roomName == null || roomName.equals("")) {
            roomName = "none";
        }

        if (roomTopic == null || roomTopic.equals("")) {
            roomTopic = "none";
        }


        this.userInfoLabel.setText("<html>Your User Name: " + userName
                + "<br>Current Room Name: " + roomName
                + "<br>Current Room Topic: " + roomTopic + "</html>");
    }


}
