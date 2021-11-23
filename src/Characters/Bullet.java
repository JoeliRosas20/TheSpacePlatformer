package Characters;

import Engine.*;

public class Bullet extends Sprite {

    Animation left, right, muzzle;

    public Bullet(Animation left, Animation right, Animation muzzle) {
        super(right);
        this.left = left;
        this.right = right;
        this.muzzle = muzzle;
    }

    public void update(long elapsedTime, int dx){
        Animation nAnim = new Animation();
        if (dx < 0){//negative
            nAnim = left;
        }
        else if (dx > 0){//positive
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
