package Characters;

import Engine.*;

public class Alien extends Sprite{

    Animation left;
    Animation right;
    int a = 0;

    public Alien(Animation left, Animation right) {
        super(right);
        this.left = left;
        this.right = right;
    }

    public void check(int n){
        a = n;
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
        super.update(elapsedTime/a);
        if (animation != nAnim){
            animation = nAnim;
            animation.start();
        }
        else{
            animation.update(elapsedTime/a);
        }
    }

    /**
     * Creates another enemy
     * @return another enemy
     */
    public Object clone(){
        return new Alien(left, right);
    }
}
