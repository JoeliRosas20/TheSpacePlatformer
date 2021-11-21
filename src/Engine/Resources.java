package Engine;

import Characters.Bullet;
import TileMap.TileMap;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Resources {

    public static int width = 300;
    public static int height = width / 16 * 9;
    private BufferedImage image;
    private File f;
    int curr = 1;
    Animation animation, animation2, bAnimation;
    PlayerTest2 playerTest2;
    Bullet bullet;
    TileMap map;

    public Resources(){
        loadPlayerImages();
        loadBulletImages();
    }

    public TileMap getMap(){
        return map;
    }

    //-----Accessors-----\\

    public PlayerTest2 getPlayer(){
        return playerTest2;
    }

    public Bullet getBullet(){
        return bullet;
    }

    public TileMap loadNextMap(int n){
        if (curr != n){
            System.out.println("True");
            curr = n;
        }
        System.out.println("Current is "+curr);
        map = new TileMap("Maps//world"+curr);
        return map;
    }

    public TileMap reloadMap(){
        try {
            return map;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //-----Loading the images-----\\

    /**
     * Loads the images of the player onto the game
     */
    public void loadPlayerImages(){
        //Load Idle, Jump, Walk Right, Walk Left, and Die
        BufferedImage[][] images = new BufferedImage[5][];
        BufferedImage player = loadImage("Images//PlayerImages//Idle (1).png");
        BufferedImage player2 = loadImage("Images//PlayerImages//Idle (2).png");
        BufferedImage player3 = loadImage("Images//PlayerImages//Idle (3).png");
        BufferedImage player4 = loadImage("Images//PlayerImages//Idle (4).png");
        BufferedImage player5 = loadImage("Images//PlayerImages//Idle (5).png");
        BufferedImage player6 = loadImage("Images//PlayerImages//Idle (6).png");
        BufferedImage player7 = loadImage("Images//PlayerImages//Idle (7).png");
        BufferedImage player8 = loadImage("Images//PlayerImages//Idle (8).png");
        BufferedImage player9 = loadImage("Images//PlayerImages//Idle (9).png");
        BufferedImage player10 = loadImage("Images//PlayerImages//Idle (10).png");
        BufferedImage player11 = loadImage("Images//PlayerImages//Jump (1).png");
        BufferedImage player12 = loadImage("Images//PlayerImages//Jump (2).png");
        BufferedImage player13 = loadImage("Images//PlayerImages//Jump (3).png");
        BufferedImage player14 = loadImage("Images//PlayerImages//Jump (4).png");
        BufferedImage player15 = loadImage("Images//PlayerImages//Jump (5).png");
        BufferedImage player16 = loadImage("Images//PlayerImages//Jump (6).png");
        BufferedImage player17 = loadImage("Images//PlayerImages//Jump (7).png");
        BufferedImage player18 = loadImage("Images//PlayerImages//Jump (8).png");
        BufferedImage player19 = loadImage("Images//PlayerImages//Jump (9).png");
        BufferedImage player20 = loadImage("Images//PlayerImages//Jump (10).png");
        animation = new Animation();
        animation2 = new Animation();
        animation.addFrame(player, 100);
        animation.addFrame(player2, 100);
        animation.addFrame(player3, 100);
        animation.addFrame(player4, 100);
        animation.addFrame(player5, 100);
        animation.addFrame(player6, 100);
        animation.addFrame(player7, 100);
        animation.addFrame(player8, 100);
        animation.addFrame(player9, 100);
        animation.addFrame(player10, 100);
        animation2.addFrame(player11, 200);
        animation2.addFrame(player12, 200);
        animation2.addFrame(player13, 200);
        animation2.addFrame(player14, 200);
        animation2.addFrame(player15, 200);
        animation2.addFrame(player16, 200);
        animation2.addFrame(player17, 200);
        animation2.addFrame(player18, 200);
        animation2.addFrame(player19, 200);
        animation2.addFrame(player20, 200);
        playerTest2 = new PlayerTest2(animation, animation2);

    }

    public void loadBulletImages(){
        BufferedImage bullet1 = loadImage("Images//BulletImages//Bullet_000.png");
        BufferedImage bullet2 = loadImage("Images//BulletImages//Bullet_001.png");
        BufferedImage bullet3 = loadImage("Images//BulletImages//Bullet_002.png");
        BufferedImage bullet4 = loadImage("Images//BulletImages//Bullet_003.png");
        BufferedImage bullet5 = loadImage("Images//BulletImages//Bullet_004.png");
        BufferedImage bullet6 = loadImage("Images//BulletImages//Muzzle_000.png");
        BufferedImage bullet7 = loadImage("Images//BulletImages//Muzzle_001.png");
        BufferedImage bullet8 = loadImage("Images//BulletImages//Muzzle_002.png");
        BufferedImage bullet9 = loadImage("Images//BulletImages//Muzzle_003.png");
        BufferedImage bullet10 = loadImage("Images//BulletImages//Muzzle_004.png");
        bAnimation = new Animation();
        bAnimation.addFrame(bullet1, 100);
        bAnimation.addFrame(bullet2, 100);
        bAnimation.addFrame(bullet3, 100);
        bAnimation.addFrame(bullet4, 100);
        bAnimation.addFrame(bullet5, 100);
        /*
        bAnimation.addFrame(bullet6, 100);
        bAnimation.addFrame(bullet7, 100);
        bAnimation.addFrame(bullet8, 100);
        bAnimation.addFrame(bullet9, 100);
        bAnimation.addFrame(bullet10, 100);
        */
        bullet = new Bullet(bAnimation);
        bullet.setX(-100);
        bullet.setY(-100);
    }

    private BufferedImage loadImage(String name){
        try {
            f = new File(name);
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            image = ImageIO.read(f);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

}
