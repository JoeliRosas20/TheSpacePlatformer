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
    private BufferedImage image, bg;
    private File f;
    private boolean paused;
    protected InputManager inputManager;
    protected GameAction jump, exit, moveLeft, moveRight, pause;
    Resources resources;
    TileMap map;
    PlayerTest2 playerT;
    Enemy enemy;
    int Top, Bottom, Right, Left;
    int eTop, eBottom, eRight, eLeft;

    public Game(){
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        paused = false;
        inputManager= new InputManager(this);
        createGameActions();//The commands for the user to move
        resources = new Resources();//Calls the resources
        playerT = resources.getPlayer();//Gets the player object which originate from resources
        map = resources.getMap();//Get the map object with originates from resources
        bg = loadImage("Images//Space.jpg");//Loads the background of the game
    }

    //-----The methods that are the game engine-----\\

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

    public void update(long elapsedTime) {
        for (int i = 0; i < map.getSize(); i++) {
            enemy = map.getEnemy(i);//Gets the enemy object which originates from TileMap
            checkSystemInput();
            if (!isPaused()) {
                checkingPlayerCollision();
                checkingEnemyCollision();
                checkGameInput();
                playerT.update(elapsedTime);
                enemy.update(elapsedTime);
            }
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
        g.setColor(Color.GREEN);
        map.draw(g);
        g.drawImage(playerT.getImage(), Math.round(playerT.getX()), Math.round(playerT.getY()+10), null);
        //g.setColor(Color.red);
        //g.drawRect(Math.round(playerT.getX()), Math.round(playerT.getY()+10), playerT.getWidth(), playerT.getHeight());
    }

    //-----Game Collision-----\\

    public void checkingPlayerCollision(){
        //Create the values for top, bottom, right and left
        Top = Math.round(playerT.getY());
        Bottom = Math.round(playerT.getY() + (playerT.getHeight() + 2));
        Right = Math.round(playerT.getX() + 99);
        Left = Math.round(playerT.getX());

        //For checking if it has space all around
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

        //Attempt to check the Right side of player if it is a tile
        if(thereIsATileOnRight){
            playerT.setX(Left-1);
        }

        //Attempt to check the Left side of player if it is a tile
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
                //----------------------------------------------------------------------------\\

                //----------------------------------------------------------------------------\\
            }
            if ((map.valueAt(Top, Right) == '@') || map.valueAt(Bottom, Right) == '@'){
                System.out.println("Hit");
            }
        }
        else{
            //When the player is airborne
            playerT.applyGravity();
        }

    }

    public void checkingEnemyCollision(){
        //This loop will access the LinkedList which has the enemies
        for (int i = 0; i < map.getSize(); i++){
            //Creating access for enemies' x and y
            int enemyX = Math.round(map.getEnemy(i).getX());
            int enemyY = Math.round(map.getEnemy(i).getY());

            //Creating access to enemy top, bottom, left and right values
            eTop = enemyY;
            eBottom = enemyY + map.getEnemy(i).getHeight();
            eLeft = enemyX;
            eRight = enemyX + map.getEnemy(i).getWidth();

            //Checking the tile collision for the enemy
            if (map.valueAt(eTop, eRight) == '@' && map.valueAt(eBottom, eRight) == '@'){
                map.getEnemy(i).move();
            }

            if (map.valueAt(eTop, eRight) == 'R' && map.valueAt(eBottom, eRight) == 'R'){
                map.getEnemy(i).setX(eLeft);
            }
        }
    }

    public void checkingSpriteCollision(){ }

    //-----The Game Controls-----\\

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

    //-----The Helper Method-----\\

    private BufferedImage loadImage(String name){
        try {
            f = new File(name);
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            image = ImageIO.read(f);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

}