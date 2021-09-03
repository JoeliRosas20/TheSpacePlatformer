import Engine.Animation;
import Engine.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game extends Canvas implements Runnable{

    public static int width = 300;
    public static int height = width / 16 * 9;
    public static int scale = 3;
    private Thread thread;
    private boolean running = false;
    BufferedImage image;
    File f;
    Animation animation;
    Sprite sprite;

    public Game(){
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
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

    public void loadImages(){
        BufferedImage player = loadImage("Images//Jump (1).png");
        BufferedImage player2 = loadImage("Images//Jump (2).png");
        BufferedImage player3 = loadImage("Images//Jump (3).png");
        BufferedImage player4 = loadImage("Images//Jump (4).png");
        BufferedImage player5 = loadImage("Images//Jump (5).png");
        BufferedImage player6 = loadImage("Images//Jump (6).png");
        BufferedImage player7 = loadImage("Images//Jump (7).png");
        BufferedImage player8 = loadImage("Images//Jump (8).png");
        BufferedImage player9 = loadImage("Images//Jump (9).png");
        BufferedImage player10 = loadImage("Images//Jump (10).png");
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
        sprite = new Sprite(animation);
        sprite.setDx(0.2f);
        sprite.setDy(0.2f);
    }

    public synchronized void start(){
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    public synchronized void stop(){
        running = false;
        try {
            thread.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running){
            //update();
            loadImages();
            render();
        }
        stop();
    }

    public void update(long elapsedTime){
        if (sprite.getX() < 0){
            sprite.setDx(Math.abs(sprite.getDx()));
        }
        else if (sprite.getX() + sprite.getWidth() >= width * scale){
            sprite.setDx(-Math.abs(sprite.getDx()));
        }
        if (sprite.getY() < 0){
            sprite.setDy(Math.abs(sprite.getDy()));
        }
        else if (sprite.getY() + sprite.getHeight() >= height * scale){
            sprite.setDy(-Math.abs(sprite.getDy()));
        }
        sprite.update(elapsedTime);
    }

    public void render(){
        long startTime = System.currentTimeMillis();
        long currTime = startTime;
        while (true) {
            //System.out.println("The time is: " + (currTime - startTime));
            long elapsedTime = System.currentTimeMillis() - currTime;
            currTime += elapsedTime;
            //animation.update(elapsedTime);
            update(elapsedTime);
            BufferStrategy strategy = getBufferStrategy();
            if (strategy == null) {
                createBufferStrategy(3);
                return;
            }
            Graphics g = strategy.getDrawGraphics();
            g.setColor(Color.blue);
            g.fillRect(0, 0, getWidth(), getHeight());
            //g.drawImage(animation.getImage(), 1, 1, null);
            g.drawImage(sprite.getImage(), Math.round(sprite.getX()), Math.round(sprite.getY()), null);
            g.dispose();
            strategy.show();
        }
    }

}
