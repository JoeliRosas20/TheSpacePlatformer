package Characters;

import Engine.*;

public class Bullet extends Sprite {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int HIT = 2;
    private static final int HIT_TIME = 1000;
    private int state;
    private long stateTime;
    Animation left, right, muzzle;

    public Bullet(Animation left, Animation right, Animation muzzle) {
        super(right);
        this.left = left;
        this.right = right;
        this.muzzle = muzzle;
    }

    public void setFace(int num){
        state = num;
        stateTime = 0;
    }

    public void update(long elapsedTime){
        Animation nAnim = new Animation();
        if (state == LEFT){//negative
            nAnim = left;
        }
        if (state == RIGHT){//positive
            nAnim = right;
        }
        stateTime += elapsedTime;
        //System.out.println("Bullet "+stateTime);
        if (state == HIT){
            nAnim = muzzle;
        }
        super.update(elapsedTime);
        if (animation != nAnim){
            animation = nAnim;
            animation.start();
        }
        else{
            animation.update(elapsedTime);
        }
        stateTime += elapsedTime;
        //System.out.println("FIRST "+stateTime);
        if (state == HIT && stateTime >= HIT_TIME){
            //System.out.println("Now");
            setX(-100);
            setY(-100);
        }
    }
}
