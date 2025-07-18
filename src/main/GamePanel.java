package main;

// IMPORTS
import Characters.Character;
import Characters.Player;
import ai.PathFinder;
import tile.TileManager;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    // Window settings
    final int originalTileSize = 16;
    final int scale = 3;

    final public int tileSize = originalTileSize * scale; // 48x48 tiles
    final public int maxWindowCol = 20;
    final public int maxWindowRow = 14;
    final public int windowWidth = tileSize * maxWindowCol; //960 pixels
    final public int windowHeight = tileSize * maxWindowRow; //576 pixels

    //World Settings
    final public int maxWorldCol = 100;
    final public int maxWorldRow = 100;
    public final int maxMap = 10;
    public int currentMap = 1;
    final public int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    Graphics2D g2;

    // FPS
    int FPS = 60;

    // SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;
    public PathFinder pFinder = new PathFinder(this);
    Config config = new Config(this);

    // Characters and Objects
    public Player player = new Player(this, keyH);
    public Character[][] obj = new Character[maxMap][20];
    public Character[][] npc = new Character[maxMap][10];
    public Character[][] monster = new Character[maxMap][40];
    ArrayList<Character> characterList = new ArrayList<>();
    public ArrayList<Character> projectileList = new ArrayList<>();

    // Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;

    // GamePanel Constructor
    public GamePanel() {

        this.setPreferredSize(new Dimension(windowWidth, windowHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    // Set up the game
    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        gameState = titleState;

    }

    public void retry(){
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        aSetter.setNPC();
        aSetter.setMonster();
    }

    public void restart(){
        player.setDefaultValues();
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        player.setItems();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        gameState = titleState;
        stopMusic();
    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }

    // Frames per second
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        if (gameState == playState){
            // PLAYER
            player.update();

            // NPC
            for (int i = 0; i < npc[1].length; i++){
                if (npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            }
            // MONSTER
            for (int i = 0; i < monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    if (monster[currentMap][i].alive == true && monster[currentMap][i].dying == false){
                        monster[currentMap][i].update();
                    }
                    if (monster[currentMap][i].alive == false){
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            for (int i = 0; i < projectileList.size(); i++){
                if(projectileList.get(i) != null){
                    if (projectileList.get(i).alive == true){
                        projectileList.get(i).update();
                    }
                    if (projectileList.get(i).alive == false){
                        projectileList.remove(i);
                    }
                }
            }
        }
        if (gameState == pauseState){
            // nothing, player can't move & game is paused
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // DEBUG
        long drawStart = 0;
        if (keyH.showDebugText == true){
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == titleState){
            ui.draw(g2);
        }
        // OTHERS
        else{
            // Draw tiles
            tileM.draw(g2);

            // ADD PLAYER TO LIST
            characterList.add(player);

            // ADD NPC TO LIST
            for(int i = 0; i < npc[1].length; i++){
                if (npc[currentMap][i] != null){
                    characterList.add(npc[currentMap][i]);
                }
            }

            // ADD OBJECTS TO LIST
            for(int i = 0; i < obj[1].length; i++){
                if (obj[currentMap][i] != null){
                    characterList.add(obj[currentMap][i]);
                }
            }

            // ADD MONSTERS TO LIST
            for(int i = 0; i < monster[1].length; i++){
                if (monster[currentMap][i] != null){
                    characterList.add(monster[currentMap][i]);
                }
            }

            for(int i = 0; i < projectileList.size(); i++){
                if (projectileList.get(i) != null){
                    characterList.add(projectileList.get(i));
                }
            }

            // SORT
            Collections.sort(characterList, new Comparator<Character>() {

                @Override
                public int compare(Character c1, Character c2){

                    int result = Integer.compare(c1.worldY, c2.worldY);
                    return result;
                }

            });

            // DRAW CHARACTERS
            for (int i = 0; i < characterList.size(); i++){
                characterList.get(i).draw(g2);
            }
            //EMPTY CHARACTER LIST
            characterList.clear();

            // UI
            ui.draw(g2);
        }

        // Shows draw time
        if (keyH.showDebugText == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;

            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);
            int x = 20;
            int y = 570;
            int lineHeight = 20;

            // Shows player current position
            g2.drawString("WorldX" + player.worldX, x, y);
            y += lineHeight;
            g2.drawString("WorldY" + player.worldY, x, y);
            y += lineHeight;
            g2.drawString("Col" + (player.worldX + player.solidArea.x)/tileSize, x, y);
            y += lineHeight;
            g2.drawString("Row" + (player.worldY + player.solidArea.y)/tileSize, x, y);
            y += lineHeight;

            g2.drawString("Draw Time: " + passed, x, y);
        }
        g2.dispose();
    }

    // Plays background music
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    // Stops background music
    public void stopMusic(){
        music.stop();
    }

    // Plays sound effects
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }

}