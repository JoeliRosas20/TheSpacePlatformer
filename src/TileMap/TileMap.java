package TileMap;

import Engine.Animation;
import Engine.Enemy;
import Engine.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

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
    /**
     * This is where the sprites like enemies are stored in
     */
    public LinkedList<Sprite> sprites;
    BufferedImage image;
    Animation eAnimation, eAnimation2;
    Enemy enemy;

    public TileMap(String filename){
        loadMap(filename);
        loadTileImages();
        loadEnemyImages();
        loadEnemies();
    }

    /**
     * Access to the enemy object
     * @return enemy
     */
    public Enemy getEnemy(){
        return enemy;
    }

    public void loadMap(String fileName){
        File file = new File(fileName);
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int n = Integer.parseInt(reader.readLine());//The number of rows the map txt has
            map = new String[n];//creating map array
            for (int row = 0; row < n; row++){
                map[row] = reader.readLine();//This takes all the words in the txt file and store it in map array
            }
            //Now off to get the name of the image files
            n = Integer.parseInt(reader.readLine());//Number of images
            tileName = new String[n];//creating tileName array
            for (int i = 0; i < n; i++){
                tileName[i] = reader.readLine();//The name of the tile images is stored in the tileName array
            }
            reader.close();
        }catch (IOException e){ }
    }

    public void loadEnemies(){
        for (int row = 0; row < map.length; row++){
            for (int col = 0; col < map[row].length(); col++){
                char c = map[row].charAt(col);
                if (c == '@'){
                    addSprites(enemy);
                    enemy.setX(col*100);
                    enemy.setY(row*100);
                }
            }
        }
    }

    public void loadTileImages(){
        tile = new BufferedImage[tileName.length];
        for (int i = 0; i < tile.length; i++){
            tile[i] = loadImage(tileName[i]);//The array where the images are stored
        }
    }

    public void loadEnemyImages(){
        BufferedImage enemy1 = loadImage("Images//EnemyImages//armor__0003_turn_1.png");
        BufferedImage enemy2 = loadImage("Images//EnemyImages//armor__0004_turn_2.png");
        BufferedImage enemy3 = loadImage("Images//EnemyImages//armor__0005_turn_3.png");
        BufferedImage enemy4 = loadImage("Images//EnemyImages//armor__0006_walk_1.png");
        BufferedImage enemy5 = loadImage("Images//EnemyImages//armor__0007_walk_2.png");
        BufferedImage enemy6 = loadImage("Images//EnemyImages//armor__0008_walk_3.png");
        BufferedImage enemy7 = loadImage("Images//EnemyImages//armor__0009_walk_4.png");
        BufferedImage enemy8 = loadImage("Images//EnemyImages//armor__0010_walk_5.png");
        BufferedImage enemy9 = loadImage("Images//EnemyImages//armor__0011_walk_6.png");
        BufferedImage enemy12 = loadImage("Images//EnemyImages//armor__0006_walk_1_left.png");
        BufferedImage enemy13 = loadImage("Images//EnemyImages//armor__0007_walk_2_left.png");
        BufferedImage enemy14 = loadImage("Images//EnemyImages//armor__0008_walk_3_left.png");
        BufferedImage enemy15 = loadImage("Images//EnemyImages//armor__0009_walk_4_left.png");
        BufferedImage enemy16 = loadImage("Images//EnemyImages//armor__0010_walk_5_left.png");
        BufferedImage enemy17 = loadImage("Images//EnemyImages//armor__0011_walk_6_left.png");
        eAnimation = new Animation();
        eAnimation2 = new Animation();
        eAnimation.addFrame(enemy4, 100);
        eAnimation.addFrame(enemy5, 100);
        eAnimation.addFrame(enemy6, 100);
        eAnimation.addFrame(enemy7, 100);
        eAnimation.addFrame(enemy8, 100);
        eAnimation.addFrame(enemy9, 100);
        eAnimation2.addFrame(enemy12, 100);
        eAnimation2.addFrame(enemy13, 100);
        eAnimation2.addFrame(enemy14, 100);
        eAnimation2.addFrame(enemy15, 100);
        eAnimation2.addFrame(enemy16, 100);
        eAnimation2.addFrame(enemy17, 100);
        enemy = new Enemy(eAnimation, eAnimation2);
    }

    private BufferedImage loadImage(String name){
        try {
            File f = new File(name);
            image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            image = ImageIO.read(f);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    private void addSprites(Sprite sprite){
        sprites = new LinkedList<>();
        sprites.add(sprite);
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
                    g.drawImage(tile[c - '@'], col * 100, row * 100, null);
                    //g.setColor(Color.green);
                    //g.drawRect(col * 100, row * 100, 100, 100);
                }
                if (c == '@'){
                    g.drawImage(enemy.getImage(), Math.round(enemy.getX()), Math.round(enemy.getY()), null);
                }
            }
        }
    }

}
