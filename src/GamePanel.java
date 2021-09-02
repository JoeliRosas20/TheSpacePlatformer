import Engine.Animation;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    Animation animation;

    public GamePanel(){
        this.setPreferredSize(new Dimension(600, 500));
    }

    public void loadImages(){
        Image player = loadImage("Images//Idle (1).png");
        Image player2 = loadImage("Images//Idle (2).png");
        Image player3 = loadImage("Images//Idle (3).png");
        Image player4 = loadImage("Images//Idle (4).png");
        Image player5 = loadImage("Images//Idle (5).png");
        Image player6 = loadImage("Images//Idle (6).png");
        Image player7 = loadImage("Images//Idle (7).png");
        Image player8 = loadImage("Images//Idle (8).png");
        Image player9 = loadImage("Images//Idle (9).png");
        Image player10 = loadImage("Images//Idle (10).png");
        animation = new Animation();
        /*
        animation.addFrame(player, 150);
        animation.addFrame(player2, 150);
        animation.addFrame(player3, 150);
        animation.addFrame(player4, 150);
        animation.addFrame(player5, 150);
        animation.addFrame(player6, 150);
        animation.addFrame(player7, 150);
        animation.addFrame(player8, 150);
        animation.addFrame(player9, 150);
        animation.addFrame(player10, 150);

         */
    }

    private Image loadImage(String name){
        return new ImageIcon(name).getImage();
    }

    public void animationLoop(){
        long startTime = System.currentTimeMillis();
        long currTime = startTime;
        while (currTime - startTime < 5000){
            long elapsedTime = System.currentTimeMillis() - currTime;
            currTime += elapsedTime;
            animation.update(elapsedTime);
            Graphics2D g = (Graphics2D) this.getGraphics();
            draw(g);
            g.dispose();
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){}
        }
    }

    public void draw(Graphics graphics){
        super.paint(graphics);
        graphics.drawImage(animation.getImage(), 100, 100, null);
    }

}
