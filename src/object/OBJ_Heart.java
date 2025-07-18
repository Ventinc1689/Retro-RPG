package object;

import main.GamePanel;
import Characters.Character;

public class OBJ_Heart extends Character{

    public OBJ_Heart(GamePanel gp){

        super(gp);

        name = "Heart";
        image = setup("/object_images/heart", gp.tileSize, gp.tileSize);
        image2 = setup("/object_images/halfheart", gp.tileSize, gp.tileSize);
        image3 = setup("/object_images/blankheart", gp.tileSize, gp.tileSize);
    }

}
