package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Optional;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
    // DEBUG
    boolean showDebugText = false;

    public KeyHandler (GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // Title state
        if (gp.gameState == gp.titleState){
            titleState(code);
        }

        // Play State
        else if (gp.gameState == gp.playState){
            playState(code);
        }

        // Pause State
        else if (gp.gameState == gp.pauseState){
            pauseState(code);
        }

        // Dialogue Sate
        else if (gp.gameState == gp.dialogueState){
            dialogueState(code);
        }

        // Character State
        else if (gp.gameState == gp.characterState){
            characterState(code);
        }

        // Options State
        else if (gp.gameState == gp.optionsState){
            optionsState(code);
        }

        // Game Over State
        else if (gp.gameState == gp.gameOverState){
            gameOverState(code);
        }

        // Trade State
        else if (gp.gameState == gp.tradeState){
            tradeState(code);
        }

    }

    public void titleState(int code){
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 2;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 2){
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
            if (gp.ui.commandNum == 1){
                // Add later
            }
            if (gp.ui.commandNum == 2){
                System.exit(0);
            }
        }
    }

    public void playState(int code){
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_QUOTE) {
            shotKeyPressed = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionsState;
        }

        //DEBUG
        if (code == KeyEvent.VK_T) {
            if (showDebugText == false){
                showDebugText = true;
            }else if (showDebugText == true){
                showDebugText = false;
            }
        }
        if (code == KeyEvent.VK_R) {
            switch(gp.currentMap){
                case 0: gp.tileM.loadMap("/maps/worldmap.txt", 0); break;
                case 1: gp.tileM.loadMap("/maps/houseinterior.txt", 1); break;
            }
        }
    }

    public void pauseState(int code){
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }

    public void optionsState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch(gp.ui.subState){
            case 0: maxCommandNum = 4; break;
            case 2: maxCommandNum = 1; break;
        }

        if (code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0){
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if (gp.ui.commandNum > maxCommandNum){
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_A){
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumeScale > 0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                }
                if (gp.ui.commandNum == 1 && gp.se.volumeScale > 0){
                    gp.se.volumeScale--;
                }
            }
        }
        if (code == KeyEvent.VK_D){
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumeScale < 5){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                }
                if (gp.ui.commandNum == 1 && gp.se.volumeScale < 5){
                    gp.se.volumeScale++;
                }
            }
        }
    }

    public void dialogueState(int code){
        if (code == KeyEvent.VK_ENTER){
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code){
        if (code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }
        playerInventory(code);
    }

    public void gameOverState(int code){
        if (code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0){
                gp.ui.commandNum = 1;
            }
        }
        if (code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1){
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER){
            if (gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.retry();
            }
            else if (gp.ui.commandNum == 1){
                gp.gameState = gp.titleState;
                gp.restart();
                gp.ui.commandNum = 0;
            }
        }
    }

    public void tradeState(int code){
        if (code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if (gp.ui.subState == 0){
            if (code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S){
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
            }
        }
        if (gp.ui.subState == 1){
            npcInventory(code);
            if (code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
        if (gp.ui.subState == 2){
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
    }

    public void playerInventory(int code){
        if (code == KeyEvent.VK_W){
            if (gp.ui.playerSlotRow != 0){
                gp.ui.playerSlotRow--;
            }
        }
        if (code == KeyEvent.VK_A){
            if (gp.ui.playerSlotCol != 0){
                gp.ui.playerSlotCol--;
            }
        }
        if (code == KeyEvent.VK_S){
            if (gp.ui.playerSlotRow != 3){
                gp.ui.playerSlotRow++;
            }
        }
        if (code == KeyEvent.VK_D){
            if (gp.ui.playerSlotCol != 4){
                gp.ui.playerSlotCol++;
            }
        }
    }

    public void npcInventory(int code){
        if (code == KeyEvent.VK_W){
            if (gp.ui.npcSlotRow != 0){
                gp.ui.npcSlotRow--;
            }
        }
        if (code == KeyEvent.VK_A){
            if (gp.ui.npcSlotCol != 0){
                gp.ui.npcSlotCol--;
            }
        }
        if (code == KeyEvent.VK_S){
            if (gp.ui.npcSlotRow != 3){
                gp.ui.npcSlotRow++;
            }
        }
        if (code == KeyEvent.VK_D){
            if (gp.ui.npcSlotCol != 4){
                gp.ui.npcSlotCol++;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_QUOTE) {
            shotKeyPressed = false;
        }
        if (code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }

    }

}
