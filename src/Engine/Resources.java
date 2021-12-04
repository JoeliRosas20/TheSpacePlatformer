package Engine;

import Characters.*;
import TileMap.TileMap;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Resources {

    public static int width = 300;
    public static int height = width / 16 * 9;
    private BufferedImage image;
    private File f;
    int curr = 1;
    Player player;
    Bullet bullet;
    TileMap map;

    public Resources(){
        loadPlayerImages();
        loadBulletImages();
    }

    //-----Accessors-----\\
    public Player getPlayer(){
        return player;
    }

    public Bullet getBullet(){
        return bullet;
    }

    public TileMap loadNextMap(int n){
        if (curr != n){
            curr = n;
        }
        map = new TileMap("Maps//world"+curr);
        return map;
    }

    public TileMap reloadMap(){
        map = new TileMap("Maps//world"+curr);
        player.setState(Player.STATE_NORMAL);
        return map;
    }

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

    //-----Loading the images-----\\
    /**
     * Loads the images of the player onto the game
     */
    public void loadPlayerImages(){
        //Load Idle, Jump, Walk Right, Walk Left, and Die
        BufferedImage[][] images = new BufferedImage[10][];
        images[0] = new BufferedImage[]{
                loadImage("Images//PlayerImages//IdleL (1).png"),
                loadImage("Images//PlayerImages//IdleL (2).png"),
                loadImage("Images//PlayerImages//IdleL (3).png"),
                loadImage("Images//PlayerImages//IdleL (4).png"),
                loadImage("Images//PlayerImages//IdleL (5).png"),
                loadImage("Images//PlayerImages//IdleL (6).png"),
                loadImage("Images//PlayerImages//IdleL (7).png"),
                loadImage("Images//PlayerImages//IdleL (8).png"),
                loadImage("Images//PlayerImages//IdleL (9).png"),
                loadImage("Images//PlayerImages//IdleL (10).png")
        };
        images[1] = new BufferedImage[]{
                loadImage("Images//PlayerImages//IdleR (1).png"),
                loadImage("Images//PlayerImages//IdleR (2).png"),
                loadImage("Images//PlayerImages//IdleR (3).png"),
                loadImage("Images//PlayerImages//IdleR (4).png"),
                loadImage("Images//PlayerImages//IdleR (5).png"),
                loadImage("Images//PlayerImages//IdleR (6).png"),
                loadImage("Images//PlayerImages//IdleR (7).png"),
                loadImage("Images//PlayerImages//IdleR (8).png"),
                loadImage("Images//PlayerImages//IdleR (9).png"),
                loadImage("Images//PlayerImages//IdleR (10).png")
        };
        images[2] = new BufferedImage[]{
                loadImage("Images//PlayerImages//JumpL (1).png"),
                loadImage("Images//PlayerImages//JumpL (2).png"),
                loadImage("Images//PlayerImages//JumpL (3).png"),
                loadImage("Images//PlayerImages//JumpL (4).png"),
                loadImage("Images//PlayerImages//JumpL (5).png"),
                loadImage("Images//PlayerImages//JumpL (6).png"),
                loadImage("Images//PlayerImages//JumpL (7).png"),
                loadImage("Images//PlayerImages//JumpL (8).png"),
                loadImage("Images//PlayerImages//JumpL (9).png"),
                loadImage("Images//PlayerImages//JumpL (10).png")
        };
        images[3] = new BufferedImage[]{
                loadImage("Images//PlayerImages//JumpR (1).png"),
                loadImage("Images//PlayerImages//JumpR (2).png"),
                loadImage("Images//PlayerImages//JumpR (3).png"),
                loadImage("Images//PlayerImages//JumpR (4).png"),
                loadImage("Images//PlayerImages//JumpR (5).png"),
                loadImage("Images//PlayerImages//JumpR (6).png"),
                loadImage("Images//PlayerImages//JumpR (7).png"),
                loadImage("Images//PlayerImages//JumpR (8).png"),
                loadImage("Images//PlayerImages//JumpR (9).png"),
                loadImage("Images//PlayerImages//JumpR (10).png")
        };
        images[4] = new BufferedImage[]{
                loadImage("Images//PlayerImages//RunL (1).png"),
                loadImage("Images//PlayerImages//RunL (2).png"),
                loadImage("Images//PlayerImages//RunL (3).png"),
                loadImage("Images//PlayerImages//RunL (4).png"),
                loadImage("Images//PlayerImages//RunL (5).png"),
                loadImage("Images//PlayerImages//RunL (6).png"),
                loadImage("Images//PlayerImages//RunL (7).png"),
                loadImage("Images//PlayerImages//RunL (8).png")
        };
        images[5] = new BufferedImage[]{
                loadImage("Images//PlayerImages//RunR (1).png"),
                loadImage("Images//PlayerImages//RunR (2).png"),
                loadImage("Images//PlayerImages//RunR (3).png"),
                loadImage("Images//PlayerImages//RunR (4).png"),
                loadImage("Images//PlayerImages//RunR (5).png"),
                loadImage("Images//PlayerImages//RunR (6).png"),
                loadImage("Images//PlayerImages//RunR (7).png"),
                loadImage("Images//PlayerImages//RunR (8).png")
        };
        images[6] = new BufferedImage[]{
                loadImage("Images//PlayerImages//ShootL (1).png"),
                loadImage("Images//PlayerImages//ShootL (2).png"),
                loadImage("Images//PlayerImages//ShootL (3).png"),
                loadImage("Images//PlayerImages//ShootL (4).png")
        };
        images[7] = new BufferedImage[]{
                loadImage("Images//PlayerImages//ShootR (1).png"),
                loadImage("Images//PlayerImages//ShootR (2).png"),
                loadImage("Images//PlayerImages//ShootR (3).png"),
                loadImage("Images//PlayerImages//ShootR (4).png")
        };
        images[8] = new BufferedImage[]{
                loadImage("Images//PlayerImages//DeadL (1).png"),
                loadImage("Images//PlayerImages//DeadL (2).png"),
                loadImage("Images//PlayerImages//DeadL (3).png"),
                loadImage("Images//PlayerImages//DeadL (4).png"),
                loadImage("Images//PlayerImages//DeadL (5).png"),
                loadImage("Images//PlayerImages//DeadL (6).png"),
                loadImage("Images//PlayerImages//DeadL (7).png"),
                loadImage("Images//PlayerImages//DeadL (8).png"),
                loadImage("Images//PlayerImages//DeadL (9).png"),
                loadImage("Images//PlayerImages//DeadL (10).png")
        };
        images[9] = new BufferedImage[]{
                loadImage("Images//PlayerImages//DeadR (1).png"),
                loadImage("Images//PlayerImages//DeadR (2).png"),
                loadImage("Images//PlayerImages//DeadR (3).png"),
                loadImage("Images//PlayerImages//DeadR (4).png"),
                loadImage("Images//PlayerImages//DeadR (5).png"),
                loadImage("Images//PlayerImages//DeadR (6).png"),
                loadImage("Images//PlayerImages//DeadR (7).png"),
                loadImage("Images//PlayerImages//DeadR (8).png"),
                loadImage("Images//PlayerImages//DeadR (9).png"),
                loadImage("Images//PlayerImages//DeadR (10).png")
        };
        //Create Player Animations
        Animation[] playerAnim = new Animation[10];
        //idleL
        playerAnim[0] = createPlayerIdleLAnim(images[0][0], images[0][1], images[0][2], images[0][3], images[0][4],
                    images[0][5], images[0][6], images[0][7], images[0][8], images[0][9]);
        //idleR
        playerAnim[1] = createPlayerIdleRAnim(images[1][0], images[1][1], images[1][2], images[1][3], images[1][4],
                images[1][5], images[1][6], images[1][7], images[1][8], images[1][9]);
        //jumpL
        playerAnim[2] = createPlayerJumpLAnim(images[2][0], images[2][1], images[2][2], images[2][3], images[2][4],
                images[2][5], images[2][6], images[2][7], images[2][8], images[2][9]);
        //jumpR
        playerAnim[3] = createPlayerJumpRAnim(images[3][0], images[3][1], images[3][2], images[3][3], images[3][4],
                images[3][5], images[3][6], images[3][7], images[3][8], images[3][9]);
        //walkL
        playerAnim[4] = createPlayerWalkLAnim(images[4][0], images[4][1], images[4][2], images[4][3], images[4][4],
                images[4][5], images[4][6], images[4][7]);
        //walkR
        playerAnim[5] = createPlayerWalkRAnim(images[5][0], images[5][1], images[5][2], images[5][3], images[5][4],
                images[5][5], images[5][6], images[5][7]);
        //shootL
        playerAnim[6] = createPlayerShootLAnim(images[6][0], images[6][1], images[6][2], images[6][3]);
        //shootR
        playerAnim[7] = createPlayerShootRAnim(images[7][0], images[7][1], images[7][2], images[7][3]);
        //deadL
        playerAnim[8] = createPlayerDeadLAnim(images[8][0], images[8][1], images[8][2], images[8][3], images[8][4],
                images[8][5], images[8][6], images[8][7], images[8][8], images[8][9]);
        //deadR
        playerAnim[9] = createPlayerDeadRAnim(images[9][0], images[9][1], images[9][2], images[9][3], images[9][4],
                images[9][5], images[9][6], images[9][7], images[9][8], images[9][9]);
        //Create Player Sprites
        player = new Player(playerAnim[0], playerAnim[1], playerAnim[2], playerAnim[3], playerAnim[4],
                playerAnim[5], playerAnim[6], playerAnim[7], playerAnim[8], playerAnim[9]);
    }

    public void loadBulletImages(){
        BufferedImage[][] images = new BufferedImage[3][];
        images[0] = new BufferedImage[]{
                loadImage("Images//BulletImages//LBullet_000.png"),
                loadImage("Images//BulletImages//LBullet_001.png"),
                loadImage("Images//BulletImages//LBullet_002.png"),
                loadImage("Images//BulletImages//LBullet_003.png"),
                loadImage("Images//BulletImages//LBullet_004.png")
        };
        images[1] = new BufferedImage[]{
                loadImage("Images//BulletImages//Bullet_000.png"),
                loadImage("Images//BulletImages//Bullet_001.png"),
                loadImage("Images//BulletImages//Bullet_002.png"),
                loadImage("Images//BulletImages//Bullet_003.png"),
                loadImage("Images//BulletImages//Bullet_004.png")
        };
        images[2] = new BufferedImage[]{
                loadImage("Images//BulletImages//Muzzle_000.png"),
                loadImage("Images//BulletImages//Muzzle_001.png"),
                loadImage("Images//BulletImages//Muzzle_002.png"),
                loadImage("Images//BulletImages//Muzzle_003.png"),
                loadImage("Images//BulletImages//Muzzle_004.png"),
        };
        Animation[] bulletAnim = new Animation[3];
        bulletAnim[0] = createBulletLeftAnim(images[0][0], images[0][1], images[0][2], images[0][3], images[0][4]);
        bulletAnim[1] = createBulletRightAnim(images[1][0], images[1][1], images[1][2], images[1][3], images[1][4]);
        bulletAnim[2] = createBulletMuzzleAnim(images[2][0], images[2][1], images[2][2], images[2][3], images[2][4]);
        bullet = new Bullet(bulletAnim[0], bulletAnim[1], bulletAnim[2]);
        bullet.setX(-100);
        bullet.setY(-100);
    }

    private Animation createPlayerIdleLAnim(BufferedImage player1, BufferedImage player2, BufferedImage player3,
                                       BufferedImage player4, BufferedImage player5, BufferedImage player6,
                                       BufferedImage player7, BufferedImage player8, BufferedImage player9,
                                       BufferedImage player10) {
        Animation animation = new Animation();
        animation.addFrame(player1, 150);
        animation.addFrame(player2, 150);
        animation.addFrame(player3, 150);
        animation.addFrame(player4, 150);
        animation.addFrame(player5, 150);
        animation.addFrame(player6, 150);
        animation.addFrame(player7, 150);
        animation.addFrame(player8, 150);
        animation.addFrame(player9, 150);
        animation.addFrame(player10, 150);
        return animation;
    }

    private Animation createPlayerIdleRAnim(BufferedImage player1, BufferedImage player2, BufferedImage player3,
                                            BufferedImage player4, BufferedImage player5, BufferedImage player6,
                                            BufferedImage player7, BufferedImage player8, BufferedImage player9,
                                            BufferedImage player10) {
        Animation animation = new Animation();
        animation.addFrame(player1, 200);
        animation.addFrame(player2, 200);
        animation.addFrame(player3, 200);
        animation.addFrame(player4, 200);
        animation.addFrame(player5, 200);
        animation.addFrame(player6, 200);
        animation.addFrame(player7, 200);
        animation.addFrame(player8, 200);
        animation.addFrame(player9, 200);
        animation.addFrame(player10, 200);
        return animation;
    }

    private Animation createPlayerJumpLAnim(BufferedImage player1, BufferedImage player2, BufferedImage player3,
                                            BufferedImage player4, BufferedImage player5, BufferedImage player6,
                                            BufferedImage player7, BufferedImage player8, BufferedImage player9,
                                            BufferedImage player10) {
        Animation animation = new Animation();
        animation.addFrame(player1, 100);
        animation.addFrame(player2, 100);
        animation.addFrame(player3, 100);
        animation.addFrame(player4, 100);
        animation.addFrame(player5, 100);
        animation.addFrame(player6, 100);
        animation.addFrame(player7, 100);
        animation.addFrame(player8, 100);
        animation.addFrame(player9, 100);
        animation.addFrame(player10, 100);
        return animation;
    }

    private Animation createPlayerJumpRAnim(BufferedImage player1, BufferedImage player2, BufferedImage player3,
                                            BufferedImage player4, BufferedImage player5, BufferedImage player6,
                                            BufferedImage player7, BufferedImage player8, BufferedImage player9,
                                            BufferedImage player10) {
        Animation animation = new Animation();
        animation.addFrame(player1, 100);
        animation.addFrame(player2, 100);
        animation.addFrame(player3, 100);
        animation.addFrame(player4, 100);
        animation.addFrame(player5, 100);
        animation.addFrame(player6, 100);
        animation.addFrame(player7, 100);
        animation.addFrame(player8, 100);
        animation.addFrame(player9, 100);
        animation.addFrame(player10, 100);

        return animation;
    }

    private Animation createPlayerWalkLAnim(BufferedImage player1, BufferedImage player2, BufferedImage player3,
                                            BufferedImage player4, BufferedImage player5, BufferedImage player6,
                                            BufferedImage player7, BufferedImage player8) {
        Animation animation = new Animation();
        animation.addFrame(player1, 100);
        animation.addFrame(player2, 100);
        animation.addFrame(player3, 100);
        animation.addFrame(player4, 100);
        animation.addFrame(player5, 100);
        animation.addFrame(player6, 100);
        animation.addFrame(player7, 100);
        animation.addFrame(player8, 100);
        return animation;
    }

    private Animation createPlayerWalkRAnim(BufferedImage player1, BufferedImage player2, BufferedImage player3,
                                            BufferedImage player4, BufferedImage player5, BufferedImage player6,
                                            BufferedImage player7, BufferedImage player8) {
        Animation animation = new Animation();
        animation.addFrame(player1, 100);
        animation.addFrame(player2, 100);
        animation.addFrame(player3, 100);
        animation.addFrame(player4, 100);
        animation.addFrame(player5, 100);
        animation.addFrame(player6, 100);
        animation.addFrame(player7, 100);
        animation.addFrame(player8, 100);
        return animation;
    }

    private Animation createPlayerShootLAnim(BufferedImage player1, BufferedImage player2, BufferedImage player3, BufferedImage player4) {
        Animation animation = new Animation();
        animation.addFrame(player1, 400);
        animation.addFrame(player2, 400);
        animation.addFrame(player3, 400);
        animation.addFrame(player4, 400);
        return animation;
    }

    private Animation createPlayerShootRAnim(BufferedImage player1, BufferedImage player2, BufferedImage player3, BufferedImage player4) {
        Animation animation = new Animation();
        animation.addFrame(player1, 400);
        animation.addFrame(player2, 400);
        animation.addFrame(player3, 400);
        animation.addFrame(player4, 400);
        return animation;
    }

    private Animation createPlayerDeadLAnim(BufferedImage player1, BufferedImage player2, BufferedImage player3,
                                            BufferedImage player4, BufferedImage player5, BufferedImage player6,
                                            BufferedImage player7, BufferedImage player8, BufferedImage player9,
                                            BufferedImage player10) {
        Animation animation = new Animation();
        animation.addFrame(player1, 400);
        animation.addFrame(player2, 400);
        animation.addFrame(player3, 400);
        animation.addFrame(player4, 400);
        animation.addFrame(player5, 400);
        animation.addFrame(player6, 400);
        animation.addFrame(player7, 400);
        animation.addFrame(player8, 400);
        animation.addFrame(player9, 400);
        animation.addFrame(player10, 400);
        return animation;
    }

    private Animation createPlayerDeadRAnim(BufferedImage player1, BufferedImage player2, BufferedImage player3,
                                            BufferedImage player4, BufferedImage player5, BufferedImage player6,
                                            BufferedImage player7, BufferedImage player8, BufferedImage player9,
                                            BufferedImage player10) {
        Animation animation = new Animation();
        animation.addFrame(player1, 400);
        animation.addFrame(player2, 400);
        animation.addFrame(player3, 400);
        animation.addFrame(player4, 400);
        animation.addFrame(player5, 400);
        animation.addFrame(player6, 400);
        animation.addFrame(player7, 400);
        animation.addFrame(player8, 400);
        animation.addFrame(player9, 400);
        animation.addFrame(player10, 400);
        return animation;
    }

    private Animation createBulletLeftAnim(BufferedImage bullet1, BufferedImage bullet2, BufferedImage bullet3,
                                           BufferedImage bullet4, BufferedImage bullet5){
        Animation animation = new Animation();
        animation.addFrame(bullet1, 100);
        animation.addFrame(bullet2, 100);
        animation.addFrame(bullet3, 100);
        animation.addFrame(bullet4, 100);
        animation.addFrame(bullet5, 100);
        return animation;
    }

    private Animation createBulletRightAnim(BufferedImage bullet1, BufferedImage bullet2, BufferedImage bullet3,
                                            BufferedImage bullet4, BufferedImage bullet5){
        Animation animation = new Animation();
        animation.addFrame(bullet1, 100);
        animation.addFrame(bullet2, 100);
        animation.addFrame(bullet3, 100);
        animation.addFrame(bullet4, 100);
        animation.addFrame(bullet5, 100);
        return animation;
    }

    private Animation createBulletMuzzleAnim(BufferedImage bullet1, BufferedImage bullet2, BufferedImage bullet3,
                                             BufferedImage bullet4, BufferedImage bullet5){
        Animation animation = new Animation();
        animation.addFrame(bullet1, 300);
        animation.addFrame(bullet2, 300);
        animation.addFrame(bullet3, 300);
        animation.addFrame(bullet4, 300);
        animation.addFrame(bullet5, 300);
        return animation;
    }
}
