import Engine.Animation;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    Animation animation;

    public GamePanel(){
        System.out.println("Inside constructor(GamePanel.java)");
        // Disable the paint notification from the System
        this.setIgnoreRepaint(true);
        setIgnoreRepaint(true);
        //this.setBackground(Color.black);
        this.setPreferredSize(new Dimension(400, 300));
        //loadImages();
    }

    public void loadImages(){
        System.out.println("Inside loadImages(GamePanel.java)");
        Image player = loadImage("Images//Jump (1).png");
        Image player2 = loadImage("Images//Jump (2).png");
        Image player3 = loadImage("Images//Jump (3).png");
        animation = new Animation();
        animation.addFrame(player, 100);
        animation.addFrame(player2, 100);
        animation.addFrame(player3, 100);
    }

    private Image loadImage(String name){
        return new ImageIcon(name).getImage();
    }

    public void animationLoop(){
        System.out.println("Inside animationLoop(GamePanel.java)");
        long startTime = System.currentTimeMillis();
        long currTime = startTime;
        while (currTime - startTime < 5000){
            long elapsedTime = System.currentTimeMillis() - currTime;
            currTime += elapsedTime;
            animation.update(elapsedTime);
            Graphics g = this.getGraphics();
            if (g == null){
                System.out.println("Null");
                break;
            }
            draw(g);
            g.dispose();
            try{
                Thread.sleep(20);
            }catch (InterruptedException e){}
        }
    }

    public void draw(Graphics graphics){
        super.paint(graphics);
        graphics.drawImage(animation.getImage(), 100, 100, null);
    }

}
