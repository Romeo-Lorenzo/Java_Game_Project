import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class WelcomeScreen extends JPanel{

    public static void welcomeScreen(String[] args) {

        // Create the main frame
        JFrame welcomeScreen;
        welcomeScreen = new JFrame("Java Labs");
        welcomeScreen.setSize(400,600);
        welcomeScreen.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add a welcome label
        JLabel welcomeLabel = new JLabel("Welcome, press Start to play", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.GREEN);

        // Create a custom panel for the background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("./img/WelcomeScreenBackground.jpg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new GridLayout(7, 1));
        welcomeScreen.setContentPane(backgroundPanel); // Set the custom panel as the frame's content pane

        // Add a 'Start Game' button (on top of the background)
        JButton startButton = new JButton("Start Game");
        startButton.setSize(100,50);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
    startButton.setForeground(Color.GREEN);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);

        // Set up button action
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the welcome screen and start the game
                welcomeScreen.dispose();
                try {
                    Main.Game(); // Start the main game
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Add components to the frame
        backgroundPanel.add(new JLabel()); // Spacer
        backgroundPanel.add(welcomeLabel);
        backgroundPanel.add(new JLabel()); // Spacer
        backgroundPanel.add(startButton); // Add the button
        backgroundPanel.add(new JLabel()); // Spacer
        backgroundPanel.add(new JLabel()); // Spacer
        backgroundPanel.add(new JLabel());


        // Make the frame visible
        welcomeScreen.setVisible(true);
    }
}
