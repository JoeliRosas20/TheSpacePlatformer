import Engine.Animation;
import Engine.Sprite;

public class PlayerTest extends Sprite {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_JUMPING = 1;
    public static final float SPEED = .3f;
    public static final float GRAVITY = .002f;
    private int floorY;
    private int state;

    public PlayerTest(Animation animation) {
        super(animation);
        state = STATE_NORMAL;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setFloorY(int floorY) {
        this.floorY = floorY;
        setY(floorY);
    }

    public void jump(){
        setDy(-0.8f);
        state = STATE_JUMPING;
    }

    public void update(long elapsedTime){
        //Set the vertical velocity for jumping
        if (getState() == STATE_JUMPING){
            setDy(getDy() + GRAVITY * elapsedTime);
        }
        //Moves the player
        super.update(elapsedTime);
        //Check if player landed on floor
        if (getState() == STATE_JUMPING && getY() >= floorY){
            setDy(0);
            setY(floorY);
            setState(STATE_NORMAL);
        }
    }
}
