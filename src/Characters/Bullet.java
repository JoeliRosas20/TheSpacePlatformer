package Characters;

import Engine.*;

public class Bullet extends Sprite {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private int state;
    Animation left, right, muzzle;
    Player player;

    public Bullet(Animation left, Animation right, Animation muzzle) {
        super(right);
        this.left = left;
        this.right = right;
        this.muzzle = muzzle;
    }

    public void setFace(int num){
        state = num;
    }

    public void update(long elapsedTime){
        Animation nAnim = new Animation();
        if (state == LEFT){//negative
            nAnim = left;
        }
        if (state == RIGHT){//positive
            nAnim = right;
        }
        super.update(elapsedTime);
        if (animation != nAnim){
            animation = nAnim;
            animation.start();
        }
        else{
            animation.update(elapsedTime);
        }
    }
}
