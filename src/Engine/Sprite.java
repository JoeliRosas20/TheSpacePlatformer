package Engine;

import java.awt.*;

public class Sprite {

    public Animation animation;
    private float x;
    private float y;
    private float dx;
    private float dy;
    private int ay = 0;
    public static final int GRAVITY = 1;

    public Sprite(Animation animation){
        this.animation = animation;
    }

    public void update(long elapsedTime){
        //System.out.println("(Sprite.java) update()");
        //dy += ay;
        x += dx * elapsedTime;
        y += dy * elapsedTime;
        animation.update(elapsedTime);
    }

    /**
     * Gets the x value of the sprite
     * @return
     */
    public float getX(){
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth(){
        return animation.getImage().getWidth(null);
    }

    public int getHeight(){
        return animation.getImage().getHeight(null);
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public void applyGravity(){
        this.ay = GRAVITY;
    }

    public Image getImage(){
        return animation.getImage();
    }

    public Object clone(){
        return new Sprite(animation);
    }
}
