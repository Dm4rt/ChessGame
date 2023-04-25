package Main;

import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable
{
    final int originalTileSize = 16;
    final int scale = 5;
    final int tileSize = 80;
    final int maxScreenCol = 10;
    final int maxScreenRow = 10;
    final int screenWidth = 800;
    final int screenHeight = 800;
    int FPS;
    boolean checkMate;
    Thread gameThread;
    Board playBoard;
    Board testBoard;
    boolean menu;
    MouseHandler mh;
    BufferedImage title;
    BufferedImage pieceA;
    BufferedImage pieceB;
    BufferedImage startButton;
    BufferedImage playerA;
    BufferedImage playerB;
    BufferedImage rematchButton;
    int gameTimerS;
    int gameTimerM;
    public Sound sound;
    int prevX;
    int prevY;
    int currentX;
    int currentY;
    int state;
    
    public GamePanel() {
        this.FPS = 60;
        this.checkMate = false;
        this.playBoard = new Board();
        this.testBoard = new Board();
        this.menu = true;
        this.gameTimerS = 0;
        this.gameTimerM = 0;
        this.prevX = 0;
        this.prevY = 0;
        this.currentX = 1;
        this.currentY = 1;
        this.state = 0;
        this.setPreferredSize(new Dimension(800, 800));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.playBoard.resetBoard();
        this.testBoard.resetBoard();
        this.copyBoard();
        this.addMouseListener((MouseListener)(this.mh = new MouseHandler()));
        this.sound = new Sound();
        try {
            this.title = ImageIO.read(this.getClass().getResourceAsStream("/hud/Chess.png"));
            this.pieceA = ImageIO.read(this.getClass().getResourceAsStream("/pieces/blackTower.png"));
            this.pieceB = ImageIO.read(this.getClass().getResourceAsStream("/pieces/whiteQueen.png"));
            this.startButton = ImageIO.read(this.getClass().getResourceAsStream("/hud/Start.png"));
            this.playerA = ImageIO.read(this.getClass().getResourceAsStream("/hud/black.png"));
            this.playerB = ImageIO.read(this.getClass().getResourceAsStream("/hud/white.png"));
            this.rematchButton = ImageIO.read(this.getClass().getResourceAsStream("/hud/rematch.png"));
        }
        catch (IOException ex) {}
    }
    
    public void startGameThread() {
        (this.gameThread = new Thread(this)).start();
    }
    
    @Override
    public void run() {
        final double drawInterval = 1000000000 / this.FPS;
        double delta = 0.0;
        long lastTime = System.nanoTime();
        long timer = 0L;
        int drawCount = 0;
        while (this.gameThread != null) {
            final long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;
            if (delta >= 1.0) {
                this.update();
                this.repaint();
                --delta;
                ++drawCount;
            }
            if (timer >= 1000000000L) {
                drawCount = 0;
                timer = 0L;
                if (this.checkMate) {
                    continue;
                }
                if (!this.menu) {
                    ++this.gameTimerS;
                    if (this.gameTimerS != 59) {
                        continue;
                    }
                    this.gameTimerS = 0;
                    ++this.gameTimerM;
                }
                else {
                    this.gameTimerS = 0;
                    this.gameTimerM = 0;
                }
            }
        }
    }
    
    public void update() {
        if (this.checkMate) {
            if (this.mh.j >= 240 && this.mh.j <= 560 && this.mh.k >= 600 && this.mh.k <= 720) {
                this.menu = false;
                this.playBoard.check = false;
                this.playBoard.whiteCheck = false;
                this.playBoard.blackCheck = false;
                this.playBoard.resetBoard();
                this.testBoard.resetBoard();
                this.copyBoard();
                this.checkMate = false;
                this.gameTimerS = 0;
                this.gameTimerM = 0;
            }
        }
        else if (!this.menu) {
            if (this.mh.start && this.mh.x >= 80 && this.mh.y >= 80 && this.mh.x <= 640 && this.mh.y <= 640) {
                this.currentX = this.mh.x / 80 - 1;
                this.currentY = this.mh.y / 80 - 1;
                if (this.playBoard.checkForPiece(this.currentX, this.currentY)) {
                	System.out.println(currentY+" "+currentX+" "+prevY+" "+prevX);
                    this.prevX = this.currentX;
                    this.prevY = this.currentY;
                    this.state = 1;
                }
                if (this.state == 1 && this.playBoard.checkPossible(this.currentX, this.currentY, this.prevX, this.prevY)) {
                	System.out.println(currentY+" "+currentX+" "+prevY+" "+prevX);
                    if (this.playBoard.check) {
                        if (!this.testMove(this.prevX, this.prevY, this.currentX, this.currentY)) {
                            this.copyBoard();
                        }
                        else {
                            this.playBoard.playerMove(this.prevX, this.prevY, this.currentX, this.currentY);
                            this.playSE(0);
                            this.copyBoard();
                        }
                    }
                    else {
                    	if (!this.testMove(this.prevX, this.prevY, this.currentX, this.currentY)) {
                    		if(whichCheck!=playBoard.turn) {
                    			this.playBoard.playerMove(this.prevX, this.prevY, this.currentX, this.currentY);
                    			if (!this.playBoard.checkForCheck()) {
                                    this.playSE(0);
                                }
                                else {
                                	this.playSE(1);
                                }
                    		}
                            this.copyBoard();
                        }
                    	else {
                        this.playBoard.playerMove(this.prevX, this.prevY, this.currentX, this.currentY);
                        if (!this.playBoard.checkForCheck()) {
                            this.playSE(0);
                        }
                        else {
                        	this.playSE(1);
                        }
                    	}
                        this.copyBoard();
                    }
                    this.playBoard.checkForCheck();
                    this.state = 0;
                }
                if (checkForMate()) {
                    this.playSE(3);
                }
            }
        }
        else if (this.mh.j >= 240 && this.mh.j <= 560 && this.mh.k >= 480 && this.mh.k <= 600) {
            this.menu = false;
        }
    }
    
    public boolean checkForMate() {
        for (int x = 0; x < 8; ++x) {
            for (int y = 0; y < 8; ++y) {
                if (this.playBoard.getBoard()[x][y].getPiece() != null && this.playBoard.getBoard()[x][y].getColor() == this.playBoard.turn) {
                    for (int j = 0; j < 8; ++j) {
                        for (int k = 0; k < 8; ++k) {
                            if ((this.playBoard.getBoard()[j][k].getPiece() == null || this.playBoard.getBoard()[j][k].getColor() != this.playBoard.turn) && this.playBoard.checkPossible(k, j, y, x) && this.testMove(y, x, k, j)) {
                                return this.checkMate = false;
                            }
                        }
                    }
                }
            }
        }
        return this.checkMate = true;
    }
    public boolean whichCheck=false;
    public boolean testMove(final int x, final int y, final int j, final int k) {
        this.testBoard.turn = this.playBoard.turn;
        this.testBoard.playerMove(x, y, j, k);
        if (this.testBoard.checkForCheck()) {
        	if(testBoard.blackCheck) {
        		whichCheck=false;
        	}
        	else {
        		whichCheck=true;
        	}
            this.copyBoard();
            return false;
        }
        this.copyBoard();
        return true;
    }
    
    public void copyBoard() {
        this.testBoard.turn = this.playBoard.turn;
        this.testBoard.check = this.playBoard.check;
        for (int x = 0; x < 8; ++x) {
            this.testBoard.getBoard()[x] = this.playBoard.getBoard()[x].clone();
        }
    }
    
    public void playMusic(final int i) {
        this.sound.setFile(i);
        this.sound.play();
        this.sound.loop();
    }
    
    public void stopMusic() {
        this.sound.stop();
    }
    
    public void playSE(final int i) {
        this.sound.setFile(i);
        this.sound.play();
    }
    
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics g2 = g;
        g2.setColor(new Color(81, 117, 141));
        g2.fillRect(0, 0, 800, 800);
        if (this.checkMate) {
            g2.setColor(new Color(127, 127, 127, 95));
            g2.fillRect(0, 0, 800, 800);
            g2.setColor(Color.white);
            g2.setFont(new Font("Impact", 0, 85));
            if (!this.playBoard.whiteCheck) {
                g2.drawImage(this.playerB, 240, 40, 320, 320, null);
                g2.drawString("Player 2 Wins!", 140, 480);
            }
            else {
                g2.drawImage(this.playerA, 240, 40, 320, 320, null);
                g2.drawString("Player 1 Wins!", 140, 480);
            }
            g2.setColor(new Color(171, 171, 171));
            if (this.gameTimerS < 10) {
                g2.drawString("Time: " + this.gameTimerM + ":0" + this.gameTimerS, 216, 580);
            }
            else {
                g2.drawString("Time: " + this.gameTimerM + ":" + this.gameTimerS, 216, 580);
            }
            g2.drawImage(this.rematchButton, 240, 600, 320, 120, null);
        }
        else if (!this.menu) {
            this.playBoard.draw(g2);
            if (this.playBoard.turn) {
                g2.setColor(Color.white);
                g2.setFont(new Font("Impact", 0, 35));
                g2.drawString("Whites Turn", 540, 765);
            }
            else {
                g2.setColor(Color.black);
                g2.setFont(new Font("Impact", 0, 35));
                g2.drawString("Blacks Turn", 540, 765);
            }
            g2.setColor(Color.white);
            if (this.gameTimerS < 10) {
                g2.drawString("Time: " + this.gameTimerM + ":0" + this.gameTimerS, 85, 765);
            }
            else {
                g2.drawString("Time: " + this.gameTimerM + ":" + this.gameTimerS, 85, 765);
            }
            g2.drawImage(this.playerA, 80, 16, 52, 52, null);
            g2.setFont(new Font("Impact", 0, 20));
            g2.drawString("Player 1", 136, 36);
            g2.drawImage(this.playerB, 668, 16, 52, 52, null);
            g2.setFont(new Font("Impact", 0, 20));
            g2.drawString("Player 2", 596, 36);
        }
        else {
            g2.setColor(Color.white);
            g2.drawImage(this.title, 80, 80, 640, 240, null);
            g2.drawImage(this.pieceA, -160, 480, 320, 320, null);
            g2.drawImage(this.pieceB, 640, 480, 320, 320, null);
            g2.drawImage(this.startButton, 240, 480, 320, 120, null);
        }
        g2.dispose();
    }
}