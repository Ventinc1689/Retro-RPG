package monster;

import Characters.Character;
import main.GamePanel;
import object.OBJ_Bloodthirst;
import object.OBJ_Coin;
import object.OBJ_Meat;
import object.OBJ_Nightfall;

import java.util.Random;

public class MON_BoneSkeleton extends Character{

    GamePanel gp;

    // Skeleton Constructor
    public MON_BoneSkeleton(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typeMonster;
        name = "Bone Skeleton";
        speed = 1;
        maxLife = 20;
        life = maxLife;
        attack = 5;
        defense = 1;
        exp = 5;

        getImage();
    }

    // Get Skeleton Images
    public void getImage(){

        down1 = setup("/Monster_Images/boneskeletondown1", gp.tileSize, gp.tileSize);
        down2 = setup("/Monster_Images/boneskeletondown2", gp.tileSize, gp.tileSize);
        up1 = setup("/Monster_Images/boneskeletonup1", gp.tileSize, gp.tileSize);
        up2 = setup("/Monster_Images/boneskeletonup2", gp.tileSize, gp.tileSize);
        left1 = setup("/Monster_Images/boneskeletonleft1", gp.tileSize, gp.tileSize);
        left2 = setup("/Monster_Images/boneskeletonleft2", gp.tileSize, gp.tileSize);
        right1 = setup("/Monster_Images/boneskeletonright1", gp.tileSize, gp.tileSize);
        right2 = setup("/Monster_Images/boneskeletonright2", gp.tileSize, gp.tileSize);
    }


    public void update(){
        super.update();

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance)/gp.tileSize;

        if (onPath == true && tileDistance > 20){
            onPath = false;
        }
    }

    public void setAction(){

        if (onPath) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow);
        }
        else {
            actionLookCounter++;

            // Random Movement
            if (actionLookCounter == 120){
                Random random = new Random();
                int i = random.nextInt(100)+1; // 1 to 100

                if (i <= 25){
                    direction = "up";
                }
                if (i > 25 && i <= 50){
                    direction = "down";
                }
                if (i > 50 && i <= 75){
                    direction = "left";
                }
                if (i >75 && i <= 100){
                    direction = "right";
                }
                actionLookCounter = 0;
            }

        }

    }

    public void damageReaction() {
        actionLookCounter = 0;
        onPath = true;
    }

    public void checkDrop(){
        int i = new Random().nextInt(100)+1;

        // Set the monster drop items
        if (i < 60){
            dropItem(new OBJ_Bloodthirst(gp));
        }
        if (i >= 60 && i < 85){
            dropItem(new OBJ_Bloodthirst(gp));
        }
        if (i >= 85 && i < 100){
            dropItem(new OBJ_Bloodthirst(gp));
        }
    }

}

