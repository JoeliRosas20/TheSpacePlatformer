package Characters;

import Engine.*;

public class Alien extends Sprite{
    Animation left;
    Animation right;

    public Alien(Animation left, Animation right) {
        super(left);
        this.left = left;
        this.right = right;
    }

    /**
     * Enemy update
     * @param elapsedTime Time passed
     */
    public void update(long elapsedTime){
        Animation nAnim = animation;
        if (getDx() < 0){//negative
            nAnim = left;
        }
        else if (getDx() > 0){//positive
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

    /**
     * Creates another enemy
     * @return another enemy
     */
    public Object clone(){
        return new Enemy(left, right);
    }
}
