package Engine;

public class PlayerTest2 extends Sprite {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_JUMPING = 1;
    public static final float SPEED = .3f;
    public static final float GRAVITY = .002f;
    private int floorY;
    private int state;
    Animation idle;
    Animation jump;
    private float ay;

    public PlayerTest2(Animation idle, Animation jump) {
        super(idle);
        this.idle = idle;
        this.jump = jump;
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

    public void stop(){
        setDx(0);
        setDy(0);
        ay = 0;
    }

    public void applyGravity(){
        ay = GRAVITY;
    }


    public void update(long elapsedTime){
        //System.out.println("(Engine.PlayerTest2.java) update()");
        Animation nAnim = animation;
        //Set the vertical velocity for jumping
        if (getState() == STATE_JUMPING){
            //System.out.println("(Engine.PlayerTest2.java) update(), if state is jumping");
            setDy(getDy() + GRAVITY * elapsedTime);
            nAnim = jump;
            //System.out.println("(Engine.PlayerTest2.java) update(), if state is jumping:"+(getDy() + GRAVITY * elapsedTime));
        }
        //Moves the player
        super.update(elapsedTime);
        System.out.println("(PlayerTest2.java)Y is:"+Math.round(getY())+"\n");
        //System.out.println("Floor is:"+floorY);
        //Check if player landed on floor
        if (getState() == STATE_JUMPING && getY() >= floorY){
            //System.out.println("(Engine.PlayerTest2.java) update(), if state is jumping and not on the floor");
            setDy(0);
            setY(floorY);
            setState(STATE_NORMAL);
            nAnim = idle;
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
