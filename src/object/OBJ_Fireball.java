package object;

import Characters.Character;
import Characters.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile {

    GamePanel gp;

    public OBJ_Fireball(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = "Fireball";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        down1 = setup("/Projectile_Images/fireball_down1", gp.tileSize, gp.tileSize);
        down2 = setup("/Projectile_Images/fireball_down2", gp.tileSize, gp.tileSize);
        up1 = setup("/Projectile_Images/fireball_up1", gp.tileSize, gp.tileSize);
        up2 = setup("/Projectile_Images/fireball_up2", gp.tileSize, gp.tileSize);
        left1 = setup("/Projectile_Images/fireball_left1", gp.tileSize, gp.tileSize);
        left2 = setup("/Projectile_Images/fireball_left2", gp.tileSize, gp.tileSize);
        right1 = setup("/Projectile_Images/fireball_right1", gp.tileSize, gp.tileSize);
        right2 = setup("/Projectile_Images/fireball_right2", gp.tileSize, gp.tileSize);
    }

    public boolean haveResource(Character user){
        boolean haveReource = false;
        if (user.mana >= useCost){
            haveReource = true;
        }
        return haveReource;
    }

    public void subtractResource(Character user){
        user.mana -= useCost;
    }

}
