package Characters;
import main.GamePanel;

public class NPC_Grandfather extends Character{

    public NPC_Grandfather(GamePanel gp){

        super(gp);
        direction = "left";
        speed = 0;

        getImage();
        setDialogue();
    }

    public void getImage(){

        down1 = setup("/NPC/oldmandown1", gp.tileSize, gp.tileSize);
        down2 = setup("/NPC/oldmandown1", gp.tileSize, gp.tileSize);
        up1 = setup("/NPC/oldmanup1", gp.tileSize, gp.tileSize);
        up2 = setup("/NPC/oldmanup1", gp.tileSize, gp.tileSize);
        left1 = setup("/NPC/oldmanleft1", gp.tileSize, gp.tileSize);
        left2 = setup("/NPC/oldmanleft1", gp.tileSize, gp.tileSize);
        right1 = setup("/NPC/oldmanright1", gp.tileSize, gp.tileSize);
        right2 = setup("/NPC/oldmanright1", gp.tileSize, gp.tileSize);

    }

    // Dialogues
    public void setDialogue(){

        dialogues[0] = "Grandfather: Hey my grandchild!";
        dialogues[1] = "I see you are about to set on your\nadventure to defeat the Demon King!";
        dialogues[2] = "I am now too old, and your father lost\nhis live during his battle against the\nDemon King.";
        dialogues[3] = "So now I am placing the duty upon you!";
        dialogues[4] = "But most importantly,\nstay safe and come back alive!";

    }

    public void speak(){
        super.speak();
    }

}