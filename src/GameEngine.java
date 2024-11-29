import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GameEngine implements Engine, KeyListener {
    private DynamicSprite hero;
    private ArrayList<Sprite> environment;
    private Set<Integer> pressedKeys = new HashSet<>();
    private Timer staminaDepletionTimer;
    private Timer staminaRegenTimer;

    public GameEngine(DynamicSprite hero, HUD hud) {
        this.hero = hero;
        this.staminaDepletionTimer = new Timer(1000, e -> {
            if (hero.getRunning()) {
                hero.decreaseStamina();
            }
        });
        this.staminaRegenTimer = new Timer(2000, e -> {
            if (!hero.getRunning()) {
                hero.regenerateStamina();
            }
        });
        this.staminaDepletionTimer.start();
        this.staminaRegenTimer.start();
    }


    @Override
    public void update() {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
        updateMovement();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
        if (pressedKeys.isEmpty()) {
            hero.setWalking(false);
        }
        if (!pressedKeys.contains(KeyEvent.VK_SHIFT)) {
            hero.setRunning(false); // Stop running if Shift key is released
        }
    }

    private void updateMovement() {
        boolean up = pressedKeys.contains(KeyEvent.VK_W);
        boolean down = pressedKeys.contains(KeyEvent.VK_S);
        boolean left = pressedKeys.contains(KeyEvent.VK_A);
        boolean right = pressedKeys.contains(KeyEvent.VK_D);
        boolean sprint = pressedKeys.contains(KeyEvent.VK_SHIFT);

        if (sprint) {
            hero.setRunning(true);
        } else {
            hero.setRunning(false);
        }

        if (up && left) {
            hero.setWalking(true);
            hero.setDirection(Direction.NORTH_WEST);
        } else if (up && right) {
            hero.setWalking(true);
            hero.setDirection(Direction.NORTH_EAST);
        } else if (down && left) {
            hero.setWalking(true);
            hero.setDirection(Direction.SOUTH_WEST);
        } else if (down && right) {
            hero.setWalking(true);
            hero.setDirection(Direction.SOUTH_EAST);
        } else if (up) {
            hero.setWalking(true);
            hero.setDirection(Direction.NORTH);
        } else if (down) {
            hero.setWalking(true);
            hero.setDirection(Direction.SOUTH);
        } else if (left) {
            hero.setWalking(true);
            hero.setDirection(Direction.WEST);
        } else if (right) {
            hero.setWalking(true);
            hero.setDirection(Direction.EAST);
        } else {
            hero.setWalking(false);
        }
    }
}
