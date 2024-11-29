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

        backgroundPanel.setLayout(new GridLayout(3, 2));
        gameOverFrame.setContentPane(backgroundPanel); // Set the custom panel as the frame's content pane

        // Restart Buttom

        JButton restartButton = new JButton("Restart Game");
        restartButton.setSize(70,50);
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setForeground(Color.GREEN);
        restartButton.setOpaque(false);
        restartButton.setContentAreaFilled(false);
        restartButton.setBorderPainted(false);

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the Game Over screen and start the game
                gameOverFrame.dispose();
                try {
                    Main.Game(); // Start the main game
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Quit Button

        JButton quitbutton = new JButton("Quit Game");
        quitbutton.setSize(70,50);
        quitbutton.setFont(new Font("Arial", Font.BOLD, 20));
        quitbutton.setForeground(Color.RED);
        quitbutton.setOpaque(false);
        quitbutton.setContentAreaFilled(false);
        quitbutton.setBorderPainted(false);

        quitbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the Game Over screen and start the game
                gameOverFrame.dispose();
                System.exit(0);
            }
        });

        // Add components to the frame
        backgroundPanel.add(new JLabel()); // Spacer
        backgroundPanel.add(new JLabel()); // Spacer
        backgroundPanel.add(new JLabel()); // Spacer
        backgroundPanel.add(new JLabel()); // Spacer
        backgroundPanel.add(restartButton); // Add the button
        backgroundPanel.add(quitbutton); // Add the button

        // Make the frame visible
        gameOverFrame.setVisible(true);
    }
}