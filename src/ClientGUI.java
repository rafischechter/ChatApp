import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


/**
 * The client user interface
 */
public class ClientGUI extends JFrame {

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
    private JTextArea chatMessagesArea = new JTextArea(50, 100);
    private JTextField chatText = new JTextField();
    private JButton sendButton = new JButton("Send");


    public ClientGUI(){
        createAndShowGUI();

    }

    private void createAndShowGUI() {

        sendMsgPanel.setLayout(new BorderLayout());
        sendMsgPanel.add(chatText, BorderLayout.CENTER);
        sendMsgPanel.add(sendButton, BorderLayout.EAST);

        chatPanel.setLayout(new BorderLayout());
        chatPanel.add(userInfoLabel, BorderLayout.NORTH);
        chatPanel.add(chatMessagesArea, BorderLayout.CENTER);
        chatPanel.add(sendMsgPanel, BorderLayout.SOUTH);

        roomTabsPanel.add(usersButton);
        roomTabsPanel.add(roomsButton);

        roomInfoPanel.add(roomInfoLabel);

        roomsPanel.setLayout(new GridLayout(0, 1));
        roomsPanel.add(roomTabsPanel);
        roomsPanel.add(roomInfoPanel);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(chatPanel, BorderLayout.WEST);
        mainPanel.add(roomsPanel);

        this.add(mainPanel);
        this.pack();
        //this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);


    }

}
