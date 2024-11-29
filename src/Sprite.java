import java.awt.*;

public class Sprite implements Displayable{
    protected double x;
    protected double y;
    protected final Image image;
    protected final double width;
    protected final double height;
    protected Double lifePoints;
    protected Double staminaPoints;

    public Sprite(double x, double y, Image image, double width, double height, Double lifePoints, Double staminaPoints) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.width = width;
        this.height = height;
        this.lifePoints = lifePoints;
        this.staminaPoints = staminaPoints;
    }

    public Double getLifePoints() {
        return lifePoints;
    }

    public Double getStaminaPoints() {
        return staminaPoints;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image,(int)x,(int)y,null);
    }
}
