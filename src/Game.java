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
    Alien alien;
    Bullet bullet;
    SimpleSoundPlayer soundPlayer;
    LoopingByteInputStream stream;
    int Top, Bottom, Right, Left, n=1;
    int aTop, aBottom, aRight, aLeft;
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
        int n = 0;
        checkSystemInput();
        if (!isPaused()) {
            spriteSurroundings();
            for (int i = 0; i < map.getSize(); i++){
                n++;
                alien = map.getAlien(i);
                alien.check(n);
                alien.update(elapsedTime);
            }
            checkGameInput();
            player.update(elapsedTime);
            Camera.update(elapsedTime);
            bullet.update(elapsedTime);
            if (player.getState() == Player.STATE_DEAD){
                map = resources.reloadMap();
                player.setX(0);
                player.setY(0);
                Camera.setX(0);
                started = true;
            }
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
        //Creating access to enemy top, bottom, left and right values
        for (int i = 0; i < map.getSize(); i++){
            Alien alien = map.getAlien(i);
            //Creating access for enemies' x and y placement
            int alienX = Math.round(alien.getX());
            int alienY = Math.round(alien.getY());
            aTop = alienY;
            aBottom = alienY + alien.getHeight()+5;
            aLeft = alienX;
            aRight = alienX + alien.getWidth();
            checkingEnemyCollision(alien);
            checkingSpriteCollision(alien, alienX, alienY);
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

        boolean spaceAllAround = ((map.valueAt(Top,Left) == '#') && (map.valueAt(Top,Right) == '#') && (map.valueAt(Bottom,Left) == '#') && (map.valueAt(Bottom, Right) == '#') ||
                (map.valueAt(Top,Left) == '?') && (map.valueAt(Top,Right) == '?') && (map.valueAt(Bottom,Left) == '?') && (map.valueAt(Bottom, Right) == '?'));
        boolean leftIsOutOfBounds = Left <= -20;
        boolean doorIsThere = map.valueAt(Top, Right) == '@';
        boolean acid = map.valueAt(Bottom, Left) == 'A' && map.valueAt(Bottom, Right) == 'A';

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
            if (player.getY() > (map.getRow()*100)){//Puts the player properly on top
                System.out.println("True");
                player.setFloorY((map.getRow()*100));
            }
        }
        else{
            //When the player is airborne
            player.applyGravity();
            Camera.applyGravity();
        }

        if (acid){
            player.setState(Player.STATE_DYING);
        }

        if (doorIsThere){
            n++;
            loadNextMap(n);
        }

    }

    public void checkingEnemyCollision(Sprite alien){
        boolean tileRightOfAlien = (map.valueAt(aTop, aRight) == 'N'||map.valueAt(aTop, aRight) == 'O' || map.valueAt(aTop, aRight) == 'Q' || map.valueAt(aTop, aRight) == 'R') && map.valueAt(aBottom, aRight) == 'R';
        boolean tileRightOfAlienLeft = (map.valueAt(aTop, aLeft) == '#' || map.valueAt(aTop, aLeft) == '?') && (map.valueAt(aBottom, aLeft) == 'R' || map.valueAt(aBottom, aLeft) == 'A');
        boolean tileLeftOfAlien = (map.valueAt(aTop, aLeft) == 'O' || map.valueAt(aTop, aLeft) == 'P' ||map.valueAt(aTop, aLeft) == 'R' || map.valueAt(aTop, aLeft) == 'S') && map.valueAt(aBottom, aLeft) == 'R';
        boolean tileLeftOfAlienRight = (map.valueAt(aTop, aRight) == '#' || map.valueAt(aTop, aRight) == '?') && (map.valueAt(aBottom, aRight) == 'R' || map.valueAt(aBottom, aRight) == 'A');

        //Makes sure the player does not go out of bounds on left side
        if (aLeft <= -20){
            alien.setDx(0.05f);
            aLeft = (aLeft * -1) - 100;
        }

        //Makes sure the player does not go out of bounds on right side
        if (aRight == map.getTileSize()-10){
            alien.setDx(-0.05f);
        }

        //Checking the tile collision for the enemy so it can begin walking
        if ((map.valueAt(aTop, aRight) == '?' && map.valueAt(aBottom, aRight) == 'R') && (map.valueAt(aTop, aLeft) == '?') && (map.valueAt(aBottom, aLeft) == 'R') && started){
            alien.setDx(-0.05f);
        }

        //Once the enemy hits the tile on right side
        if ((tileRightOfAlien && tileRightOfAlienLeft)){
            alien.setDx(-0.05f);
            started = false;
        }

        //Once the enemy hits the tile on right side
        if (tileLeftOfAlien && tileLeftOfAlienRight){
            alien.setDx(0.05f);
            started = false;
        }

        //For aliens that are on upper platform
        if (map.valueAt(aBottom, aLeft) == '#' && map.valueAt(aTop, aLeft) == '#'){
            alien.setDx(0.05f);
            started = false;
        }
        if (map.valueAt(aBottom, aRight) == '#' && map.valueAt(aTop, aRight) == '#'){
            alien.setDx(-0.05f);
        }
    }

    public void checkingSpriteCollision(Alien alien, int x, int y){
        int playerX = Math.round(player.getX());
        int playerY = Math.round(player.getY());
        boolean bulletHitAlien;
        boolean playerHitAlien;
        if (bullet.getX()+bullet.getWidth() == x && bullet.getY() - bullet.getWidth()-13 == y){
            map.removeAlien(alien);
            bullet.setDx(0);
            player.setState(Player.STATE_NORMAL);
            bullet.setFace(Bullet.HIT);
            bullet.setX(bullet.getX()+50);
            bullet.setY(bullet.getY()-45);
        }
        if (playerX+60 == x && playerY == y) {
            player.setState(Player.STATE_DYING);
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
        int dx = Math.round(player.getDx());
        if (moveLeft.isPressed() && player.isAlive()){
            velocityX -= Player.SPEED;
        }
        if (moveRight.isPressed() && player.isAlive()){
            velocityX += Player.SPEED;
        }
        if (shoot.isPressed()){
            player.shoot(bullet, x, y, dx);
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