package Characters;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Arrows;
import object.OBJ_Fireball;
import object.OBJ_Normal_Shield;
import object.OBJ_Normal_Sword;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Character{

    KeyHandler keyH;

    public final int windowX;
    public final int windowY;
    int hasMeat = 0;
    int standCounter = 0;
    public boolean attackCanceled = false;

    // Player constructor
    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);
        this.keyH = keyH;

        windowX = gp.windowWidth/2 - (gp.tileSize/2);
        windowY = gp.windowHeight/2 - (gp.tileSize/2);

        // Player solid area for detecting collision
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 42;

        // Attack Area
        //attackArea.width = 36;
        //attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues(){
        // Player starting position
        worldX = gp.tileSize * 18;
        worldY = gp.tileSize * 15;

        speed = 4; // movement speed
        direction = "down"; // defaullt direction

        // Player status
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 1; // more strength = more damage to enemies
        dexterity = 1; // more dexterity = fewer damages received from enemy
        exp = 0;
        nextLevelExp = 5;
        coin = 500;
        currentWeapon = new OBJ_Normal_Sword(gp);
        currentShield = new OBJ_Normal_Shield(gp);
        projectile = new OBJ_Fireball(gp);
        attack = getAttack(); // Total attack value determined by strength and weapon
        defense = getDefense(); // Total defense value determined by dexterity and shield
    }

    public void setDefaultPositions(){
        worldX = gp.tileSize * 11;
        worldY = gp.tileSize * 91;
        direction = "down";
    }

    public void restoreLifeAndMana(){
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }

    public void setItems(){

        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);

    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage(){

        if (currentWeapon.type == typeSword){
            down1 = setup("/player/down1", gp.tileSize, gp.tileSize);
            down2 = setup("/player/down2", gp.tileSize, gp.tileSize);
            up1 = setup("/player/up1", gp.tileSize, gp.tileSize);
            up2 = setup("/player/up2", gp.tileSize, gp.tileSize);
            left1 = setup("/player/left1", gp.tileSize, gp.tileSize);
            left2 = setup("/player/left2", gp.tileSize, gp.tileSize);
            right1 = setup("/player/right1", gp.tileSize, gp.tileSize);
            right2 = setup("/player/right2", gp.tileSize, gp.tileSize);
        }

    }

    public void getPlayerAttackImage(){

        downattack1 = setup("/player/downattack1", gp.tileSize, gp.tileSize*2);
        downattack2 = setup("/player/downattack2", gp.tileSize, gp.tileSize*2);
        upattack1 = setup("/player/upattack1", gp.tileSize, gp.tileSize*2);
        upattack2 = setup("/player/upattack2", gp.tileSize, gp.tileSize*2);
        leftattack1 = setup("/player/leftattack1", gp.tileSize*2, gp.tileSize);
        leftattack2 = setup("/player/leftattack2", gp.tileSize*2, gp.tileSize);
        rightattack1 = setup("/player/rightattack1", gp.tileSize*2, gp.tileSize);
        rightattack2 = setup("/player/rightattack2", gp.tileSize*2, gp.tileSize);
    }

    public void update(){

        if (attacking == true){
            attacking();
        }
        else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true){
            if (keyH.upPressed == true) {
                direction = "up";
            }else if (keyH.downPressed == true) {
                direction = "down";
            }else if (keyH.leftPressed == true) {
                direction = "left";
            }else if (keyH.rightPressed == true) {
                direction = "right";
            }

            // Check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Check Object Collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickupObject(objIndex);

            // Check NPC collision
            int npcIndex = gp.cChecker.checkCharacter(this, gp.npc);
            interactNPC(npcIndex);

            // Check monster collision
            int monsterIndex = gp.cChecker.checkCharacter(this, gp.monster);
            contactMonster(monsterIndex);

            // Check event
            gp.eHandler.checkEvent();

            // If collision is false, player moves
            if (collisionOn == false && keyH.enterPressed == false){

                switch(direction){
                    case "up": worldY -= speed;
                        break;
                    case "down": worldY += speed;
                        break;
                    case "left": worldX -= speed;
                        break;
                    case "right": worldX += speed;
                        break;
                }
            }

            if (keyH.enterPressed == true && attackCanceled == false){
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 12){
                if (spriteNum == 1){
                    spriteNum = 2;
                }else if (spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
        else{
            standCounter++;

            if(spriteCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }
        }

        if (gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30 && projectile.haveResource(this) == true){

            // Set default coordinates, directions, and user
            projectile.set(worldX, worldY, direction, true, this);

            // Subtract the cost
            projectile.subtractResource(this);

            // Add it to the list
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }

        if (invincible == true){
            invincibleCounter++;
            if (invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }

        if (life > maxLife){
            life = maxLife;
        }

        if (mana > maxMana){
            mana = maxMana;
        }

        if (life <= 0){
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
        }
    }

    public void attacking(){

        spriteCounter++;

        if (spriteCounter <= 5){
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 30){
            spriteNum = 2;

            // Save current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjustings
            switch (direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            // Attack area become solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // Check monster collision with the new adjustments
            int monsterIndex = gp.cChecker.checkCharacter(this, gp.monster);
            damageMonster(monsterIndex, attack);

            // Restore original positions
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }
        if (spriteCounter > 30){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickupObject(int i){
        if (i != 999){
            // Pickup Items
            if (gp.obj[gp.currentMap][i].type == typePickup){
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }else{
                // Inventory Items
                String text;
                String objectName = gp.obj[gp.currentMap][i].name;

                if (inventory.size() != inventorySize){
                    inventory.add(gp.obj[gp.currentMap][i]);
                    text = "Picked up a " + objectName + "!";
                }else{
                    text = "Your inventory is full.";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }

        }
    }

    // NPC interaction
    public void interactNPC(int i){

        if (gp.keyH.enterPressed == true){

            if (i != 999){
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
            }

        }
    }

    public void contactMonster(int i){

        if (i != 999){
            if (invincible == false && gp.monster[gp.currentMap][i].dying == false){
                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if (damage <= 0){
                    damage = 1;
                }
                life -= damage;
                invincible = true;
            }
        }

    }

    public void damageMonster (int i, int attack){

        if (i != 999){

            if (gp.monster[gp.currentMap][i].invincible == false){

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if (damage <= 0){
                    damage = 1;
                }
                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage!");

                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if (gp.monster[gp.currentMap][i].life <= 0){
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("Killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[gp.currentMap][i].exp + "!");
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }

    public void checkLevelUp(){
        int levelUpLife = 2;
        int levelUpMana = 2;
        if (exp >= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp*2;
            maxLife += levelUpLife;
            maxMana += levelUpMana;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.gameState = gp.dialogueState;
            gp.ui.currentdialogue = "You have leveled up to " + level + " level!";
            exp = 0;
            life += levelUpLife;
            mana += levelUpMana;
        }

    }

    public void selectItem(){

        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if (itemIndex < inventory.size()){
            Character selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == typeSword){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if (selectedItem.type == typeShield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if (selectedItem.type == typeConsumable){
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }

    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int tempWindowX = windowX;
        int tempWindowY = windowY;

        switch(direction){
            case "up":
                if (attacking == false){
                    if(spriteNum == 1) {image = up1;}
                    if(spriteNum == 2) {image = up2;}
                }
                if (attacking == true) {
                    tempWindowY = windowY - gp.tileSize;
                    if(spriteNum == 1) {image = upattack1;}
                    if(spriteNum == 2) {image = upattack2;}
                }
                break;
            case "down":
                if (attacking == false) {
                    if(spriteNum == 1) {image = down1;}
                    if(spriteNum == 2) {image = down2;}
                }
                if (attacking == true){
                    if(spriteNum == 1) {image = downattack1;}
                    if(spriteNum == 2) {image = downattack2;}
                }
                break;
            case "left":
                if (attacking == false) {
                    if(spriteNum == 1) {image = left1;}
                    if(spriteNum == 2) {image = left2;}
                }
                if (attacking == true) {
                    tempWindowX = windowX - gp.tileSize;
                    if(spriteNum == 1) {image = leftattack1;}
                    if(spriteNum == 2) {image = leftattack2;}
                }
                break;
            case "right":
                if (attacking == false) {
                    if(spriteNum == 1) {image = right1;}
                    if(spriteNum == 2) {image = right2;}
                }
                if (attacking == true){
                    if(spriteNum == 1) {image = rightattack1;}
                    if(spriteNum == 2) {image = rightattack2;}
                }
                break;
        }

        if (invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempWindowX, tempWindowY, null);

        // reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}
