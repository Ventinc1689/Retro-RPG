package Characters;

import main.GamePanel;

public class Projectile extends Character{

    Character user;

    public Projectile (GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Character user){
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }

    public void update(){

        if (user == gp.player) {
            int monsterIndex = gp.cChecker.checkCharacter(this, gp.monster);
            if(monsterIndex != 999){
                gp.player.damageMonster(monsterIndex, attack);
                alive = false;
            }
        }
        if (user != gp.player){
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if (gp.player.invincible == false && contactPlayer == true){
                damagePlayer(attack);
                alive = false;
            }
        }

        switch (direction){
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }

        life--;
        if (life <= 0){
            alive = false;
        }

        spriteCounter++;
        if (spriteCounter > 12){
            if (spriteNum == 1){
                spriteNum = 2;
            }
            else if (spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public boolean haveResource(Character user){
        boolean haveReource = false;
        return haveReource;
    }

    public void subtractResource(Character user){}

}

