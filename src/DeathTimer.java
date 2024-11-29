import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DeathTimer extends JPanel implements Engine, Displayable {
    private int countdown;
    private Timer timer;

    public DeathTimer(int countdown) {
        this.countdown = countdown + 1;
        initTimer();
    }

    private void initTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(countdown);
                countdown--;
                repaint();
                if (countdown <= 0) {
                    timer.stop();
                    GameOver.gameOverScreen(null);
                }
            }
        });
        timer.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Time: " + countdown, Main.tileSize * 17 - Main.tileSize * 3, Main.tileSize + 32);
    }

    @Override
    public void draw(Graphics g) throws IOException {
        paintComponent(g);
    }

    @Override
    public void update() {
        repaint();
    }

    public static void stopAndClearTimers() {
        for (Timer timer : timers) {
            if (timer != null) {
                timer.stop();
            }
        }
        timers.clear();
    }
}