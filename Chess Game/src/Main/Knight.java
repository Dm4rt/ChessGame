package Main;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Knight extends ChessPiece
{
    BufferedImage piece;
    String strpiece;
    
    public Knight(final boolean white) {
        super(white);
        this.strpiece = "n";
        String choice = "";
        if (white) {
            choice = "white";
        }
        else {
            choice = "black";
        }
        try {
            this.piece = ImageIO.read(this.getClass().getResourceAsStream("/pieces/"+choice + "Horse.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public Image getPiece() {
        return this.piece;
    }
    
    @Override
    public String toString() {
        return this.strpiece;
    }
    
    @Override
    public boolean checkValid(final int x, final int y, final int j, final int k, final int[][] z) {
        return (Math.abs(x - j) == 2 && Math.abs(y - k) == 1) || (Math.abs(x - j) == 1 && Math.abs(y - k) == 2);
    }
}