package Characters;

// IMPORTS
import main.GamePanel;
import main.UtilityTool;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Character {

    GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage upattack1, upattack2, downattack1, downattack2, leftattack1, leftattack2, rightattack1, rightattack2;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = false;
    String dialogues[] = new String[20];

    // STATE
    public int worldX, worldY; // position in the world
    public String direction = "down"; // default direction
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;

    // COUNTER
    public int spriteCounter = 0;
    public int actionLookCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    public int hpBarCounter = 0;

    // CHARACTER ATTRIBUTES
    public String name;
    public int speed; // movement speed
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Character currentWeapon;
    public Character currentShield;
    public Projectile projectile;

    // ITEM ATTRIBUTES
    public ArrayList<Character> inventory = new ArrayList<>();
    public final int inventorySize = 20;
    public int attackValue;
    public int defenseValue;
    public int healValue;
    public String description = "";
    public int useCost;
    public int value;
    public int price;

    // TYPE
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int typePlayer = 0;
    public final int typeNPC = 1;
    public final int typeMonster = 2;
    public final int typeSword = 3;
    public final int typeShield = 4;
    public final int typeConsumable = 5;
    public final int typePickup = 6;

    public Character(GamePanel gp){
        this.gp = gp;
    }

    public void setAction(){}
    public void use(Character character){}
    public void checkDrop(){}

    public void dropItem(Character droppedItem){
        for (int i = 0; i < gp.obj[1].length; i++){
            if (gp.obj[gp.currentMap][i] == null){
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX; // dead monster's worldX
                gp.obj[gp.currentMap][i].worldY = worldY; // dead monster's worldY
                break;
            }
        }
    }

    public void damageReaction(){}

    // Dialogues
    public void speak(){

        if (dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }

        gp.ui.currentdialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        // Makes npc face player when speaking
        switch(gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }

    }

    public void checkCollision(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkCharacter(this, gp.npc);
        gp.cChecker.checkCharacter(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (type == typeMonster && contactPlayer == true){
            damagePlayer(attack);
        }
    }

    public void update(){
        setAction();
        checkCollision();

        // Player keeps moving if no collision
        if (collisionOn == false){

            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }

        }

        // Changes sprite, walking
        spriteCounter++;
        if (spriteCounter > 12){
            if (spriteNum == 1){
                spriteNum = 2;
            }else if (spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (invincible == true){
            invincibleCounter++;
            if (invincibleCounter > 40){ // invincible time
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30){
            shotAvailableCounter++;
        }

    }

    public void damagePlayer(int attack){
        if (gp.player.invincible == false){
            // damage given to player
            int damage = attack - gp.player.defense;
            if (damage < 0){
                damage = 0;
            }
            gp.player.life -= damage;

            gp.player.invincible = true;
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int windowX = worldX - gp.player.worldX + gp.player.windowX;
        int windowY = worldY - gp.player.worldY + gp.player.windowY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.windowX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.windowX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.windowY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.windowY){

            switch(direction){
                case "up":
                    if(spriteNum == 1) {image = up1;}
                    if(spriteNum == 2) {image = up2;}
                    break;
                case "down":
                    if(spriteNum == 1) {image = down1;}
                    if(spriteNum == 2) {image = down2;}
                    break;
                case "left":
                    if(spriteNum == 1) {image = left1;}
                    if(spriteNum == 2) {image = left2;}
                    break;
                case "right":
                    if(spriteNum == 1) {image = right1;}
                    if(spriteNum == 2) {image = right2;}
                    break;
            }

            // Monster health bar
            if (type == 2 && hpBarOn == true){
                double oneScale = (double)gp.tileSize/maxLife;
                double hpValue = oneScale*life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(windowX - 1, windowY - 16, gp.tileSize+2, 12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(windowX, windowY - 15, (int)hpValue, 10);

                hpBarCounter++;

                if (hpBarCounter > 600){
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible == true){
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4F);
            }

            if (dying == true){
                dyingAnimation(g2);
            }

            g2.drawImage(image, windowX, windowY, null);

            changeAlpha(g2, 1F);
        }
    }

    public void dyingAnimation(Graphics2D g2){

        dyingCounter++;
        int i = 5;

        if (dyingCounter <= i){
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i*2){
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i*2 && dyingCounter <= i*3){
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i*3 && dyingCounter <= i*4){
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i*4 && dyingCounter <= i*5){
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i*5 && dyingCounter <= i*6){
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i*6 && dyingCounter <= i*7){
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i*7 && dyingCounter <= i*8){
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i*8){
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue){

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    // Setting up images
    public BufferedImage setup(String imagePath, int imageWidth, int imageHeight){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, imageWidth, imageHeight);

        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pFinder.setNode(startCol, startRow, goalCol, goalRow);

        if (gp.pFinder.search()) {
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enTopY = worldY + solidArea.y;

            // Check the horizontal and vertical distances
            int xDistance = Math.abs(enLeftX - nextX);
            int yDistance = Math.abs(enTopY - nextY);

            // Prioritize one axis based on the distances
            if (xDistance > 0 && yDistance == 0) {
                // Horizontal movement only
                if (enLeftX > nextX) {
                    direction = "left";
                } else if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (yDistance > 0 && xDistance == 0) {
                // Vertical movement only
                if (enTopY > nextY) {
                    direction = "up";
                } else if (enTopY < nextY) {
                    direction = "down";
                }
            } else if (xDistance > 0) {
                // Prioritize horizontal movement if both are valid
                if (enLeftX > nextX) {
                    direction = "left";
                } else if (enLeftX < nextX) {
                    direction = "right";
                }
            } else if (yDistance > 0) {
                // Fallback to vertical movement
                if (enTopY > nextY) {
                    direction = "up";
                } else if (enTopY < nextY) {
                    direction = "down";
                }
            }

            // Handle collision
            checkCollision();
            if (collision) {
                direction = ""; // Stop movement if there's a collision
            }
        }

    }

//        if (gp.pFinder.search() == true){
//
//            // Next worldX & worldY
//            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
//            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
//
//            // Character's solidArea position
//            int enLeftX = worldX + solidArea.x;
//            int enRightX = worldX + solidArea.x + solidArea.width;
//            int enTopY = worldY + solidArea.y;
//            int enBottomY = worldY + solidArea.y + solidArea.height;
//
//            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
//                direction = "up";
//            }
//            else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
//                direction = "down";
//            }
//            else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize){
//                // left or right
//                if (enLeftX > nextX){
//                    direction = "left";
//                }
//                if (enLeftX < nextX){
//                    direction = "right";
//                }
//            }
//            else if (enTopY > nextY && enLeftX > nextX) {
//                // up or left
//                direction = "up";
//                checkCollision();
//                if (collision == true){
//                    direction = "left";
//                }
//            }
//            else if (enTopY > nextY && enLeftX < nextX){
//                // up or right
//                direction = "up";
//                checkCollision();
//                if (collision == true){
//                    direction = "right";
//                }
//            }
//            else if (enTopY < nextY && enLeftX > nextX){
//                // down or left
//                direction = "down";
//                checkCollision();
//                if (collision == true){
//                    direction = "left";
//                }
//            }
//            else if (enTopY < nextY && enLeftX < nextX){
//                // down or right
//                direction = "down";
//                checkCollision();
//                if (collision == true){
//                    direction = "right";
//                }
//            }
//
//            // if reaches the goal, stop the search
//            int nextCol = gp.pFinder.pathList.get(0).col;
//            int nextRow = gp.pFinder.pathList.get(0).row;
//            if (nextCol == goalCol && nextRow == goalRow){
//                onPath = false;
//            }
//        }


}