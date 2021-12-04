package Input;

import java.awt.*;
import java.awt.event.*;

/**
 * Manages the input of keys
 */
public class InputManager implements KeyListener {

    private static final int NUM_KEY_CODES = 600;
    private GameAction[] keyActions = new GameAction[NUM_KEY_CODES];
    private Component component;

    public InputManager(Component component){
        this.component = component;
        component.addKeyListener(this);
    }

    /**
     * Maps GameAction to specific key
     * @param gameAction
     * @param keyCode
     */
    public void mapToKey(GameAction gameAction, int keyCode){
        keyActions[keyCode] = gameAction;
    }

    /**
     * Resets all GameAction so they appear like they haven't pressed
     */
    public void resetAllGameActions(){
        for (GameAction keyAction : keyActions) {
            if (keyAction != null) {
                keyAction.reset();
            }
        }
    }

    public GameAction getKeyAction(KeyEvent event){
        int keyCode = event.getKeyCode();
        if (keyCode < keyActions.length){
            return keyActions[keyCode];
        }
        else {
            return null;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        e.consume();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        GameAction gameAction = getKeyAction(e);
        if (gameAction != null){
            gameAction.press();
        }
        e.consume();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        GameAction gameAction = getKeyAction(e);
        if (gameAction != null){
            gameAction.released();
        }
        e.consume();
    }
}
