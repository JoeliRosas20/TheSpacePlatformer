package TileMap;

import Engine.PlayerTest2;
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

    public boolean clearAbove(PlayerTest2 player){
        int top = (int) player.getY();
        int left = (int) player.getX();
        int right = (int) player.getX() + 1;

        //System.out.println("TOP = "+top);
        //System.out.println("(TileMap.java) Inside of clearAbove:"+(top));

//System.out.println("(TileMap.java) Inside of clearAbove:"+(top-4)+"\n");
        return (valueAt(top - 25, left) == '#') && (valueAt(top - 25, right) == '#');
    }

    public boolean clearBelow(PlayerTest2 player){
        int bottom = (int) player.getY() + 1;
        int left = (int) player.getX();
        int right = (int) player.getX() + 1;

        //System.out.println("BOTTOM = "+bottom);
        //System.out.println("(TileMap.java) Inside of clearBelow:"+(bottom - player.getDy()+1));

        return (valueAt((int) (bottom - (player.getDy())+1), left) == '#') && (valueAt((int) (bottom - (player.getDy())+1), right) == '#');
    }

    public boolean clearLeftOf(PlayerTest2 player){
        int top = (int) player.getY();
        int bottom = (int) player.getY() + 1;
        int left = (int) player.getX();

        //System.out.println("LEFT = "+left);
        //System.out.println("(TileMap.java) Inside of clearLeftOf:"+(left));

//        System.out.println("(TileMap.java) Inside of clearLeftOf:"+(left-8));
        return (valueAt(top, left - 20) == '#') && (valueAt(bottom, left - 20) == '#');
    }

    public boolean clearRightOf(PlayerTest2 player){
        int top = (int) player.getY();
        int bottom = (int) player.getY() + 1;
        int right = (int) player.getX() + 1;

        //System.out.println("RIGHT = "+right);
        //System.out.println("(TileMap.java) Inside clearRightOf:"+(right));

//System.out.println("(TileMap.java) Inside clearRightOf:"+(right+20));
        return (valueAt(top, right + 20) == '#') && (valueAt(bottom, (int) right + 20) == '#');
    }

    public char valueAt(int y, int x){
/*
        System.out.println("Y:"+y);
        System.out.println("X:"+x);
*/
        int row = (y / 100);
        int col = (x / 100);
/*
        System.out.println("Row:"+row);
        System.out.println("Col:"+col);
        System.out.println("(TileMap.java)Inside valueAt:"+map[row].charAt(col));
*/
        return map[row].charAt(col);
    }

    public void draw(Graphics g){
        for (int row = 0; row < map.length; row++){
            for (int col = 0; col < map[row].length(); col++){
                char c = map[row].charAt(col);
                if (c != '#')
                g.drawImage(tile[c - 'A'], col*100, row*100, null);
            }
        }
    }

}
