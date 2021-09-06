package TileMap;

import Engine.Sprite;

import java.awt.*;
import java.awt.image.*;
import java.util.Iterator;
import java.util.LinkedList;

public class TileMap {

    private BufferedImage[][] tiles;
    private LinkedList sprites;
    private Sprite player;

    public TileMap(int width, int height){
        tiles = new BufferedImage[width][height];
        sprites = new LinkedList();
    }

    public int getWidth(){
        return tiles.length;
    }

    public int getHeight(){
        return tiles[0].length;
    }

    public BufferedImage getTile(int x, int y){
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()){
            return null;
        }
        else {
            return tiles[x][y];
        }
    }

    public void setTile(int x, int y, BufferedImage tile){
        tiles[x][y] = tile;
    }

    public Sprite getPlayer(){
        return player;
    }

    public void setPlayer(Sprite player){
        this.player = player;
    }

    public void addSprite(Sprite sprite){
        sprites.add(sprite);
    }

    public void removeSprite(Sprite sprite){
        sprites.remove(sprite);
    }

    public Iterator getSprites(){
        return sprites.iterator();
    }

}
