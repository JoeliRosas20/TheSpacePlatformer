import Characters.*;
import Engine.*;
import Input.*;
import Sound.LoopingByteInputStream;
import Sound.SimpleSoundPlayer;
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
    protected GameAction jump, exit, moveLeft, moveRight, pause, shoot;
    Resources resources;
    TileMap map;
    PlayerTest2 playerT;
    Enemy enemy;
    Bullet bullet;
    SimpleSoundPlayer soundPlayer;
    LoopingByteInputStream stream;
    int Top, Bottom, Right, Left;
    int eTop, eBottom, eRight, eLeft;
    boolean started = true;

    public Game(){
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        paused = false;
        inputManager= new InputManager(this);
        createGameActions();//The commands for the user to move
        resources = new Resources();//Calls the resources
        playerT = resources.getPlayer();//Gets the player object which originate from resources
        bullet = resources.getBullet();
        map = resources.getMap();//Get the map object with originates from resources
        //map = resources.loadNextMap();
        bg = loadImage("Images//Space.jpg");//Loads the background of the game
        soundPlayer = new SimpleSoundPlayer("Sound//Cyberpunk Moonlight Sonata.wav");
        stream = new LoopingByteInputStream(soundPlayer.getSamples());
    }

    //-----The methods that are the game engine-----\\

    public synchronized void start(){
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
        soundPlayer.play(stream);
    }

    public synchronized void stop(){
        running = false;
        try {
            thread.join();
            stream.close();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        catch (IOException e){
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
                Camera.update(elapsedTime);
                enemy.update(elapsedTime);
                bullet.update(elapsedTime);
                System.out.println(bullet.getX());
                System.out.println(bullet.getY());
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

    public TileMap getMap(){
        return map;
    }

    public void draw(Graphics g){
        g.drawImage(bg, 0, 0, null);
        g.setColor(Color.GREEN);
        map.draw(g);
        g.drawImage(playerT.getImage(), Math.round(playerT.getX()- (int) Camera.x), Math.round(playerT.getY()+10), null);
        //g.drawImage(bullet.getImage(), Math.round(bullet.getX()- (int) Camera.x), Math.round(bullet.getY()), null);
        g.drawImage(bullet.getImage(), Math.round(bullet.getX()), Math.round(bullet.getY()), null);
        Camera.draw(g);
    }

    //-----Game Collision and mechanics-----\\

    public void checkingPlayerCollision(){
        //Create the values for top, bottom, right and left
        Top = Math.round(playerT.getY());
        Bottom = Math.round(playerT.getY() + (playerT.getHeight() + 2));
        Right = Math.round(playerT.getX() + 99);
        Left = Math.round(playerT.getX());

        //For checking if it is on ground
        boolean notBottomRightTileEmpty= map.valueAt(Bottom, Right) != '#';
        boolean notBottomLeftTileEmpty = map.valueAt(Bottom, Left) != '#';

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
            Camera.setX(0);
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
            Camera.setDy(0.4f);
            Camera.setDx(0);
        }

        //Once the player is on the ground
        if(notBottomLeftTileEmpty && notBottomRightTileEmpty){
            playerT.setFloorY(Bottom - (playerT.getHeight()+2));
            Camera.setY(Bottom - (playerT.getHeight()+2));
            //This loop is for the player jump
            if (map.valueAt(Top-100, Right) == '#' && map.valueAt(Top-100, Left) == '#' && jump.isPressed() && playerT.getState() != PlayerTest2.STATE_JUMPING){
                playerT.jump();
                Camera.jump();
            }
            if ((map.valueAt(Top, Right) == '@') || map.valueAt(Bottom, Right) == '@'){
                //Something
            }
        }
        else{
            //When the player is airborne
            playerT.applyGravity();
            Camera.applyGravity();
        }

        if (map.valueAt(Top, Right) == '@'){
            resources.loadNextMap();
            System.out.println("Something");
        }

    }

    public void checkingEnemyCollision(){
        //This loop will access the LinkedList which has the enemies
        for (int i = 0; i < map.getSize(); i++){
            //To access the enemy sprite easily
            Sprite enemySprite = map.getEnemy(i);

            //Creating access for enemies' x and y placement
            int enemyX = Math.round(enemySprite.getX());
            int enemyY = Math.round(enemySprite.getY());

            //Creating access to enemy top, bottom, left and right values
            eTop = enemyY;
            eBottom = enemyY + enemySprite.getHeight();
            eLeft = enemyX;
            eRight = enemyX + enemySprite.getWidth();

            //Makes sure the player does not go out of bounds on left side
            if (eLeft <= -20){
                enemySprite.setDx(0.05f);
                eLeft = (eLeft * -1) - 100;
            }

            //Checking the tile collision for the enemy so it can begin walking
            if ((map.valueAt(eTop, eRight) == '?' && map.valueAt(eBottom, eRight) == '?') && (map.valueAt(eTop, eLeft) == '?') && (map.valueAt(eBottom, eLeft) == '?') && started){
                enemySprite.setDx(0.05f);
            }

            //Once the enemy hits the tile
            if ((map.valueAt(eTop, eRight) == 'R' && map.valueAt(eBottom, eRight) == 'R' && map.valueAt(eTop, eLeft) == '#' && map.valueAt(eBottom, eLeft) == '#')){
                enemySprite.setDx(-0.05f);
                started = false;
            }

        }
    }

    public void checkingSpriteCollision(){ }

    public void launch(Bullet bullet){
        bullet.setX(Math.round(playerT.getX()));
        bullet.setDx(0.5f);
    }

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
        shoot = new GameAction("shoot", GameAction.DETECT_INITIAL_PRESS_ONLY);
        inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
        inputManager.mapToKey(pause, KeyEvent.VK_P);
        inputManager.mapToKey(jump, KeyEvent.VK_SPACE);
        inputManager.mapToKey(moveLeft, KeyEvent.VK_LEFT);
        inputManager.mapToKey(moveRight, KeyEvent.VK_RIGHT);
        inputManager.mapToKey(shoot, KeyEvent.VK_S);
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
        if (shoot.isPressed()){
            System.out.println("Shoot was pressed!");
            //bullet.setDx(1);
            playerT.shoot(bullet, Math.round(playerT.getX()), Math.round(playerT.getY()));
            //playerT.launch(bullet);
            System.out.println(bullet.getDx());
            System.out.println(bullet.getX());
        }
        playerT.setDx(velocityX);
        Camera.setDx(velocityX);
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