package object;

import Characters.Character;
import main.GamePanel;

public class OBJ_Normal_Sword extends Character{

    public OBJ_Normal_Sword(GamePanel gp) {
        super(gp);

        name = "Normal Sword";
        type = typeSword;
        down1 = setup("/object_images/sword",gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = name + "\nAttack: " + attackValue;
        price = 10;
    }
}
