package Characters;

import Engine.Animation;
import Engine.Sprite;

public class Bullet extends Sprite {

    Animation animation;

    public Bullet(Animation animation) {
        super(animation);
        this.animation = animation;
    }

    public void update(long elapsedTime){
        Animation nAnim = new Animation();
        if (getDx() < 0){//negative
            nAnim = animation;
        }
        else if (getDx() > 0){//positive
            nAnim = animation;
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
