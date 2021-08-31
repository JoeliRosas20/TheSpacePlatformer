package Engine;

public class Sprite {

    private Animation animation;
    private float x;
    private float y;
    private float dx;
    private float dy;

    public Sprite(Animation animation){
        this.animation = animation;
    }

    public void update(long elapsedTime){
        x += dx * elapsedTime;
        y += dy * elapsedTime;
    }



}
