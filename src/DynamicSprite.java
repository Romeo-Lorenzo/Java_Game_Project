import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite {
    private Direction direction = Direction.SOUTH;
    private double speed = 5;
    private double timeBetweenFrame = 200;
    private boolean isWalking = false;
    private boolean isInvincible = false;
    private boolean isTakingDamage = false; // Add this flag
    private final int spriteSheetNumberOfColumn = 10;
    private Timer invincibilityTimer;
    private Timer sprintTimer;
    private boolean isRunning = false;

    HUD hearts;
    HUD stamina;

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    public void setRunning(boolean running) {
        if (running && hasStamina()) {
            isRunning = true;
        } else {
            isRunning = false;
        }
    }

    public boolean getRunning() {
        return isRunning;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public DynamicSprite(double x, double y, Image image, double width, double height, Double lifePoints, Double staminaPoints) {
        super(x, y, image, width, height, lifePoints, staminaPoints);
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment) {
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch (direction) {
            case EAST:
                moved.setRect(super.getHitBox().getX() + speed, super.getHitBox().getY(),
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:
                moved.setRect(super.getHitBox().getX() - speed, super.getHitBox().getY(),
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:
                moved.setRect(super.getHitBox().getX(), super.getHitBox().getY() - speed,
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:
                moved.setRect(super.getHitBox().getX(), super.getHitBox().getY() + speed,
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH_WEST:
                moved.setRect(super.getHitBox().getX() - speed / Math.sqrt(2), super.getHitBox().getY() - speed / Math.sqrt(2),
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH_EAST:
                moved.setRect(super.getHitBox().getX() + speed / Math.sqrt(2), super.getHitBox().getY() - speed / Math.sqrt(2),
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH_WEST:
                moved.setRect(super.getHitBox().getX() - speed / Math.sqrt(2), super.getHitBox().getY() + speed / Math.sqrt(2),
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH_EAST:
                moved.setRect(super.getHitBox().getX() + speed / Math.sqrt(2), super.getHitBox().getY() + speed / Math.sqrt(2),
                        super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }

        for (Sprite s : environment) {
            if ((s instanceof SolidSprite) && (s != this)) {
                if (((SolidSprite) s).intersect(moved)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private void move() {
        if (isWalking) {
            if (isRunning){
                setSpeed(10);
            }
            else {
                setSpeed(5);
            }
            switch (direction) {
                case NORTH:
                    this.y -= speed;
                    break;
                case SOUTH:
                    this.y += speed;
                    break;
                case EAST:
                    this.x += speed;
                    break;
                case WEST:
                    this.x -= speed;
                    break;
                case NORTH_WEST:
                    this.x -= speed / Math.sqrt(2);
                    this.y -= speed / Math.sqrt(2);
                    break;
                case NORTH_EAST:
                    this.x += speed / Math.sqrt(2);
                    this.y -= speed / Math.sqrt(2);
                    break;
                case SOUTH_WEST:
                    this.x -= speed / Math.sqrt(2);
                    this.y += speed / Math.sqrt(2);
                    break;
                case SOUTH_EAST:
                    this.x += speed / Math.sqrt(2);
                    this.y += speed / Math.sqrt(2);
                    break;
            }
        }
    }

    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (isMovingPossible(environment)) {
            move();
        } else {
            this.changeLifePoints(-1);
        }
    }

    public void changeLifePoints(double change) {
        if (!isInvincible && this.lifePoints != null) {
            this.lifePoints = this.lifePoints + change;
            if (this.lifePoints < 0) {
                this.lifePoints = (double) 0;
            }
            if (this.lifePoints == 0) {
                Main.displayZoneFrame.dispose();
                this.lifePoints = null;
                GameOver.gameOverScreen(null);
            }
            if (hearts != null) {
                hearts.changeNumber(change);
            }
            startInvincibility();
        }
    }

    private void startInvincibility() {
        isInvincible = true;
        isTakingDamage = true; // Set flag to true when taking damage

        if (invincibilityTimer != null) {
            invincibilityTimer.stop();
        }

        invincibilityTimer = new Timer(2000, e -> {
            isInvincible = false;
            isTakingDamage = false; // Reset flag after invincibility period
            invincibilityTimer.stop();
        });
    }

    public void decreaseStamina(double delta) {
        if (stamina != null && stamina.getNumber() > 0) {
            stamina.changeNumber(-delta);
            if (stamina.getNumber() <= 0) {
                setRunning(false); // Stop running if stamina is depleted
            }
        }
    }

    public void regenerateStamina(double delta) {
        if (stamina != null) {
            stamina.changeNumber(delta);
            if (stamina.getNumber() > staminaPoints) {
                stamina.changeNumber(staminaPoints - stamina.getNumber()); // Cap stamina at max value
            }
        }
    }

    public boolean hasStamina() {
        return stamina != null && stamina.getNumber() > 0;
    }


    @Override
    public void draw(Graphics g) {
        int index = isWalking ? (int) (System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberOfColumn) : 5;
        int srcX1 = index * (int) width;
        int srcY1 = direction.getFrameLineNumber() * (int) height;
        int srcX2 = srcX1 + (int) width;
        int srcY2 = srcY1 + (int) height;

        // Create a temporary image for the hero
        BufferedImage tempImage = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dTemp = tempImage.createGraphics();

        // Draw the hero's sprite onto the temporary image
        g2dTemp.drawImage(image, 0, 0, (int) width, (int) height, srcX1, srcY1, srcX2, srcY2, null);

        // Apply red tint if taking damage
        if (isTakingDamage) {
            for (int y = 0; y < tempImage.getHeight(); y++) {
                for (int x = 0; x < tempImage.getWidth(); x++) {
                    int pixel = tempImage.getRGB(x, y);

                    // Extract individual components
                    int alpha = pixel & 0xff000000; // Mask to get alpha
                    if (alpha != 0) { // Only modify non-transparent pixels
                        int red = (pixel >> 16) & 0xff; // Extract red
                        int green = (pixel >> 8) & 0xff; // Extract green
                        int blue = pixel & 0xff; // Extract blue

                        // Modify the red component
                        red = Math.min(255, red + 128); // Increase red component

                        // Recombine components into a single pixel value
                        int newPixel = alpha | (red << 16) | (green << 8) | blue;

                        // Set the new pixel value
                        tempImage.setRGB(x, y, newPixel);
                    }
                }
            }
        }

        g2dTemp.dispose();

        // Draw the temporary image onto the main graphics context
        g.drawImage(tempImage, (int) x, (int) y, null);
    }
}
