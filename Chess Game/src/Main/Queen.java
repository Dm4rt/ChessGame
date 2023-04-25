package Main;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Queen extends ChessPiece
{
    BufferedImage piece;
    String strpiece;
    
    public Queen(final boolean white) {
        super(white);
        this.strpiece = "q";
        String choice = "";
        if (white) {
            choice = "white";
        }
        else {
            choice = "black";
        }
        try {
            this.piece = ImageIO.read(this.getClass().getResourceAsStream("/pieces/"+choice + "Queen.png"));
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
        return (Math.abs(x - j) > 0 && Math.abs(y - k) == 0) || (Math.abs(x - j) == 0 && Math.abs(y - k) > 0) || (Math.abs(x - j) != 0 && Math.abs(x - j) == Math.abs(y - k));
    }
}