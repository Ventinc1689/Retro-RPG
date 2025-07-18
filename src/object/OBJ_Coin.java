package object;

import main.GamePanel;
import Characters.Character;

public class OBJ_Coin extends Character{

    GamePanel gp;

    public OBJ_Coin(GamePanel gp){
        super(gp);
        this.gp = gp;

        type = typePickup;
        name = "Coin";
        value = 1;
        down1 = setup("/object_images/coin", gp.tileSize, gp.tileSize);

    }

    public void use(Character character){
        gp.ui.addMessage("Coin + " + value);
        gp.player.coin += value;
    }

}
