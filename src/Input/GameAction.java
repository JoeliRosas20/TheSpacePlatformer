package Input;

/**
 * Creates actions for the players and maps the keys.
 */
public class GameAction {

    /**
     * Normal behavior, 0. isPressed() returns true when key is held down
     */
    public static final int NORMAL = 0;
    /**
     * Initial press behavior, 1. isPressed() returns after key is first pressed
     * and not again til key is released and pressed again
     */
    public static final int DETECT_INITIAL_PRESS_ONLY = 1;
    public static final int STATE_RELEASED = 0;
    public static final int STATE_PRESSED = 1;
    public static final int STATE_WAITING_FOR_RELEASE = 2;
    private String name;
    private int behavior;
    private int amount;
    private int state;

    /**
     * Creates GameAction with NORMAL BEHAVIOR
     * @param name
     */
    public GameAction(String name){
        this(name, NORMAL);
    }

    /**
     * Creates new GameAction with specified behavior
     * @param name
     * @param behavior
     */
    public GameAction(String name, int behavior){
        this.name = name;
        this.behavior = behavior;
        reset();
    }

    /**
     * Get name of the action
     * @return
     */
    public String getName() {
        return name;
    }

    /**
    Reset GameAction so it appears nothing has happened
     **/
    public void reset(){
        state = STATE_RELEASED;
        amount = 0;
    }

    /**
     * When someone taps a button
     */
    public synchronized void tap(){
        press();
        released();
    }

    /**
     * Signals that key is pressed
     */
    public synchronized void press(){
        press(1);
    }

    /**
     * Signals that key was pressed a number of times
     * @param amount
     */
    public synchronized void press(int amount){
        System.out.println("(GameAction.java) press()");
        if (state != STATE_WAITING_FOR_RELEASE){
            this.amount += amount;
            state = STATE_PRESSED;
        }
    }

    /**
     * Signals that key was released
     */
    public synchronized void released(){
        System.out.println("(GameAction.java) released()");
        state = STATE_RELEASED;
    }

    /**
     * Returns whether key was pressed or not
     * @return
     */
    public synchronized boolean isPressed(){
        return (getAmount() != 0);
    }

    /**
     * This is the number of times the key was pressed since last checked
     * @return
     */
    public synchronized int getAmount(){
        int returnVal = amount;
        if (returnVal != 0){
            if (state == STATE_RELEASED){
                amount = 0;
            }
            else if (behavior == DETECT_INITIAL_PRESS_ONLY){
                state = STATE_WAITING_FOR_RELEASE;
            }
        }
        return returnVal;
    }
}
