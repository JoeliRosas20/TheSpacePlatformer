import Engine.*;
import Input.*;
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
    BufferedImage image, bg;
    File f;
    Animation animation, animation2;
    private boolean paused;
    protected InputManager inputManager;
    protected GameAction jump, exit, moveLeft, moveRight, pause;
    PlayerTest2 playerTest2;

    public Game(){
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        paused = false;
        inputManager= new InputManager(this);
        createGameActions();
        loadImages();
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

    public void loadImages(){
        bg = loadImage("Images//Space.jpg");
        BufferedImage player = loadImage("Images//Idle (1).png");
        BufferedImage player2 = loadImage("Images//Idle (2).png");
        BufferedImage player3 = loadImage("Images//Idle (3).png");
        BufferedImage player4 = loadImage("Images//Idle (4).png");
        BufferedImage player5 = loadImage("Images//Idle (5).png");
        BufferedImage player6 = loadImage("Images//Idle (6).png");
        BufferedImage player7 = loadImage("Images//Idle (7).png");
        BufferedImage player8 = loadImage("Images//Idle (8).png");
        BufferedImage player9 = loadImage("Images//Idle (9).png");
        BufferedImage player10 = loadImage("Images//Idle (10).png");
        BufferedImage player11 = loadImage("Images//Jump (1).png");
        BufferedImage player12 = loadImage("Images//Jump (2).png");
        BufferedImage player13 = loadImage("Images//Jump (3).png");
        BufferedImage player14 = loadImage("Images//Jump (4).png");
        BufferedImage player15 = loadImage("Images//Jump (5).png");
        BufferedImage player16 = loadImage("Images//Jump (6).png");
        BufferedImage player17 = loadImage("Images//Jump (7).png");
        BufferedImage player18 = loadImage("Images//Jump (8).png");
        BufferedImage player19 = loadImage("Images//Jump (9).png");
        BufferedImage player20 = loadImage("Images//Jump (10).png");
        animation = new Animation();
        animation2 = new Animation();
        animation.addFrame(player, 100);
        animation.addFrame(player2, 100);
        animation.addFrame(player3, 100);
        animation.addFrame(player4, 100);
        animation.addFrame(player5, 100);
        animation.addFrame(player6, 100);
        animation.addFrame(player7, 100);
        animation.addFrame(player8, 100);
        animation.addFrame(player9, 100);
        animation.addFrame(player10, 100);
        animation2.addFrame(player11, 100);
        animation2.addFrame(player12, 100);
        animation2.addFrame(player13, 100);
        animation2.addFrame(player14, 100);
        animation2.addFrame(player15, 100);
        animation2.addFrame(player16, 100);
        animation2.addFrame(player17, 100);
        animation2.addFrame(player18, 100);
        animation2.addFrame(player19, 100);
        animation2.addFrame(player20, 100);
        playerTest2 = new PlayerTest2(animation, animation2);
        System.out.println(height * scale);
        playerTest2.setFloorY((height * scale) - playerTest2.getHeight());
        System.out.println(playerTest2.getHeight());
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
            checkGameInput();
            playerTest2.update(elapsedTime);
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
            g.drawImage(bg, 0, 0, null);
            g.drawImage(playerTest2.getImage(), Math.round(playerTest2.getX()), Math.round(playerTest2.getY()), null);
            g.dispose();
            strategy.show();
        }
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
        float velocityX = 0;
        if (moveLeft.isPressed()){
            velocityX -= PlayerTest2.SPEED;
        }
        if (moveRight.isPressed()){
            velocityX += PlayerTest2.SPEED;
        }
        playerTest2.setDx(velocityX);
        if (jump.isPressed() && playerTest2.getState() != PlayerTest2.STATE_JUMPING){
            playerTest2.jump();
        }

    }

}
