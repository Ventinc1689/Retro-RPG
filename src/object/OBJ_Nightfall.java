package object;

import Characters.Character;
import main.GamePanel;

public class OBJ_Nightfall extends Character{

    public OBJ_Nightfall(GamePanel gp) {
        super(gp);

        name = "Nightfall";
        type = typeSword;
        down1 = setup("/object_images/Nightfall",gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 38;
        attackArea.height = 38;
        description = name + "\nAttack: " + attackValue;
        price = 25;
    }
}
