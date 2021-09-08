package Engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Resources {

    public static int width = 300;
    public static int height = width / 16 * 9;
    public static int scale = 3;
    BufferedImage image;
    File f;
    Animation animation, animation2;
    PlayerTest2 playerTest2;

    public Resources(){
        loadPlayerImages();
    }

    public PlayerTest2 getPlayer(){
        return playerTest2;
    }

    /**
     * Loads the images of the player onto the game
     */
    public void loadPlayerImages(){
        //Load Idle, Jump, Walk Right, Walk Left, and Die
        BufferedImage[][] images = new BufferedImage[5][];
        BufferedImage player = loadImage("Images//Idle (1).png");
        BufferedImage player2 = loadImage("Images//Idle (2).png");
        BufferedImage player3 = loadImage("Images//Idle (3).png");
        BufferedImage player4 = loadImage("Images//Idle (4).png");
        BufferedImage player5 = loadImage("Images//Idle (5).png");
        BufferedImage player6 = loadImage("Images//Idle (6).png");
        BufferedImage player7 = loadImage("Images//Idle (7).png");
        BufferedImage player8 = loadImage("Images//Idle (8).png");
        BufferedImage player9 = loadImage("Images//Idle (9).png");
        BufferedImage player10 = loadImage("Images//Idle (10).png");
        BufferedImage player11 = loadImage("Images//Jump (1).png");
        BufferedImage player12 = loadImage("Images//Jump (2).png");
        BufferedImage player13 = loadImage("Images//Jump (3).png");
        BufferedImage player14 = loadImage("Images//Jump (4).png");
        BufferedImage player15 = loadImage("Images//Jump (5).png");
        BufferedImage player16 = loadImage("Images//Jump (6).png");
        BufferedImage player17 = loadImage("Images//Jump (7).png");
        BufferedImage player18 = loadImage("Images//Jump (8).png");
        BufferedImage player19 = loadImage("Images//Jump (9).png");
        BufferedImage player20 = loadImage("Images//Jump (10).png");
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
        animation2.addFrame(player11, 100);
        animation2.addFrame(player12, 100);
        animation2.addFrame(player13, 100);
        animation2.addFrame(player14, 100);
        animation2.addFrame(player15, 100);
        animation2.addFrame(player16, 100);
        animation2.addFrame(player17, 100);
        animation2.addFrame(player18, 100);
        animation2.addFrame(player19, 100);
        animation2.addFrame(player20, 100);
        playerTest2 = new PlayerTest2(animation, animation2);
        System.out.println(height * scale);
        playerTest2.setFloorY((height * scale) - playerTest2.getHeight());
        System.out.println(playerTest2.getHeight());
    }

    public BufferedImage loadImage(String name){
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
