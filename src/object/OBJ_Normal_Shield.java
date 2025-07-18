package object;

import Characters.Character;
import main.GamePanel;

public class OBJ_Normal_Shield extends Character {

    public OBJ_Normal_Shield(GamePanel gp) {
        super(gp);

        name = "Normal Shield";
        type = typeShield;
        down1 = setup("/object_images/shield", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = name + "\nDefense: " + defenseValue;
        price = 10;
    }
}
