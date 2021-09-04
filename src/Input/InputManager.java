package Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

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
     * Clears all mapped keys
     * @param gameAction
     */
    public void clearMap(GameAction gameAction){
        for (int i = 0; i < keyActions.length; i++){
            if (keyActions[i] == gameAction){
                keyActions[i] = null;
            }
        }
        gameAction.reset();
    }

    /**
     * Gets list of names keys mapped to this action
     * @param gameCode
     * @return
     */
    public ArrayList getMaps(GameAction gameCode){
        ArrayList list = new ArrayList();
        for (int i = 0; i < keyActions.length; i++){
            if (keyActions[i] == gameCode){
                list.add(getKeyName(i));
            }
        }
        return list;
    }

    /**
     * Resets all GameAction so they appear like they haven't pressed
     */
    public void resetAllGameActions(){
        for(int i = 0; i < keyActions.length; i++){
            if (keyActions[i] != null){
                keyActions[i].reset();
            }
        }
    }

    /**
     * Gets name of key
     * @param keyCode
     * @return
     */
    public static String getKeyName(int keyCode){
        return KeyEvent.getKeyText(keyCode);
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
