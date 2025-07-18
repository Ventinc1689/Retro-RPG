package object;

import Characters.Character;
import main.GamePanel;

public class OBJ_Meat extends Character {

    GamePanel gp;

    public OBJ_Meat(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Meat";
        type = typeConsumable;
        down1 = setup("/object_images/meat", gp.tileSize, gp.tileSize);
        healValue = 1;
        description = name + "\nHeals: " + healValue + " hp";
        price = 5;
    }

    public void use(Character character){
        gp.gameState = gp.dialogueState;
        gp.ui.currentdialogue = "You are healed by 1 hp from the meat.";
        character.life += healValue;
        if (gp.player.life > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }
    }
}
