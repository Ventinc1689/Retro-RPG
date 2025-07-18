package main;

import Characters.NPC_Grandfather;
import Characters.NPC_Merchant;
import monster.MON_BoneSkeleton;
import monster.MON_BowSkeleton;
import monster.MON_Skeleton;
import object.OBJ_Coin;
import object.OBJ_Meat;
import object.OBJ_MetalShield;
import object.OBJ_Nightfall;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        int mapNum = 0;
        int o = 0;

//        gp.obj[mapNum][o] = new OBJ_Meat(gp);
//        gp.obj[mapNum][o].worldX = gp.tileSize*10;
//        gp.obj[mapNum][o].worldY = gp.tileSize*92;
//        o++;

    }

    public void setNPC(){
        int mapNum = 0;
        int n = 0;

        gp.npc[mapNum][n] = new NPC_Grandfather(gp);
        gp.npc[mapNum][n].worldX = gp.tileSize*17;
        gp.npc[mapNum][n].worldY = gp.tileSize*88;
        n++;

        gp.npc[mapNum][n] = new NPC_Merchant(gp);
        gp.npc[mapNum][n].worldX = gp.tileSize*11;
        gp.npc[mapNum][n].worldY = gp.tileSize*92;
        n++;
    }

    public void setMonster(){
        int mapNum = 0;
        int i = 0;

        gp.monster[mapNum][i] = new MON_Skeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 12;
        gp.monster[mapNum][i].worldY = gp.tileSize * 72;
        i++;

        gp.monster[mapNum][i] = new MON_Skeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 12;
        gp.monster[mapNum][i].worldY = gp.tileSize * 76;
        i++;

        gp.monster[mapNum][i] = new MON_Skeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 19;
        gp.monster[mapNum][i].worldY = gp.tileSize * 72;
        i++;

        gp.monster[mapNum][i] = new MON_Skeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 19;
        gp.monster[mapNum][i].worldY = gp.tileSize * 76;
        i++;

        gp.monster[mapNum][i] = new MON_BowSkeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 24;
        gp.monster[mapNum][i].worldY = gp.tileSize * 67;
        i++;

        gp.monster[mapNum][i] = new MON_BowSkeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 12;
        gp.monster[mapNum][i].worldY = gp.tileSize * 45;
        i++;

        gp.monster[mapNum][i] = new MON_Skeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 12;
        gp.monster[mapNum][i].worldY = gp.tileSize * 59;
        i++;

        gp.monster[mapNum][i] = new MON_BoneSkeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 10;
        gp.monster[mapNum][i].worldY = gp.tileSize * 47;
        i++;

        gp.monster[mapNum][i] = new MON_BowSkeleton(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 10;
        gp.monster[mapNum][i].worldY = gp.tileSize * 57;
        i++;

    }

}
