package TileMap;

import Engine.Enemy;
import Engine.PlayerTest2;
import Engine.Resources;
import Engine.Sprite;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TileMap {

    /**
     * This is where the characters from the text file are stored in
     */
    public String[] map;
    /**
     * This is the arrayList where the image of the tiles are stored in
     */
    public BufferedImage[] tile;
    /**
     * This is where the name of the tile image are stored in
     */
    public String[] tileName;
    BufferedImage image;
    Enemy enemy;
    Resources resources;

    public TileMap(String filename){
        loadMap(filename);
        loadTileImages();
        //resources = new Resources();
        //enemy = resources.getEnemy();
    }

    public void loadMap(String fileName){
        File file = new File(fileName);
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int n = Integer.parseInt(reader.readLine());
            map = new String[n];
            for (int row = 0; row < n; row++){
                map[row] = reader.readLine();
            }
            n = Integer.parseInt(reader.readLine());
            tileName = new String[n];
            for (int i = 0; i < n; i++){
                tileName[i] = reader.readLine();
            }
            reader.close();
        }catch (IOException e){ }
    }

    public void loadTileImages(){
        tile = new BufferedImage[tileName.length];
        for (int i = 0; i < tile.length; i++){
            tile[i] = loadImage(tileName[i]);
        }
    }

    public BufferedImage loadImage(String name){
        try {
            File f = new File(name);
            image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            image = ImageIO.read(f);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Returns the value of y and x of requested tile
     * @param y the x position of item
     * @param x the y position of item
     * @return the tile located at that position
     */
    public char valueAt(int y, int x){
        int row = (y / 100);
        int col = (x / 100);
        //System.out.println("(TileMap.java)Row "+row+" and Col "+col);
        return map[row].charAt(col);
    }

    public void draw(Graphics g){
        for (int row = 0; row < map.length; row++){
            for (int col = 0; col < map[row].length(); col++){
                char c = map[row].charAt(col);
                if (c != '#') {
                    g.drawImage(tile[c - 'A'], col * 100, row * 100, null);
                    g.setColor(Color.green);
                    g.drawRect(col * 100, row * 100, 100, 100);
                }

            }
        }
    }

}
