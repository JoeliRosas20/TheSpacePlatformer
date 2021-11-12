package Engine;

import java.awt.*;

public class Camera {

    public static float x;
    public static float y;
    public static float dx;
    public static float dy;
    public static float ay = 0;
    public static int xOrigin = 0;
    public static int yOrigin = 0;
    public static final int GRAVITY = 1;
    public static Resources resources = new Resources();
    public static PlayerTest2 playerT = resources.getPlayer();

    public static void update(long elapsedTime){
        //System.out.println("Updating");
        x += dx * elapsedTime;
        y += dy * elapsedTime;
        //System.out.println(x+" "+y);
    }

    public static void setX(float newX){
        x = newX;
    }

    public static void setY(float newY){
        y = newY;
    }

    public static void setDy(float newDy){
        dy = newDy;
    }

    public static void setDx(float newDx){
        dx = newDx;
    }

    public static void jump(){
        setDy(-0.8f);
    }

    public static void applyGravity(){
        ay = GRAVITY;
    }

    public static void draw(Graphics g){
        //g.setColor(Color.GREEN);
        //g.drawRect(Math.round(playerT.getX()), Math.round(playerT.getY()), playerT.getWidth(), playerT.getHeight());
    }

}
