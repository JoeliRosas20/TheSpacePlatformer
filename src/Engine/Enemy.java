package Engine;

public class Enemy extends Sprite{

    Animation left;
    Animation right;

    public Enemy(Animation left, Animation right){
        super(left);
        this.right = right;
        this.left = left;
    }

    /**
     * Enemy default walk speed
     */
    public void move() { setDx(0.05f); }

    /**
     * Enemy update
     * @param elapsedTime
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
