package Main;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Rook extends ChessPiece
{
    BufferedImage piece;
    String strpiece;
    
    public Rook(final boolean white) {
        super(white);
        this.strpiece = "r";
        String choice = "";
        if (white) {
            choice = "white";
        }
        else {
            choice = "black";
        }
        try {
            this.piece = ImageIO.read(this.getClass().getResourceAsStream("/pieces/"+choice + "Tower.png"));
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
        return (x == j && y != k) || (x != j && y == k);
    }
}