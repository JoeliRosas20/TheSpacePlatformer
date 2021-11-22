package Characters;

import Engine.*;

public class Alien extends Sprite{
    Animation left,deadL;
    Animation right, deadR;

    public Alien(Animation left, Animation right, Animation deadL, Animation deadR) {
        super(right);
        this.left = left;
        this.right = right;
        this.deadL = deadL;
        this.deadR = deadR;
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
        return new Alien(left, right, deadL, deadR);
    }
}
