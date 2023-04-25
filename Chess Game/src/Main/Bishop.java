package Main;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Bishop extends ChessPiece
{
    BufferedImage piece;
    String strpiece;
    
    public Bishop(final boolean white) {
        super(white);
        this.strpiece = "b";
        String choice = "";
        if (white) {
            choice = "white";
        }
        else {
            choice = "black";
        }
        try {
            this.piece = ImageIO.read(this.getClass().getResourceAsStream("/pieces/"+choice + "Bishop.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Image getPiece() {
        return this.piece;
    }
    
    public String toString() {
        return this.strpiece;
    }
    
    public boolean checkValid(final int x, final int y, final int j, final int k, final int[][] z) {
        return Math.abs(x - j) != 0 && Math.abs(x - j) == Math.abs(y - k);
    }
}