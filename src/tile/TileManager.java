package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;
    boolean drawPath = true;

    public TileManager (GamePanel gp){

        this.gp = gp;
        tile = new Tile[50];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];


        getTileImage();
        loadMap("/maps/worldmap.txt", 0);
        loadMap("/maps/houseinterior.txt", 1);

    }

    public void getTileImage(){

        // Not for used
        setup(0,"grass", false);
        setup(1,"grass", false);
        setup(2,"grass", false);
        setup(3,"grass", false);
        setup(4,"grass", false);
        setup(5,"grass", false);
        setup(6,"grass", false);
        setup(7,"grass", false);
        setup(8,"grass", false);
        setup(9,"grass", false);

        // Usable Tiles
        setup(10,"grass", false);
        setup(11,"water", true);
        setup(12,"water1", true);
        setup(13,"topleftwater", true);
        setup(14,"topwater", true);
        setup(15,"toprightwater", true);
        setup(16,"rightwater", true);
        setup(17,"bottomrightwater", true);
        setup(18,"bottomwater", true);
        setup(19,"bottomleftwater", true);
        setup(20,"leftwater", true);
        setup(21,"bottomleftcornerwater", true);
        setup(22,"bottomrightcornerwater", true);
        setup(23,"road", false);
        setup(24,"tree", true);
        setup(25,"door", false);
        setup(26,"house1", true);
        setup(27,"house2", true);
        setup(28,"house3", true);
        setup(29,"house4", true);
        setup(30,"house5", true);
        setup(31,"house6", true);
        setup(32,"house7", true);
        setup(33,"house8", true);
        setup(34,"house9", true);
        setup(35,"house10", true);
        setup(36,"house11", true);
        setup(37,"house12", true);
        setup(38,"house13", true);
        setup(39,"table", true);
        setup(40,"floor", false);
        setup(41, "blacktile", true);
        setup(42, "wall", true);
    }

    public void setup(int index, String imageName, boolean collision){

        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tile_blocks/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) {
        try (InputStream is = getClass().getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            int row = 0;

            while (row < gp.maxWorldRow) {

                String line = br.readLine();

                if (line == null) break; // Stop if the file ends unexpectedly
                String[] numbers = line.split(" ");
                for (int col = 0; col < gp.maxWorldCol; col++) {
                    mapTileNum[map][col][row] = Integer.parseInt(numbers[col]);
                }
                row++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int windowX = worldX - gp.player.worldX + gp.player.windowX;
            int windowY = worldY - gp.player.worldY + gp.player.windowY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.windowX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.windowX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.windowY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.windowY){

                g2.drawImage(tile[tileNum].image, windowX, windowY, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }

        if (drawPath == true){
            g2.setColor(new Color(255, 0,0, 70));
            for (int i = 0; i < gp.pFinder.pathList.size(); i++){
                int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
                int windowX = worldX - gp.player.worldX + gp.player.windowX;
                int windowY = worldY - gp.player.worldY + gp.player.windowY;

                g2.fillRect(windowX, windowY, gp.tileSize, gp.tileSize);
            }
        }
    }

}