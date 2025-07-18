package object;

import Characters.Character;
import Characters.Projectile;
import main.GamePanel;

public class OBJ_Arrows extends Projectile {

    GamePanel gp;

    public OBJ_Arrows(GamePanel gp){
        super(gp);
        this.gp = gp;

        name = "Arrow";
        speed = 10;
        maxLife = 80;
        life = maxLife;
        attack = 3;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        down1 = setup("/Projectile_Images/downarrow", gp.tileSize, gp.tileSize);
        down2 = setup("/Projectile_Images/downarrow", gp.tileSize, gp.tileSize);
        up1 = setup("/Projectile_Images/uparrow", gp.tileSize, gp.tileSize);
        up2 = setup("/Projectile_Images/uparrow", gp.tileSize, gp.tileSize);
        left1 = setup("/Projectile_Images/leftarrow", gp.tileSize, gp.tileSize);
        left2 = setup("/Projectile_Images/leftarrow", gp.tileSize, gp.tileSize);
        right1 = setup("/Projectile_Images/rightarrow", gp.tileSize, gp.tileSize);
        right2 = setup("/Projectile_Images/rightarrow", gp.tileSize, gp.tileSize);
    }

    public boolean haveResource(Character user){
        boolean haveReource = false;
        if (user.ammo >= useCost){
            haveReource = true;
        }
        return haveReource;
    }

    public void subtractResource(Character user){
        user.ammo -= useCost;
    }

}
