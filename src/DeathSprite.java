import java.awt.geom.Rectangle2D;
import java.awt.*;


public class DeathSprite extends Sprite {
    public DeathSprite(int x, int y, Image image, int width, int height) {
        super(x, y, image, width, height,null, null);
    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D.Double(x,y,(double) width,(double) height);
    }

    public boolean intersect(Rectangle2D.Double hitBox){
        return this.getHitBox().intersects(hitBox);
    }
}
