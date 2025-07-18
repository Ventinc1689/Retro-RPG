package object;

import Characters.Character;
import main.GamePanel;

public class OBJ_Bloodthirst extends Character{

    public OBJ_Bloodthirst(GamePanel gp) {
        super(gp);

        name = "Bloodthirst";
        type = typeSword;
        down1 = setup("/object_images/bloodthirst",gp.tileSize, gp.tileSize);
        attackValue = 3;
        attackArea.width = 40;
        attackArea.height = 40;
        description = name + "\nAttack: " + attackValue;
        price = 100;
    }
}
