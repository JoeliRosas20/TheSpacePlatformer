package Engine;

public class Enemy extends Sprite{

    Animation left;
    Animation right;

    public Enemy(Animation left, Animation right){
        super(left);
    }

    public void update(long elapsedTime){
        Animation nAnim = animation;
        if (getDx() < 0){
            nAnim = left;
        }
        else if (getDy() > 0){
            nAnim = right;
        }
        if (animation != nAnim){
            animation = nAnim;
            animation.start();
        }
        else{
            animation.update(elapsedTime);
        }
    }

}
