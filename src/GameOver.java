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

public class GameOver extends JPanel{

    public static void gameOverScreen(String[] args) {
        Main.stopAndClearTimers();

        // Create the main frame
        JFrame gameOverFrame;
        gameOverFrame = new JFrame("Game Over");
        gameOverFrame.setSize(1080,720);
        gameOverFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("./img/GameOver.jpg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new GridLayout(4, 2));
        gameOverFrame.setContentPane(backgroundPanel);

        // Add a 'Start Game' button (on top of the background)
        JButton playButton = new JButton("");
        playButton.setOpaque(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);

        // Set up button action
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the welcome screen and start the game
                gameOverFrame.dispose();
                try {
                    Main.Game(); // Start the main game
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton quitButton = new JButton("");
        quitButton.setForeground(Color.RED);
        quitButton.setOpaque(false);
        quitButton.setContentAreaFilled(false);
        quitButton.setBorderPainted(false);

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the welcome screen and start the game
                gameOverFrame.dispose();
                try {
                    System.exit(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Add components to the frame
        backgroundPanel.add(new JLabel()); // Spacer
        backgroundPanel.add(new JLabel()); // Spacer
        backgroundPanel.add(new JLabel()); // Spacer
        backgroundPanel.add(new JLabel()); // Spacer
        backgroundPanel.add(new JLabel()); // Spacer
        backgroundPanel.add(new JLabel()); // Spacer
        backgroundPanel.add(playButton); // Add the Play button
        backgroundPanel.add(quitButton);

        // Make the frame visible
        gameOverFrame.setVisible(true);
    }
}