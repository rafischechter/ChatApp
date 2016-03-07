package chatapp;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
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
    private JButton viewAttachedImgButton = new JButton("View Image");

    private ImageIcon attachedImage = null;

    private JFileChooser fc = new JFileChooser();

    private final static Dimension MAX_IMG_SIZE = new Dimension(300, 300);

    public ClientGUI(Client client) {
        this.client = client;
        createAndShowGUI();

    }

    private void createAndShowGUI() {

        //sets up panel for typing out and sending messages
        sendMsgPanel.setLayout(new BoxLayout(sendMsgPanel, BoxLayout.X_AXIS));
        sendMsgPanel.add(chatText);
        this.viewAttachedImgButton.setEnabled(false);
        sendMsgPanel.add(viewAttachedImgButton);
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
                    client.sendNewRoomInfo(dialog.getRoomName(), dialog.getRoomTopic(), dialog.getRoomPassword());
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

        // allow user to attach/remove an image to/from their message
        addRemoveImgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (attachedImage == null) {
                    addImageToMessage();
                }
                else {
                    removeImageFromMessage();
                }
            }
        });

        // allow the user to view their attached image
        viewAttachedImgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "", "Image Viewer", JOptionPane.PLAIN_MESSAGE, attachedImage );
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

    /**
     * gets rid of the GUI
     */
    public void close() {
        this.dispose();
    }

    /**
     * Creates and sends a new Message object to the server to be added
     */
    private void sendMessage() {
        if (client.isInRoom()) {
            if (!chatText.getText().trim().equals("") || attachedImage != null) {
                Message message = new Message(client.getUser());
                message.setText(chatText.getText());

                if (attachedImage != null) {
                    message.setImage(attachedImage);
                }

                try {
                    client.sendActionCode(Server.ActionCodes.NEW_MESSAGE);
                    client.sendMessage(message);
                    chatText.setText("");
                    removeImageFromMessage();
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

    /**
     * Adds a room to the list of rooms the user can join
     *
     * @param room The room being added to the list
     */
    public void addNewRoom(ChatRoom room) {
        roomsList.addRoom(room);
    }

    /**
     * Sets up the UI of a new room when the user joins it
     *
     * @param room The room being joined
     */
    public void setUpNewRoom(ChatRoom room) {
        this.chatRoomMessagesPanel.removeAllMessages();
        for (Message m : room.getMessageList()) {
           addNewMessage(m);
        }

        //change header label
        setHeaderLabel(null, room.getRoomName(), room.getDiscussionTopic());

    }

    /**
     * Displays a new message
     * @param message The message being displayed
     */
    public void addNewMessage(Message message) {
        chatRoomMessagesPanel.addMessage(message);
        chatRoomMessagesPanel.getVerticalScrollBar().setValue(chatRoomMessagesPanel.getVerticalScrollBar().getMaximum());
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
        this.userInfoLabel.setText("<html>Your User Name: " + client.getUser().getUserName()
                + "<br>Current Room Name: " + roomName
                + "<br>Current Room Topic: " + roomTopic + "</html>");
    }

    /**
     * Adds an image to an unsent message
     *
     * (technically the Message isn't created until the user hits send, this justinstantiates the image that will be
     *  added to a new Message object later)
     */
    private void addImageToMessage() {

        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(new FileNameExtensionFilter("Images", "bmp", "jpeg", "tiff", "tif", "jpg", "gif", "png"));
        int returnValue = fc.showDialog(this, "Attach Image");

        if (returnValue == JFileChooser.APPROVE_OPTION) {

            File file = fc.getSelectedFile();

            this.attachedImage = new ImageIcon(file.getAbsolutePath());

            //check if image needs to be resized
            if (this.attachedImage.getIconWidth() > MAX_IMG_SIZE.width
                    || this.attachedImage.getIconHeight() > MAX_IMG_SIZE.height) {
                this.attachedImage = resizeImage(this.attachedImage);
            }

            this.addRemoveImgButton.setText("Remove Image");
            this.viewAttachedImgButton.setEnabled(true);

        }
    }

    /**
     * Removes an image if it was added
     */
    private void removeImageFromMessage() {
        this.attachedImage = null;
        this.addRemoveImgButton.setText("Add Image");
        this.viewAttachedImgButton.setEnabled(false);
    }

    /**
     * resizes an image to be within an acceptable size
     *
     * @param imageIcon The image to resize
     * @return The resized image
     */
    private ImageIcon resizeImage(ImageIcon imageIcon) {

        Dimension currImageSize = new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        Dimension newSize = getNewImageSizeMaintainingAspectRation(currImageSize, MAX_IMG_SIZE);

        Image img = imageIcon.getImage();

        Image resizedImage = img.getScaledInstance(newSize.width, newSize.height,  java.awt.Image.SCALE_SMOOTH);

        return new ImageIcon(resizedImage);

    }

    /**
     * get a new size for resizing an image that maintains its aspect ratio
     *
     * @param imageSize The size of the original image
     * @param maxAllowedSize the max size the image is allowed to be
     *
     * @return the new size with the aspect ration maintained
     */
    private Dimension getNewImageSizeMaintainingAspectRation(Dimension imageSize, Dimension maxAllowedSize) {
        int original_width = imageSize.width;
        int original_height = imageSize.height;
        int bound_width = maxAllowedSize.width;
        int bound_height = maxAllowedSize.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }

}
