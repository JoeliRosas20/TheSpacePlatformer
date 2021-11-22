package TileMap;

import Characters.Alien;
import Engine.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
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
    public LinkedList<Enemy> aSprites = new LinkedList<>();
    public LinkedList<Alien> aliens = new LinkedList<>();
    private BufferedImage image;
    Animation eAnimation, eAnimation2;
    Enemy enemy;
    Alien alien;
    int row, col;

    public TileMap(String filename){
        loadMap(filename);
        loadTileImages();
        loadEnemyImages();
        loadEnemies();
    }

    //-----Methods to load the map, images, and enemy-----\\
    public void loadMap(String fileName){
        File file = new File(fileName);
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int n = Integer.parseInt(reader.readLine());//The number of rows the map txt has
            map = new String[n];//Creating map array
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
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadEnemies(){
        for (int row = 0; row < map.length; row++){
            for (int col = 0; col < map[row].length(); col++){
                char c = map[row].charAt(col);
                if (c == '?'){
                    addSprites(enemy, col*100, row*100);
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

    public void loadEnemyImages2(){
        BufferedImage[][] images = new BufferedImage[4][];
        images[0] = new BufferedImage[]{
                loadImage("Images//EnemyImages//armor__0006_walk_1_left.png"),
                loadImage("Images//EnemyImages//armor__0007_walk_2_left.png"),
                loadImage("Images//EnemyImages//armor__0008_walk_3_left.png"),
                loadImage("Images//EnemyImages//armor__0009_walk_4_left.png"),
                loadImage("Images//EnemyImages//armor__0010_walk_5_left.png"),
                loadImage("Images//EnemyImages//armor__0011_walk_6_left.png")
        };
        images[1] = new BufferedImage[]{
                loadImage("Images//EnemyImages//armor__0006_walk_1.png"),
                loadImage("Images//EnemyImages//armor__0007_walk_2.png"),
                loadImage("Images//EnemyImages//armor__0008_walk_3.png"),
                loadImage("Images//EnemyImages//armor__0009_walk_4.png"),
                loadImage("Images//EnemyImages//armor__0010_walk_5.png"),
                loadImage("Images//EnemyImages//armor__0011_walk_6.png")
        };
        images[2] = new BufferedImage[]{
                loadImage("Images//EnemyImages//Larmor__0022_dead_1.png"),
                loadImage("Images//EnemyImages//Larmor__0023_dead_2.png"),
                loadImage("Images//EnemyImages//Larmor__0024_dead_3.png"),
                loadImage("Images//EnemyImages//Larmor__0025_dead_4.png"),
                loadImage("Images//EnemyImages//Larmor__0026_dead_5.png")
        };
        images[3] = new BufferedImage[]{
                loadImage("Images//EnemyImages//armor__0022_dead_1.png"),
                loadImage("Images//EnemyImages//armor__0023_dead_2.png"),
                loadImage("Images//EnemyImages//armor__0024_dead_3.png"),
                loadImage("Images//EnemyImages//armor__0025_dead_4.png"),
                loadImage("Images//EnemyImages//armor__0026_dead_5.png")
        };
        Animation[] alienAnim = new Animation[4];
        alienAnim[0] = createAlienLeftAnim(images[0][0], images[0][1], images[0][2], images[0][3], images[0][4], images[0][5]);
        alienAnim[1] = createAlienRightAnim(images[1][0], images[1][1], images[1][2], images[1][3], images[1][4], images[1][5]);
        alienAnim[2] = createAlienDeadLAnim(images[2][0], images[2][1], images[2][2], images[2][3], images[2][4]);
        alienAnim[3] = createAlienDeadRAnim(images[3][0], images[3][1], images[3][2], images[3][3], images[3][4]);
        alien = new Alien(alienAnim[0], alienAnim[1], alienAnim[2], alienAnim[3]);
    }
    public void loadEnemyImages(){
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
        eAnimation.addFrame(enemy4, 150);
        eAnimation.addFrame(enemy5, 150);
        eAnimation.addFrame(enemy6, 150);
        eAnimation.addFrame(enemy7, 150);
        eAnimation.addFrame(enemy8, 150);
        eAnimation.addFrame(enemy9, 150);
        eAnimation2.addFrame(enemy12, 150);
        eAnimation2.addFrame(enemy13, 150);
        eAnimation2.addFrame(enemy14, 150);
        eAnimation2.addFrame(enemy15, 150);
        eAnimation2.addFrame(enemy16, 150);
        eAnimation2.addFrame(enemy17, 150);
        enemy = new Enemy(eAnimation, eAnimation2);
    }

    //-----Enemy stuff-----\\
    /**
     * This method helps us get access of the enemy that is stored in the List
     * @param num this is the enemy you want to call
     * @return enemy
     */
    public Enemy getEnemy(int num){
        return aSprites.get(num);
    }

    /**
     * Gets the size of the List storing the enemies
     * @return list size
     */
    public int getSize(){
        return aSprites.size();
    }

    /**
     * Gets access to all enemies within the list
     * @return gets the list of enemies
     */
    public Iterator getEnemies(){
        return aSprites.iterator();
    }

    //-----Tile Map stuff-----\\
    /**
     * Returns the value of y and x of requested tile
     * @param y the x position of item
     * @param x the y position of item
     * @return the tile located at that position
     */
    public char valueAt(int y, int x){
        row = (y / 100);
        col = (x / 100);
        return map[row].charAt(col);
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public void draw(Graphics g){
        for (int row = 0; row < map.length; row++){
            for (int col = 0; col < map[row].length(); col++){
                char c = map[row].charAt(col);
                if (c != '#') {
                    g.drawImage(tile[c - '?'], col * 100 - (int) Camera.x, row * 100, null);
                }
                if (c == '?'){
                    Iterator i = getEnemies();
                    while (i.hasNext()) {
                        Sprite alien = (Sprite)i.next();
                        g.drawImage(alien.getImage(), Math.round(alien.getX()- (int) Camera.x), Math.round(alien.getY()), null);
                    }
                }
            }
        }
    }

    //-----The helper methods-----\\
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

    private void addSprites(Enemy sprite, int x, int y){
        Enemy tAlien = (Enemy) sprite.clone();
        tAlien.setX(x);
        tAlien.setY(y);
        aSprites.add(tAlien);
    }

    public void removeSprite(Enemy sprite){
        aSprites.remove(sprite);
    }

    public int getTileSize(){
        int row = 0;
        for (String s : map) {
            row = s.length();
        }
        return row*100;
    }

    private Animation createAlienLeftAnim(BufferedImage alien1, BufferedImage alien2, BufferedImage alien3,
                                          BufferedImage alien4, BufferedImage alien5, BufferedImage alien6){
        Animation animation = new Animation();
        animation.addFrame(alien1, 100);
        animation.addFrame(alien2, 100);
        animation.addFrame(alien3, 100);
        animation.addFrame(alien4, 100);
        animation.addFrame(alien5, 100);
        animation.addFrame(alien6, 100);
        return animation;
    }

    private Animation createAlienRightAnim(BufferedImage alien1, BufferedImage alien2, BufferedImage alien3,
                                           BufferedImage alien4, BufferedImage alien5, BufferedImage alien6){
        Animation animation = new Animation();
        animation.addFrame(alien1, 100);
        animation.addFrame(alien2, 100);
        animation.addFrame(alien3, 100);
        animation.addFrame(alien4, 100);
        animation.addFrame(alien5, 100);
        animation.addFrame(alien6, 100);
        return animation;
    }

    private Animation createAlienDeadLAnim(BufferedImage alien1, BufferedImage alien2, BufferedImage alien3,
                                           BufferedImage alien4, BufferedImage alien5){
        Animation animation = new Animation();
        animation.addFrame(alien1, 100);
        animation.addFrame(alien2, 100);
        animation.addFrame(alien3, 100);
        animation.addFrame(alien4, 100);
        animation.addFrame(alien5, 100);
        return animation;
    }

    private Animation createAlienDeadRAnim(BufferedImage alien1, BufferedImage alien2, BufferedImage alien3,
                                           BufferedImage alien4, BufferedImage alien5){
        Animation animation = new Animation();
        animation.addFrame(alien1, 100);
        animation.addFrame(alien2, 100);
        animation.addFrame(alien3, 100);
        animation.addFrame(alien4, 100);
        animation.addFrame(alien5, 100);
        return animation;
    }

}
