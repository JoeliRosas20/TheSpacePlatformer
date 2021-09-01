
import javax.swing.*;

public class MyFrame extends JFrame{

    GamePanel gamePanel = new GamePanel();
    Game game = new Game();

    public MyFrame(){
        System.out.println("Inside constructor(MyFrame.java)");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        //this.add(gamePanel);
        this.add(game);
        this.setTitle("Space Explorers");
        this.pack();
        this.setLocationRelativeTo(null);
        game.start();
        //gamePanel.loadImages();
        //gamePanel.animationLoop();
    }

}
