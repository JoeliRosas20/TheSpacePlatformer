import Engine.*;
import Input.*;
import TileMap.TileMap;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
//TODO Start creating the enemy class and add it to the game
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
    Resources resources;
    TileMap map;
    PlayerTest2 playerT;
    int Top, Bottom, Right, Left;
    int check = 0;
    Enemy enemy;

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
        enemy = resources.getEnemy();
        Top = Math.round(playerT.getY() - 1);
        Bottom = Math.round(playerT.getY() + (playerT.getHeight() + 2));
        Right = Math.round(playerT.getX() + 1);
        Left = Math.round(playerT.getX() - 1);
        System.out.println("Top: "+Top+", Bottom: "+Bottom+", Left: "+Left+", Right: "+Right);
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
            checkingCollision();
            checkGameInput();
            playerT.update(elapsedTime);
            System.out.println("Get Y:"+playerT.getY()+" and Get X:"+playerT.getX());
            System.out.println(map.valueAt(Math.round(playerT.getY()), Math.round(playerT.getX())));
            enemy.update(elapsedTime);
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
        g.setColor(Color.GREEN);
        map.draw(g);
        g.drawImage(playerT.getImage(), Math.round(playerT.getX()), Math.round(playerT.getY()+10), null);
        g.setColor(Color.red);
        g.drawRect(Math.round(playerT.getX()), Math.round(playerT.getY()+10), playerT.getWidth(), playerT.getHeight());
        g.drawImage(enemy.getImage(), 0, 0, null);
    }

    public void checkingCollision(){
        //Create the values for top, bottom, right and left
        Top = Math.round(playerT.getY());
        Bottom = Math.round(playerT.getY() + (playerT.getHeight() + 2));
        Right = Math.round(playerT.getX() + 99);
        Left = Math.round(playerT.getX());
        //System.out.println("Top: "+Top+", Bottom: "+Bottom+", Left: "+Left+", Right: "+Right);

        boolean notTopRightTile = map.valueAt(Top, Right) != '#';
        boolean notBottomRightTile = map.valueAt(Bottom, Right) != '#';
        boolean notTopLeftTile = map.valueAt(Top, Left) != '#';
        boolean notBottomLeftTile = map.valueAt(Bottom, Left) != '#';

        //For checking if there is a tile on the right side
        boolean notTopRight = map.valueAt(Top, Right-30) != '#';
        boolean notBottomRight = map.valueAt(Bottom, Right-30) != '#';
        boolean thereIsATileOnRight = notTopRight & notBottomRight;

        //For checking if there is a tile on the left side
        boolean notTopLeft = map.valueAt(Top, Left+20) != '#';
        boolean notBottomLeft = map.valueAt(Bottom, Left+20) != '#';
        boolean thereIsATileOnLeft = notTopLeft && notBottomLeft;

        //Makes sure the player does not go out of bounds on left side
        if (Left <= -20){
            playerT.setX(-19);
            playerT.setY(0);
            Left = (Left * -1) - 100;
        }

        //Attempt to check the right side of player if it is a tile
        if(thereIsATileOnRight){
            playerT.setX(Left-1);
        }

        //Attempt to check the left side of player if it is a tile
        if (thereIsATileOnLeft){
            playerT.setX(Left+1);
        }

        //When the player is floating in the air at the start of level
        if((map.valueAt(Top,Left) == '#') && (map.valueAt(Top,Right) == '#') && (map.valueAt(Bottom,Left) == '#') && (map.valueAt(Bottom, Right) == '#')){
            playerT.setDy(0.4f);
            playerT.setDx(0);
        }

        //Once the player is on the ground
        if(notBottomLeftTile && notBottomRightTile){
            playerT.setFloorY(Bottom - (playerT.getHeight()+2));
            //This loop is for the player jump
            if (map.valueAt(Top-100, Right) == '#' && map.valueAt(Top-100, Left) == '#' && jump.isPressed() && playerT.getState() != PlayerTest2.STATE_JUMPING){
                playerT.jump();
                System.out.println(map.valueAt(Math.round(playerT.getY()), Math.round(playerT.getX())));
                System.out.println("Top Right:"+map.valueAt(Top-100, Right));
                System.out.println("Top Left:"+map.valueAt(Top-100, Left));
                System.out.println("Bottom Right:"+map.valueAt(Bottom, Right));
                System.out.println("Bottom Left:"+map.valueAt(Bottom, Left));
                System.out.println("Player Right:"+map.valueAt(Math.round(playerT.getY()), Right));
                System.out.println("Player y velocity:"+playerT.getDy());
                check++;
                System.out.println(check+"\n");
            }
        }
        else{
            //When the player is airborne
            playerT.applyGravity();
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
        playerT.setDx(velocityX);
    }

}