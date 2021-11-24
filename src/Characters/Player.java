package Characters;

import Engine.*;

public class Player extends Sprite {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_JUMPING = 1;
    public static final int STATE_SHOOTING = 2;
    public static final int STATE_DYING = 3;
    public static final int STATE_DEAD = 4;
    public static final float SPEED = .3f;
    public static final float GRAVITY = .002f;
    private static final int DIE_TIME = 1500;
    private int floorY;
    private int state;
    private long stateTime;
    Animation idleL, idleR, jumpL, jumpR, walkL, walkR, shootL, shootR, deadL, deadR;

    public Player(Animation idleL, Animation idleR, Animation jumpL, Animation jumpR, Animation walkL,
    Animation walkR, Animation shootL, Animation shootR, Animation deadL, Animation deadR){
        super(idleR);
        this.idleL = idleL;
        this.idleR = idleR;
        this.jumpL = jumpL;
        this.jumpR = jumpR;
        this.walkL = walkL;
        this.walkR = walkR;
        this.shootL = shootL;
        this.shootR = shootR;
        this.deadL = deadL;
        this.deadR = deadR;
        state = STATE_NORMAL;
    }

    /**
     * Gets state of the player
     * @return player current state
     */
    public int getState(){
        return state;
    }

    /**
     * Sets the player state
     * @param state current position of player
     */
    public void setState(int state){
        if (this.state != state) {
            this.state = state;
            stateTime = 0;
            if (state == STATE_DYING) {
                setDx(0);
                setDy(0);
            }
        }
    }

    /**
     * Setting the floor for the player
     * @param floorY y position of player
     */
    public void setFloorY(int floorY){
        this.floorY = floorY;
        setY(floorY);
    }

    public void jump(){
        setDy(-1);
        state = STATE_JUMPING;
    }

    public void shoot(Bullet bullet, int playerX, int playerY, int dx){
        bullet.setX(playerX+50);
        bullet.setY(playerY+50);
        if (animation == idleR || animation == walkR){
            bullet.setDx(0.5f);
            bullet.setFace(1);
        }
        if (animation == idleL || animation == walkL){
            bullet.setDx(-0.5f);
            bullet.setFace(0);
        }
        setState(STATE_SHOOTING);
    }

    /**
     * Player update
     * @param elapsedTime amount of time passed
     */
    public void update(long elapsedTime){
        Animation nAnim = animation;
        //Set the vertical velocity for jumping left
        if (getState() == STATE_JUMPING && (nAnim == walkL || nAnim == idleL)){
            setDy(getDy() + GRAVITY * elapsedTime);
            nAnim = jumpL;
        }
        else if (getState() == STATE_JUMPING && (nAnim == walkR || nAnim == idleR)){
            setDy(getDy() + GRAVITY * elapsedTime);
            nAnim = jumpR;
        }
        //Walking
        if (getDx() < 0){
            nAnim = walkL;
        }
        else if (getDx() > 0){
            nAnim = walkR;
        }
        //Shooting
        if (getState() == STATE_SHOOTING && nAnim == idleL){
            nAnim = shootL;
        }
        else if (getState() == STATE_SHOOTING && nAnim == idleR){
            nAnim = shootR;
        }
        //Idle
        if (getDx() == 0 &&(nAnim == walkL || nAnim == jumpL)){
            nAnim = idleL;
            setState(STATE_NORMAL);
        }
        else if (getDx() == 0 && (nAnim == walkR || nAnim == jumpR)){
            nAnim = idleR;
            setState(STATE_NORMAL);
        }
        //Dead
        if (state == STATE_DYING && (nAnim == idleL || nAnim == walkL)){
            nAnim = deadL;
        }
        else if (state == STATE_DYING && (nAnim == idleR || nAnim == walkR)){
            nAnim = deadR;
        }
        //super.update(elapsedTime);
        //Check if player landed on floor
        if (getState() == STATE_JUMPING && getY() >= floorY && nAnim == jumpL){
            setDy(0);
            setY(floorY);
            setState(STATE_NORMAL);
            nAnim = idleL;
        }
        else if (getState() == STATE_JUMPING && getY() >= floorY && nAnim == jumpR){
            setDy(0);
            setY(floorY);
            setState(STATE_NORMAL);
            nAnim = idleR;
        }
        super.update(elapsedTime);
        if (animation != nAnim){
            animation = nAnim;
            animation.start();
        }
        else{
            animation.update(elapsedTime);
        }
        stateTime += elapsedTime;
        //System.out.println("First:"+stateTime);
        if (state == STATE_DYING && stateTime >= DIE_TIME){
            //System.out.println("Inside:"+stateTime);
            setState(STATE_DEAD);
        }
    }

}
