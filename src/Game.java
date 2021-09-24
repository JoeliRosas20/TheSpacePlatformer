import Engine.*;
import Input.*;
import TileMap.TileMap;

import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class Game extends Canvas implements Runnable{

    public static int width = 300;
    public static int height = width / 16 * 9;
    public static int scale = 3;
    private Thread thread;
    private boolean running = false;
    BufferedImage image, bg, tile;
    File f;
    private boolean paused;
    protected InputManager inputManager;
    protected GameAction jump, exit, moveLeft, moveRight, pause;
    Resources resources;;
    TileMap map;
    PlayerTest2 playerT;
    int Top, Bottom, Right, Left;

    public Game(){
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        paused = false;
        inputManager= new InputManager(this);
        createGameActions();
        resources = new Resources();
        playerT = resources.getPlayer();
        map = resources.getMap();
        bg = loadImage("Images//Space.jpg");


    }

    public BufferedImage loadImage(String name){
        try {
            f = new File(name);
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            image = ImageIO.read(f);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public synchronized void start(){
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    public synchronized void stop(){
        running = false;
        try {
            thread.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (running){
            render();
        }
        stop();
    }

    public void update(long elapsedTime){
        checkSystemInput();
        if (!isPaused()){
            //inGameLoop();
            checkGameInput();
            //playerT.update(elapsedTime);
            Top = Math.round(playerT.getY() - (playerT.getHeight() + 2));
            Bottom = Math.round(playerT.getY() + (playerT.getHeight() + 2));
            Right = Math.round(playerT.getX() + 100);
            Left = Math.round(playerT.getX() - 100);

            System.out.println("(Game.java)Top:"+ Top + " and Left:" + Left);
            System.out.println("(Game.java)Top:"+Top+ " and Right:" + Right);
            System.out.println("(Game.java)Bottom:"+Bottom+ " and Left:" + Left);
            System.out.println("(Game.java)Bottom:"+Bottom+ " and Right:" + Right);
            if((map.valueAt(Top,Left) == '#') && (map.valueAt(Top,Right) == '#') && (map.valueAt(Bottom,Left) == '#') && (map.valueAt(Bottom, Right) == '#')){
                System.out.println("Floating");
                playerT.setDy(0.1f);
            }else if(map.valueAt(Bottom,Left) != '#' && map.valueAt(Bottom, Right) != '#'){
                System.out.println("Standing on the floor");
                playerT.setDy(0);
                playerT.setFloorY(Bottom - (playerT.getHeight()+2));
                System.out.println(Bottom - (playerT.getHeight() + 2));
                //playerT.setFloorY(310);
            }
            playerT.update(elapsedTime);

        }
    }

    public void render(){
        long startTime = System.currentTimeMillis();
        long currTime = startTime;
        while (true) {
            long elapsedTime = System.currentTimeMillis() - currTime;
            currTime += elapsedTime;
            update(elapsedTime);
            BufferStrategy strategy = getBufferStrategy();
            if (strategy == null) {
                createBufferStrategy(3);
                return;
            }
            Graphics g = strategy.getDrawGraphics();
            g.fillRect(0, 0, getWidth(), getHeight());
            draw(g);
            g.dispose();
            strategy.show();
        }
    }

    public void draw(Graphics g){
        g.drawImage(bg, 0, 0, null);
        g.drawImage(tile, 100, 100, null);
        map.draw(g);
        g.drawImage(playerT.getImage(), Math.round(playerT.getX()), Math.round(playerT.getY()), null);
    }

    public void inGameLoop(){
        //System.out.println("(Game.java)Inside inGameLoop()");
        if (!map.clearBelow(playerT)){
            //System.out.println("(Game.java)Inside inGameLoop()'s loop");
            playerT.stop();
            float velocityX = 0;
            if (moveLeft.isPressed()){
                System.out.println("(Game.java)Inside inGameLoop(), moveLeft pressed");
                if (map.clearLeftOf(playerT)){
                    System.out.println("(Game.java)Inside inGameLoop(), moveLeft pressed, clearLeftOf");
                    velocityX -= PlayerTest2.SPEED;
                    System.out.println("(Game.java)Inside inGameLoop(), moveLeft pressed, Leaving clearLeftOf");
                }
            }
            if (moveRight.isPressed()){
                System.out.println("(Game.java)Inside inGameLoop(), moveRight pressed");
                if (map.clearRightOf(playerT)){
                    System.out.println("(Game.java)Inside inGameLoop(), moveRight pressed, clearRightOf");
                    velocityX += PlayerTest2.SPEED;
                    System.out.println("(Game.java)Inside inGameLoop(), moveRight pressed, Leaving clearRightOf");
                }
            }
            playerT.setDx(velocityX);
            if (jump.isPressed() && playerT.getState() != PlayerTest2.STATE_JUMPING){
                System.out.println("(Game.java)Inside inGameLoop(), jump pressed");
                if (map.clearAbove(playerT)){
                    System.out.println("(Game.java)Inside inGameLoop(), jump pressed, clearAbove");
                    playerT.jump();
                    System.out.println("(Game.java)Inside inGameLoop(), jump pressed, Leaving clearAbove");
                }
            }
        }
        else{
            playerT.applyGravity();
            if (!map.clearLeftOf(playerT)){
                playerT.setDx(0);
            }
            if (!map.clearRightOf(playerT)){
                playerT.setDx(0);
            }
            if (!map.clearAbove(playerT)){
                playerT.setDy(0);
            }
        }
        //System.out.println("(Game.java)Leaving inGameLoop()");
    }

    //------------------------------------------------------------------------------//

    public boolean isPaused(){
        return paused;
    }

    public void setPaused(boolean paused){
        if (this.paused != paused){
            this.paused = paused;
            inputManager.resetAllGameActions();
        }
    }

    public void createGameActions(){
        jump = new GameAction("jump", GameAction.DETECT_INITIAL_PRESS_ONLY);
        exit = new GameAction("exit", GameAction.DETECT_INITIAL_PRESS_ONLY);
        moveLeft = new GameAction("moveLeft");
        moveRight = new GameAction("moveRight");
        pause = new GameAction("pause", GameAction.DETECT_INITIAL_PRESS_ONLY);
        inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
        inputManager.mapToKey(pause, KeyEvent.VK_P);
        inputManager.mapToKey(jump, KeyEvent.VK_SPACE);
        inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
        inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);
    }

    public void checkSystemInput(){
        if (pause.isPressed()){
            setPaused(!isPaused());
        }
        if (exit.isPressed()){
            stop();
        }
    }

    public void checkGameInput(){
        //System.out.println("(Game.java)Inside checkGameInput.java");
        float velocityX = 0;
        if (moveLeft.isPressed()){
            //System.out.println("(Game.java)Inside checkGameInput.java, left was pressed");
            velocityX -= PlayerTest2.SPEED;
        }
        if (moveRight.isPressed()){
            //System.out.println("(Game.java)Inside checkGameInput.java, right was pressed");
            velocityX += PlayerTest2.SPEED;
        }
        playerT.setDx(velocityX);
        if (jump.isPressed() && playerT.getState() != PlayerTest2.STATE_JUMPING){
            //System.out.println("(Game.java)Inside checkGameInput.java, jump was pressed");
            playerT.jump();
        }
        //System.out.println("(Game.java)Leaving checkGameInput.java");
        //System.out.println("The PlayerX is " + (int)playerT.getX()+"\n and PlayerY is " + (int)playerT.getY());
    }

}
