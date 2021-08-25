
import javax.swing.*;

public class MyFrame extends JFrame{

    GamePanel gamePanel = new GamePanel();

    public MyFrame(){
        System.out.println("Inside constructor(MyFrame.java)");
        this.setSize(840,840);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(gamePanel);
        gamePanel.loadImages();
        gamePanel.animationLoop();
    }

}
