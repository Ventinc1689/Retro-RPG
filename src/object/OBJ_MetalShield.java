package object;

import Characters.Character;
import main.GamePanel;

public class OBJ_MetalShield extends Character {

    public OBJ_MetalShield(GamePanel gp) {
        super(gp);

        name = "Metal Shield";
        type = typeShield;
        down1 = setup("/object_images/metal_shield", gp.tileSize, gp.tileSize);
        defenseValue = 2;
        description = name + "\nDefense: " + defenseValue;
        price = 25;
    }
}
