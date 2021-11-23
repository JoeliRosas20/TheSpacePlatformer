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
    Player player;
    Enemy enemy;
    Bullet bullet;
    SimpleSoundPlayer soundPlayer;
    LoopingByteInputStream stream;
    int Top, Bottom, Right, Left, n=1;
    int eTop, eBottom, eRight, eLeft;
    int bTop, bBottom, bLeft, bRight;
    boolean started = true;

    public Game(){
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        paused = false;
        inputManager= new InputManager(this);
        createGameActions();//The commands for the user to move
        resources = new Resources();//Calls the resources
        player = resources.getPlayer();//Gets the player object which originate from resources
        bullet = resources.getBullet();
        map = resources.loadNextMap(1);//Calls the first map
        bg = loadImage("Images//Space.jpg");//Loads the background of the game
        soundPlayer = new SimpleSoundPlayer("Sound//Cyberpunk Moonlight Sonata.wav");
        stream = new LoopingByteInputStream(soundPlayer.getSamples());
    }

    //-----Game Engine-----\\
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
        checkSystemInput();
        if (!isPaused()) {
            spriteSurroundings();
            for (int i = 0; i < map.getSize(); i++){
                enemy = map.getEnemy(i);//Gets the enemy object which originates from TileMap
                enemy.update(elapsedTime);
            }
            checkGameInput();
            player.update(elapsedTime);
            Camera.update(elapsedTime);
            bullet.update(elapsedTime);
            //bullet.update(elapsedTime, Math.round(player.getDx()));
            //System.out.println(player.getX()+" Y = "+player.getY());
//            System.out.println("Velocity "+player.getDy());
        }
    }

    public void render(){
        long currTime = System.currentTimeMillis();
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
        g.drawImage(player.getImage(), Math.round(player.getX()- (int) Camera.x), Math.round(player.getY()+10), null);
        g.drawImage(bullet.getImage(), Math.round(bullet.getX()- (int) Camera.x), Math.round(bullet.getY()), null);
        Camera.draw(g);
    }

    //-----Game Collision and mechanics-----\\
    public void spriteSurroundings(){
        //Create the values for top, bottom, right and left
        Top = Math.round(player.getY());
        Bottom = Math.round(player.getY() + (player.getHeight() + 2));
        Right = Math.round(player.getX() + 99);
        Left = Math.round(player.getX());
        //Bullet x and y
        bLeft = Math.round(bullet.getX());
        bRight = Math.round(bullet.getX() + bullet.getWidth());
        //Creating access to enemy top, bottom, left and right values
        for (int i = 0; i < map.getSize(); i++){
            Enemy enemySprite = map.getEnemy(i);
            //Creating access for enemies' x and y placement
            int enemyX = Math.round(enemySprite.getX());
            int enemyY = Math.round(enemySprite.getY());
            eTop = enemyY;
            eBottom = enemyY + enemySprite.getHeight();
            eLeft = enemyX;
            eRight = enemyX + enemySprite.getWidth();
            checkingEnemyCollision(enemySprite);
            checkingSpriteCollision(enemySprite, enemyX, enemyY);
        }
        checkingPlayerCollision();
    }

    public void loadNextMap(int n){
        map = resources.loadNextMap(n);
        player.setX(0);
        player.setY(0);
        Camera.setX(0);
        Camera.setY(0);
        started = true;
    }

    public void checkingPlayerCollision(){
        //For checking if it is on ground
        boolean notBottomLeftTileEmpty = map.valueAt(Bottom, Left) != '#';//R
        boolean notBottomRightTileEmpty = map.valueAt(Bottom, Right) != '#';//R
        boolean thereIsTileOnBottom = notBottomLeftTileEmpty && notBottomRightTileEmpty;
        //For checking if there is a tile on the right side
        boolean notTopRight = map.valueAt(Top, Right-30) != '#';
        boolean notBottomRight = map.valueAt(Bottom, Right-30) != '#';
        boolean thereIsATileOnRightSide = notTopRight && notBottomRight && map.valueAt(Top, Right-30) != '?';
        //For checking if there is a tile on the left side
        boolean notTopLeft = map.valueAt(Top, Left+20) != '#';
        boolean notBottomLeft = map.valueAt(Bottom, Left+20) != '#';
        boolean thereIsATileOnLeftSide = notTopLeft && notBottomLeft && map.valueAt(Top, Left+20) != '?';
        boolean spaceAllAround = (map.valueAt(Top,Left) == '#') && (map.valueAt(Top,Right) == '#') && (map.valueAt(Bottom,Left) == '#') && (map.valueAt(Bottom, Right) == '#');
        boolean leftIsOutOfBounds = Left <= -20;
        boolean doorIsThere = map.valueAt(Top, Right) == '@';
        if(leftIsOutOfBounds){
            player.setX(-19);
            player.setY(0);
            Camera.setX(0);
            Left = (Left * -1) - 100;
        }

        if(thereIsATileOnRightSide){
            player.setX(Left-1);
            Camera.setX(Left-1);
        }

        if(thereIsATileOnLeftSide){
            player.setX(Left+1);
            Camera.setX(Left+1);
        }

        if(spaceAllAround){
            player.setDy(0.4f);
            player.setDx(0);
            Camera.setDy(0.4f);
            Camera.setDx(0);
        }

        //Once the player is on the ground
        if(thereIsTileOnBottom){
            player.setFloorY(Bottom - (player.getHeight()+2));
            player.setDy(0);
            if (player.getY() > (map.getRow()*100)){//Prevents player from sinking in ground
                player.setFloorY((map.getRow()*100));
            }
        }
        else{
            //When the player is airborne
            player.applyGravity();
            Camera.applyGravity();
        }

        if (doorIsThere){
            n++;
            loadNextMap(n);
        }

    }

    public void checkingEnemyCollision(Sprite enemy){
        //Makes sure the player does not go out of bounds on left side
        if (eLeft <= -20){
            enemy.setDx(0.05f);
            eLeft = (eLeft * -1) - 100;
        }

        //Makes sure the player does not go out of bounds on right side
        if (eRight == map.getTileSize()-10){
            enemy.setDx(-0.05f);
        }

        //Checking the tile collision for the enemy so it can begin walking
        if ((map.valueAt(eTop, eRight) == '?' && map.valueAt(eBottom, eRight) == '?') && (map.valueAt(eTop, eLeft) == '?') && (map.valueAt(eBottom, eLeft) == '?') && started){
            enemy.setDx(0.05f);
        }

        //Once the enemy hits the tile
        if ((map.valueAt(eTop, eRight) == 'R' && map.valueAt(eBottom, eRight) == 'R' && map.valueAt(eTop, eLeft) == '#' && map.valueAt(eBottom, eLeft) == '#')){
            enemy.setDx(-0.05f);
            started = false;
        }
    }

    public void checkingSpriteCollision(Enemy enemy, int x, int y){
        if (bullet.getX()+bullet.getWidth() == x && bullet.getY() - bullet.getWidth()-13 == y){
            map.removeSprite(enemy);
            bullet.setDx(0);
            bullet.setX(-100);
            bullet.setY(-100);
            player.setState(Player.STATE_NORMAL);
        }

        if ((player.getX() + player.getWidth()) >= x){
            //System.out.println("Hit");
/*
            playerT.setX(0);
            playerT.setY(0);
            Camera.setX(0);
            Camera.setY(0);
            */

        }
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
        int x = Math.round(player.getX());
        int y = Math.round(player.getY());
        if (moveLeft.isPressed()){
            velocityX -= Player.SPEED;
        }
        if (moveRight.isPressed()){
            velocityX += Player.SPEED;
        }
        if (shoot.isPressed()){
            player.shoot(bullet, x, y);
            //System.out.println("S was pressed");
        }
        if (jump.isPressed() && player.getState() != Player.STATE_JUMPING){
            player.jump();
        }
        player.setDx(velocityX);
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