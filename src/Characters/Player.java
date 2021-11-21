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
    private int floorY;
    private int state;
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
    }

    public int getState(){
        return state;
    }

    public void setState(int state){
        this.state = state;
    }

    public void setFloorY(int floorY){
        this.floorY = floorY;
        setY(floorY);
    }

    public void jump(){
        setDy(-0.8f);
        state = STATE_JUMPING;
    }

    public void shoot(Bullet bullet, int playerX, int playerY){
        bullet.setX(playerX+50);
        bullet.setY(playerY+50);
        bullet.setDx(0.5f);
    }

    public void update(int elapsedTime){
        Animation nAnim = animation;
        //Set the vertical velocity for jumping left
        if (getState() == STATE_JUMPING && nAnim == walkL || nAnim == idleL){
            setDy(getDy() + GRAVITY * elapsedTime);
            nAnim = jumpL;
        }
        else if (getState() == STATE_JUMPING && nAnim == walkR || nAnim == idleR){
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
        if (getState() == STATE_SHOOTING && nAnim == walkL || nAnim == idleL){
            nAnim = shootL;
        }
        else if (getState() == STATE_SHOOTING && nAnim == walkR || nAnim == idleR){
            nAnim = shootR;
        }
        //Idle
        if (getDx() == 0 && nAnim == walkL || nAnim == jumpL){
            nAnim = idleL;
            setState(STATE_NORMAL);
        }
        else if (getDx() == 0 && nAnim == walkR || nAnim == jumpR){
            nAnim = idleR;
            setState(STATE_NORMAL);
        }
        //Dead
        if (state == STATE_DYING && nAnim == idleL){
            nAnim = deadL;
        }
        else if (state == STATE_DYING && nAnim == idleR){
            nAnim = deadR;
        }
        super.update(elapsedTime);
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
        if (animation != nAnim){
            animation = nAnim;
            animation.start();
        }
        else{
            animation.update(elapsedTime);
        }
    }

}
