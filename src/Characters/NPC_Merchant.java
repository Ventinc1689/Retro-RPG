package Characters;
import main.GamePanel;
import object.OBJ_Meat;
import object.OBJ_MetalShield;
import object.OBJ_Nightfall;

public class NPC_Merchant extends Character {

    public NPC_Merchant(GamePanel gp) {

        super(gp);
        direction = "down";
        speed = 0;

        getImage();
        setDialogue();
        setItems();
    }

    public void getImage() {

        down1 = setup("/NPC/merchant", gp.tileSize, gp.tileSize);
        down2 = setup("/NPC/merchant", gp.tileSize, gp.tileSize);
        up1 = setup("/NPC/merchant", gp.tileSize, gp.tileSize);
        up2 = setup("/NPC/merchant", gp.tileSize, gp.tileSize);
        left1 = setup("/NPC/merchant", gp.tileSize, gp.tileSize);
        left2 = setup("/NPC/merchant", gp.tileSize, gp.tileSize);
        right1 = setup("/NPC/merchant", gp.tileSize, gp.tileSize);
        right2 = setup("/NPC/merchant", gp.tileSize, gp.tileSize);

    }

    // Dialogues
    public void setDialogue() {
        dialogues[0] = "Merchant: Hello there, I have some good stuff.\nDo your want to trade?";
    }

    public void setItems(){
        inventory.add(new OBJ_Meat(gp));
        inventory.add(new OBJ_MetalShield(gp));
        inventory.add(new OBJ_Nightfall(gp));
    }

    public void speak(){
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
