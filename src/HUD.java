import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HUD extends JPanel implements Engine, Displayable {
    protected double x;
    protected double y;
    protected final Image image;
    protected final double width;
    protected final double height;
    protected Double number;

    public void changeNumber(double delta) {
        this.number += delta;
        if (this.number < 0) {
            this.number = (double) 0;
        }
        repaint();
    }

    public Double getNumber() {
        return number;
    }

    public HUD(double x, double y, Image image, double width, double height, Double number) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
        this.height = height;
        this.number = number;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (double i = 0; i < (double) this.number; i++){
            g.drawImage(this.image, (int) i * this.image.getWidth(null),(int) y, null);
        }
    }

    @Override
    public void draw(Graphics g) throws IOException {
        paintComponent(g);
    }

    @Override
    public void update() {
        repaint();
    }
}
