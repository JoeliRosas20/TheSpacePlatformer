package TileMap;

import Engine.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TileMap {

    private Sprite player;
    public String[] map;
    public BufferedImage[] tile;
    public String[] tileName;
    BufferedImage image;
    public static int width = 300;
    public static int height = width / 16 * 9;

    public TileMap(String filename){
        loadMap(filename);
        loadTileImages();
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
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            image = ImageIO.read(f);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void draw(Graphics g){
        for (int row = 0; row < map.length; row++){
            for (int col = 0; col < map[row].length(); col++){
                char c = map[row].charAt(col);
                g.drawImage(tile[c - 'A'], col*100, row*100, null);
            }
        }
    }

}
