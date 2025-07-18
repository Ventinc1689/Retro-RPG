package main;

import Characters.Character;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    // CHECKS TILE COLLISIONS
    public void checkTile(Character character){

        int characterLeftWorldX = character.worldX + character.solidArea.x;
        int characterRightWorldX = character.worldX + character.solidArea.x + character.solidArea.width;
        int characterTopWorldY = character.worldY + character.solidArea.y;
        int characterBottomWorldY = character.worldY + character.solidArea.y + character.solidArea.height;

        int characterLeftCol = characterLeftWorldX/gp.tileSize;
        int characterRightCol = characterRightWorldX/gp.tileSize;
        int characterTopRow = characterTopWorldY/gp.tileSize;
        int characterBottomRow = characterBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch(character.direction){

            case "up":
                characterTopRow = (characterTopWorldY - character.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    character.collisionOn = true;
                }
                break;
            case "down":
                characterBottomRow = (characterBottomWorldY + character.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    character.collisionOn = true;
                }
                break;
            case "left":
                characterLeftCol = (characterLeftWorldX - character.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterLeftCol][characterBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    character.collisionOn = true;
                }
                break;
            case "right":
                characterRightCol = (characterRightWorldX + character.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][characterRightCol][characterBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    character.collisionOn = true;
                }
                break;
        }
    }

    // CHECK FOR NPC & MONSTER COLLISION
    public int checkCharacter(Character character, Character[][] target){

        int index = 999;

        for (int i = 0; i < target[1].length; i++){

            if (target[gp.currentMap][i] != null){

                // Get character's solid area position
                character.solidArea.x = character.worldX + character.solidArea.x;
                character.solidArea.y = character.worldY + character.solidArea.y;

                // Get npc's solid area position
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

                switch(character.direction){
                case "up": character.solidArea.y -= character.speed; break;
                case "down": character.solidArea.y += character.speed; break;
                case "left": character.solidArea.x -= character.speed; break;
                case "right": character.solidArea.x += character.speed; break;
                }

                if(character.solidArea.intersects(target[gp.currentMap][i].solidArea)){
                    if (target[gp.currentMap][i] != character){
                        character.collisionOn = true;
                        index = i;
                    }
                }

                character.solidArea.x = character.solidAreaDefaultX;
                character.solidArea.y = character.solidAreaDefaultY;
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(Character character){

        boolean contactPlayer = false;

        character.solidArea.x = character.worldX + character.solidArea.x;
        character.solidArea.y = character.worldY + character.solidArea.y;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch(character.direction){
            case "up": character.solidArea.y -= character.speed;
            break;
            case "down": character.solidArea.y += character.speed;
            break;
            case "left": character.solidArea.x -= character.speed;
            break;
            case "right": character.solidArea.x += character.speed;
            break;
        }

        if(character.solidArea.intersects(gp.player.solidArea)){
            character.collisionOn = true;
            contactPlayer = true;
        }

        character.solidArea.x = character.solidAreaDefaultX;
        character.solidArea.y = character.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;

        return contactPlayer;
    }

    public int checkObject(Character character, boolean player){
        int index = 999;

        for (int i = 0; i < gp.obj[1].length; i++){

            if (gp.obj[gp.currentMap][i] != null){

                // get character's solid area position
                character.solidArea.x = character.worldX + character.solidArea.x;
                character.solidArea.y = character.worldY + character.solidArea.y;

                // get object's solid area position
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y;

                switch(character.direction){
                    case "up": character.solidArea.y -= character.speed; break;
                    case "down": character.solidArea.y += character.speed; break;
                    case "left": character.solidArea.x -= character.speed; break;
                    case "right": character.solidArea.x += character.speed; break;
                }

                if (character.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)){
                    if (gp.obj[gp.currentMap][i].collision == true){
                        character.collisionOn = true;
                    }
                    if (player == true){
                        index = i;
                    }
                }

                character.solidArea.x = character.solidAreaDefaultX;
                character.solidArea.y = character.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x = gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y = gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

}