package Characters;

import Engine.Animation;
import Engine.Sprite;

public class Bullet extends Sprite {

    Animation left, right, muzzle;

    public Bullet(Animation left, Animation right, Animation muzzle) {
        super(right);
        this.left = left;
        this.right = right;
        this.muzzle = muzzle;
    }

    public void update(long elapsedTime, Player player){
        Animation nAnim = new Animation();
        if (player.getDx() < 0){//negative
            nAnim = left;
        }
        else if (player.getDx() > 0){//positive
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
