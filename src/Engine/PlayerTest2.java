package Engine;

import Characters.Bullet;
import TileMap.TileMap;
import java.util.LinkedList;

public class PlayerTest2 extends Sprite {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_JUMPING = 1;
    public static final float SPEED = .3f;
    public static final float GRAVITY = .002f;
    private int floorY;
    private int state;
    Animation idle;
    Animation jump;
    LinkedList<Bullet> bullets = new LinkedList<>();

    public PlayerTest2(Animation idle, Animation jump) {
        super(idle);
        this.idle = idle;
        this.jump = jump;
    }

    /**
     * Gets state of the player
     * @return player current state
     */
    public int getState() {
        return state;
    }

    /**
     * Sets the player state
     * @param state
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Setting the floor for the player
     * @param floorY
     */
    public void setFloorY(int floorY) {
        this.floorY = floorY;
        setY(floorY);
    }

    /**
     * Player jump
     */
    public void jump(){
        setDy(-0.8f);
        state = STATE_JUMPING;
    }

    public void shoot(Bullet bullet, int playerX, int playerY){
        bullet.setX(playerX+50);
        bullet.setY(playerY+50);
        bullet.setDx(0.5f);
        bullets.add(bullet);
    }

    public void removeBullet(Bullet bullet){

    }

    /**
     * Player update
     * @param elapsedTime
     */
    public void update(long elapsedTime){
        Animation nAnim = animation;
        //Set the vertical velocity for jumping
        if (getState() == STATE_JUMPING){
            setDy(getDy() + GRAVITY * elapsedTime);
            nAnim = jump;
        }
        //Moves the player
        super.update(elapsedTime);
        //Check if player landed on floor
        if (getState() == STATE_JUMPING && getY() >= floorY){
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
