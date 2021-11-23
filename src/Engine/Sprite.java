package Engine;

import java.awt.*;

public class Sprite {

    public static final int GRAVITY = 1;
    public Animation animation;
    private float x;
    private float y;
    private float dx;
    private float dy;
    private int ay = 0;

    public Sprite(Animation animation){
        this.animation = animation;
    }

    public void update(long elapsedTime){
        x += dx * elapsedTime;
        y += dy * elapsedTime;
        //System.out.println("Y is:"+y);
        animation.update(elapsedTime);
    }

    /**
     * Gets the x value of the sprite
     * @return x
     */
    public float getX(){
        return x;
    }

    /**
     * Gets the y value of the sprite
     * @return y
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the x value of sprite
     * @param x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Sets the y value of the sprite
     * @param y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Gets the width of the sprite
     * @return width
     */
    public int getWidth(){
        return animation.getImage().getWidth(null);
    }

    /**
     * Gets the height of the sprite
     * @return height
     */
    public int getHeight(){
        return animation.getImage().getHeight(null);
    }

    /**
     * Gets the sprite x velocity
     * @return x velocity
     */
    public float getDx() {
        return dx;
    }

    /**
     * Gets the sprite y velocity
     * @return y velocity
     */
    public float getDy() {
        return dy;
    }

    /**
     * Sets the x velocity of the sprite
     * @param dx
     */
    public void setDx(float dx) {
        this.dx = dx;
    }

    /**
     * Sets the y velocity of the sprite
     * @param dy
     */
    public void setDy(float dy) {
        this.dy = dy;
    }

    /**
     * Applies the gravity when sprite in the air
     */
    public void applyGravity(){
        this.ay = GRAVITY;
    }

    /**
     * Enemy default walk speed
     */
    public void move() { setDx(0.05f); }

    /**
     * Gets the image of the sprite
     * @return image
     */
    public Image getImage(){
        return animation.getImage();
    }

}
