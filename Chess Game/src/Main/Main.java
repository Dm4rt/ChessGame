package Main;
import java.awt.Component;
import javax.swing.JFrame;

public class Main
{
    public static void main(final String[] args) {
        final JFrame window = new JFrame();
        window.setDefaultCloseOperation(3);
        window.setResizable(false);
        window.setTitle("Chess");
        final GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.startGameThread();
    }
}