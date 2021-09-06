package Engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Resources {

    public static int width = 300;
    public static int height = width / 16 * 9;
    BufferedImage image, bg;
    File f;
    Animation animation;

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
        animation = new Animation();
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
