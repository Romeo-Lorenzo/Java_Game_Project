import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    static JFrame displayZoneFrame;
    static RenderEngine renderEngine;
    static GameEngine gameEngine;
    static PhysicEngine physicEngine;
    static HUD hud;
    static List<Timer> timers = new ArrayList<>();


    public Main() throws Exception {

        WelcomeScreen.welcomeScreen(null);

    }

    public static void Game() throws IOException {
        stopAndClearTimers();


        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(400,600);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DynamicSprite hero = new DynamicSprite(200,300,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")),48,50, (double) 5,
                (double) 5);

        HUD hudHearts = new HUD(0,0,
                ImageIO.read(new File("./img/heart.png")),32,26, hero.getLifePoints());

        HUD hudStamina = new HUD(0,30,
                ImageIO.read(new File("./img/stamina.png")),32,26, hero.getStaminaPoints());

        hero.hearts = hudHearts;
        hero.stamina = hudStamina;

        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero, hud);

        Timer renderTimer = new Timer(50,(time)-> renderEngine.update());
        Timer gameTimer = new Timer(50,(time)-> gameEngine.update());
        Timer physicTimer = new Timer(50,(time)-> physicEngine.update());

        timers.add(renderTimer);
        timers.add(gameTimer);
        timers.add(physicTimer);
        renderTimer.start();
        gameTimer.start();
        physicTimer.start();


        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);

        Playground level = new Playground("./data/level1.txt");
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        renderEngine.addToRenderList(hudHearts);
        renderEngine.addToRenderList(hudStamina);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());

        displayZoneFrame.addKeyListener(gameEngine);
    }

    private static void stopAndClearTimers() {
        for (Timer timer : timers) {
            if (timer != null) {
                timer.stop();
            }
        }
        timers.clear();
    }

    public static void main (String[] args) throws Exception {
        // write your code here
        Main main = new Main();
    }
}