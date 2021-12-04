package Engine;

import java.awt.*;

public class Camera {

    public static float x;
    public static float y;
    public static float dx;
    public static float dy;
    public static float ay = 0;
    public static final int GRAVITY = 1;

    public static void update(long elapsedTime){
        x += dx * elapsedTime;
        y += dy * elapsedTime;
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

    public static void applyGravity(){
        ay = GRAVITY;
    }

    public static void draw(Graphics g){ }

}
