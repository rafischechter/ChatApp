package chatapp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The server interface
 */
public class ServerGUI extends JFrame {

    private Server server;

    private JPanel mainPanel = new JPanel();

    private JButton turnOnserver = new JButton("Turn on Server");
    private JButton shutOffserver = new JButton("Shut off Server");


    public ServerGUI(Server server) {
        this.server = server;
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        mainPanel.add(turnOnserver);
        mainPanel.add(shutOffserver);
        shutOffserver.setEnabled(false);
        this.add(mainPanel);
        this.setTitle("Server");
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

        turnOnserver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.turnOnServer();
                shutOffserver.setEnabled(true);
                turnOnserver.setEnabled(false);
            }
        });

        shutOffserver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.shutOffServer();
                turnOnserver.setEnabled(true);
                shutOffserver.setEnabled(false);
            }
        });
    }
}
