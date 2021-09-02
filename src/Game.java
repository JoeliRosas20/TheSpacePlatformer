import Engine.Animation;

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
            update();
            loadImages();
            render();
        }
        stop();
    }

    public void update(){

    }

    public void render(){
        long startTime = System.currentTimeMillis();
        long currTime = startTime;
        while (true) {
            //System.out.println("The time is: " + (currTime - startTime));
            long elapsedTime = System.currentTimeMillis() - currTime;
            currTime += elapsedTime;
            animation.update(elapsedTime);
            BufferStrategy strategy = getBufferStrategy();
            if (strategy == null) {
                createBufferStrategy(3);
                return;
            }
            Graphics g = strategy.getDrawGraphics();
            g.setColor(Color.blue);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(animation.getImage(), 1, 1, null);
            g.dispose();
            strategy.show();
        }
    }

}
