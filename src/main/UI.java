package main;

import Characters.Character;
import object.OBJ_Coin;
import object.OBJ_Heart;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    BufferedImage fullHeart, halfHeart, blankHeart, coins;
    public boolean messageOn = false;
    //public String message = "";
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentdialogue = "";
    public int commandNum = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    int counter = 0;
    public Character npc;

    // UI Constructor
    public UI(GamePanel gp){
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        // CREATE HEART OBJECT
        Character heart = new OBJ_Heart(gp);
        fullHeart = heart.image;
        halfHeart = heart.image2;
        blankHeart = heart.image3;
        Character coin = new OBJ_Coin(gp);
        coins = coin.down1;
    }

    public void addMessage(String text){
        message.add(text);
        messageCounter.add(0);
    }

    public void draw (Graphics2D g2){

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        // Title state
        if (gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        // Play state
        if (gp.gameState == gp.playState){
            drawPlayerLife();
            drawPlayerMana();
            drawMessage();
        }

        // Pause state
        if (gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPlayerMana();
            drawPauseScreen();
        }

        // Dialog state
        if (gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawPlayerMana();
            drawDialogScreen();
        }

        // Character state
        if (gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }

        // Option State
        if (gp.gameState == gp.optionsState){
            drawOptionsScreen();
        }

        // Game Over State
        if (gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }

        // Transition State
        if (gp.gameState == gp.transitionState){
            drawTransition();
        }

        // Trade state
        if (gp.gameState == gp.tradeState){
            drawTradeScreen();
        }
    }

    public void drawPlayerLife(){

        // HEALTH BAR POSITION
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        // Player health bar
        double playerScale = (double)gp.tileSize/gp.player.maxLife;
        double playerHpValue = playerScale*gp.player.life;

        g2.setColor(new Color(128,128,128));
        g2.fillRect(x - 5, y - 10, gp.tileSize*7+10, 42);

        g2.setColor(new Color(35,35,35));
        g2.fillRect(x - 1, y - 6, gp.tileSize*7+2, 34);

        g2.setColor(new Color(255,0,30));
        g2.fillRect(x, y - 5, (int)playerHpValue*7, 32);

        gp.player.hpBarCounter++;

        if (gp.player.hpBarCounter > 600){
            gp.player.hpBarCounter = 0;
            gp.player.hpBarOn = false;
        }

        // DRAW BLANK HEARTS
//        while (i < gp.player.maxLife/2){
//            g2.drawImage(blankHeart, x, y, null);
//            i++;
//            x += gp.tileSize;
//        }

        // RESET
//        x = gp.tileSize/2;
//        y = gp.tileSize/2;
//        i = 0;

        // DRAW CURRENT LIFE
//        while (i < gp.player.life){
//            // Draw half hearts first
//            g2.drawImage(halfHeart, x, y, null);
//            i++;
//            // Changes to full heart if condition met
//            if (i < gp.player.life){
//                g2.drawImage(fullHeart, x, y, null);
//            }
//            // Moves to next heart
//            i++;
//            x += gp.tileSize;
//        }
    }

    public void drawPlayerMana(){

        // MANA BAR POSITION
        int manaX = gp.tileSize/2;
        int manaY = gp.tileSize+10;
        int i = 0;

        // Player mana bar
        double playerScale = (double)gp.tileSize/gp.player.maxMana;
        double playerManaValue = playerScale*gp.player.mana;

        g2.setColor(new Color(128,128,128));
        g2.fillRect(manaX - 5, manaY - 5, gp.tileSize*6+10, 32);

        g2.setColor(new Color(35,35,35));
        g2.fillRect(manaX - 1, manaY - 1, gp.tileSize*6+2, 24);

        g2.setColor(new Color(0,75,255));
        g2.fillRect(manaX, manaY, (int)playerManaValue*6, 22);
    }

    public void drawMessage(){

        int messageX = gp.tileSize*11;
        int messageY = gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));

        for (int i = 0; i < message.size(); i++){

            if (message.get(i) != null){
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+2, messageY+2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; // messageCounter++
                messageCounter.set(i, counter); // set the counter to the array
                messageY += 50;

                if (messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }

    }

    public void drawTitleScreen(){

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Redemption";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;

        // SHADOW COLOR
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);

        // TITLE COLOR
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // MC IMAGE
        x = gp.windowWidth/2 - (gp.tileSize*2)/2;
        y =+ gp.tileSize*5;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if (commandNum == 0){
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1){
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2){
            g2.drawString(">",x-gp.tileSize,y);
        }
    }

    public void drawPauseScreen(){

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.windowHeight/2;

        g2.drawString(text, x, y);
    }

    public void drawDialogScreen(){

        // Dialog Window
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.windowWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;
        drawSubWindow(x, y, width, height);

        // set dialogue position
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 27F));
        x += gp.tileSize;
        y += gp.tileSize;

        // New line in dialogue
        for (String line : currentdialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }

    }

    public void drawCharacterScreen(){

        // Create a frame
        final int frameX = gp.tileSize*14;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(20F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;

        // Names
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight+10;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight+15;
        g2.drawString("Shield", textX, textY);

        // Values
        int tailX = (frameX + frameWidth) - 30;

        // Reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight+10;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - 35, textY - 30, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - 35, textY - 30, null);

    }

    public void drawInventory(Character character, boolean cursor){

        int frameX;
        int frameY;
        int frameWidth;
        int frameHeight;
        int slotCol;
        int slotRow;

        if (character == gp.player){
            frameX = gp.tileSize;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else {
            frameX = gp.tileSize*13;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        // Frame
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;

        // Draw player items
        for (int i = 0; i < character.inventory.size(); i++){

            // Equip cursor
            if (character.inventory.get(i) == character.currentWeapon || character.inventory.get(i) == character.currentShield){
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 5, 5);
            }

            g2.drawImage(character.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14){
                slotX = slotXstart;
                slotY += gp.tileSize;
            }
        }

        // Cursor
        if (cursor == true){
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            // Draw Cursor
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // Description frame
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize*3;

            // Draw description text
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(20F));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if (itemIndex < character.inventory.size()){
                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                for (String line: character.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 28;
                }
            }
        }

    }

    public void drawGameOverScreen(){
        g2.setColor(new Color(0,0,0, 150));
        g2.fillRect(0, 0, gp.windowWidth, gp.windowHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "You Died";
        // Shadow
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize*5;
        g2.drawString(text, x, y);

        // Main
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        // Respawn
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Respawn";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0){
            g2.drawString(">", x-40, y);
        }

        // Back to Title screen
        text = "Quit";
        x = getXforCenteredText(text);
        y += gp.tileSize + 20;
        g2.drawString(text, x, y);
        if (commandNum == 1){
            g2.drawString(">", x-40, y);
        }
    }

    public void drawOptionsScreen(){

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        // Sub Window
        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState){
            case 0: optionsTop(frameX, frameY); break;
            case 1: options_control(frameX, frameY); break;
            case 2: options_endGame(frameX, frameY); break;
        }

    }

    public void optionsTop(int frameX, int frameY){
        int textX;
        int textY;
        int lineSpace = gp.tileSize+10;

        // Title
        String text = "Settings";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // Music
        textX = frameX + gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString("Music", textX, textY);
        if (commandNum == 0){
            g2.drawString(">", textX - 25, textY);
        }

        // SE
        textY += lineSpace;
        g2.drawString("SE", textX, textY);
        if (commandNum == 1){
            g2.drawString(">", textX - 25, textY);
        }

        // Control
        textY += lineSpace;
        g2.drawString("Control", textX, textY);
        if (commandNum == 2){
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true){
                subState = 1;
                commandNum = 0;
                gp.keyH.enterPressed = false;
            }
        }

        // End Game
        textY += lineSpace;
        g2.drawString("End Game", textX, textY);
        if (commandNum == 3){
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true){
                subState = 2;
                commandNum = 0;
                gp.keyH.enterPressed = false;
            }
        }

        // Back
        textY += lineSpace*2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 4){
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed == true){
                gp.gameState = gp.playState;
                commandNum = 0;
                gp.keyH.enterPressed = false;
            }
        }

        // Music volume
        textX = frameX + (int)(gp.tileSize*4.5);
        textY = frameY + gp.tileSize*2 + 24;
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // Sound Effect
        textY += lineSpace;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }

    public void options_control(int frameX, int frameY){
        int textX;
        int textY;

        // Title
        String text = "Controls";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));

        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Shoot/Case", textX, textY); textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY); textY += gp.tileSize;

        textX = frameX + gp.tileSize*6;
        textY = frameY + gp.tileSize*2;

        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
        g2.drawString("'", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY); textY += gp.tileSize;

        // Back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if (gp.keyH.enterPressed == true){
                subState = 0;
                commandNum = 2;
                gp.keyH.enterPressed = false;
            }
        }

    }

    public void options_endGame(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
        currentdialogue = "Quit the game and \nreturn to the title screen?";

        for (String line: currentdialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Yes
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if (commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if (gp.keyH.enterPressed == true){
                subState = 0;
                gp.gameState = gp.titleState;
                gp.stopMusic();
                gp.keyH.enterPressed = false;
            }
        }

        // No
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 1){
            g2.drawString(">", textX-25, textY);
            if (gp.keyH.enterPressed == true){
                subState = 0;
                commandNum = 3;
                gp.keyH.enterPressed = false;
            }
        }
    }

    public void drawTransition(){
        counter++;
        g2.setColor(new Color(0,0,0, counter*5));
        g2.fillRect(0,0, gp.windowWidth, gp.windowHeight);

        if (counter == 50){
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
        }
    }

    public void drawTradeScreen(){
        switch(subState){
            case 0: tradeSelect(); break;
            case 1: tradeBuy(); break;
            case 2: tradeSell(); break;
        }
        gp.keyH.enterPressed = false;
    }

    public void tradeSelect(){
        drawDialogScreen();

        // Draw window
        int x = gp.tileSize*15;
        int y = gp.tileSize*4;
        int width = gp.tileSize*3;
        int height = (int)(gp.tileSize*3.5);
        drawSubWindow(x,y,width,height);

        // Draw texts
        x += gp.tileSize;
        y += gp.tileSize;

        g2.drawString("Buy", x, y);
        if (commandNum == 0){
            g2.drawString(">", x-24, y);
            if (gp.keyH.enterPressed == true){
                subState = 1;
            }
        }
        y+= gp.tileSize;

        g2.drawString("Sell", x, y);
        if (commandNum == 1){
            g2.drawString(">", x-24, y);
            if (gp.keyH.enterPressed == true){
                subState = 2;
            }
        }
        y+= gp.tileSize;

        g2.drawString("Leave", x, y);
        if (commandNum == 2){
            g2.drawString(">", x-24, y);
            if (gp.keyH.enterPressed == true){
                commandNum = 0;
                gp.gameState = gp.dialogueState;
                currentdialogue = "Come back again!";
            }
        }
    }

    public void tradeBuy(){

        // Draw Player Inventory
        drawInventory(gp.player, false);

        // Draw npc inventory
        drawInventory(npc, true);

        // Draw hint window
        int x = gp.tileSize;
        int y = gp.tileSize*9;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coins: " + gp.player.coin, x+24, y+60);

        // Draw player coin window
        x = gp.tileSize*13;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x+24, y+60);

        // Draw price window
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if (itemIndex < npc.inventory.size()){
            x = (int)(gp.tileSize*12.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coins, x+10, y+8, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize*14);
            g2.drawString(text, x+25, y+32);

            // Buy an item
            if (gp.keyH.enterPressed == true){
                if (npc.inventory.get(itemIndex).price > gp.player.coin){
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentdialogue = "You do not have enough coins!";
                    drawDialogScreen();
                }
                else if (gp.player.inventory.size() == gp.player.inventorySize){
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentdialogue = "You do not have enough space in your inventory!";
                }
                else {
                    gp.player.coin -= npc.inventory.get(itemIndex).price;
                    gp.player.inventory.add(npc.inventory.get(itemIndex));
                }
            }
        }
    }

    public void tradeSell(){

        // Draw player inventory
        drawInventory(gp.player, true);

        int x;
        int y;
        int width;
        int height;

        // Draw hint window
        x = gp.tileSize;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coins: " + gp.player.coin, x+24, y+60);

        // Draw player coin window
        x = gp.tileSize*13;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x+24, y+60);

        // Draw price window
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if (itemIndex < gp.player.inventory.size()){
            x = (int)(gp.tileSize*5.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coins, x+10, y+8, 32, 32, null);

            int price = gp.player.inventory.get(itemIndex).price/2;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize*7);
            g2.drawString(text, x+25, y+32);

            // Sell an item
            if (gp.keyH.enterPressed == true){
                if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
                    gp.player.inventory.get(itemIndex) == gp.player.currentShield){
                    commandNum = 0;
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentdialogue = "You can't sell an equipped item!";
                }
                else {
                    gp.player.inventory.remove(itemIndex);
                    gp.player.coin += price;
                }
            }
        }

    }

    public int getItemIndexOnSlot(int slotCol, int slotRow){
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }

    // Method for creating sub windows
    public void drawSubWindow(int x, int y, int width, int height){

        // Dialogue box background color
        Color c = new Color(0,0,0, 200);
        g2.setColor(c);
        int arcWidth = 35;
        int arcHeight = 35;
        g2.fillRoundRect(x, y, width, height, arcWidth, arcHeight);

        // Dialogue box border color
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, arcWidth-10, arcHeight-10);

    }

    // Centers the text
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.windowWidth/2 - length/2;
        return x;
    }

    // Aligns the text
    public int getXforAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }

}
