import javax.swing.*;
import java.awt.*;


/**
 * The client user interface
 */
public class ClientGUI extends JFrame {

    public ClientGUI(){
        createAndShowGUI();

    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame();
        frame.setTitle("Client Demo");
        frame.setPreferredSize(new Dimension(1080,760));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //JPanel rightPanel = new JPanel();

        //JPanel leftPanel = new JPanel();

        //JPanel topPanel = new JPanel();

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(Color.red);


        JTextField textField = new JTextField(50);

        JButton sendMessage = new JButton("Send");

        JTextArea messageArea = new JTextArea();

        //Create the menu bar.
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setPreferredSize(new Dimension(1080, 20));


        bottomPanel.add(textField);
        bottomPanel.add(sendMessage);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setJMenuBar(menuBar);
        frame.getContentPane();

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }




}
