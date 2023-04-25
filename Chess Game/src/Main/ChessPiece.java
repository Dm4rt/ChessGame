package Main;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class ChessPiece
{
    boolean white;
    BufferedImage defaultPiece;
    String piece;
    
    public ChessPiece(final boolean c) {
        this.piece = " ";
        this.white = c;
        this.defaultPiece = null;
    }
    
    
    public Image getPiece() {
        return this.defaultPiece;
    }
    
    @Override
    public String toString() {
        return this.piece;
    }
    
    public boolean getColor() {
        return this.white;
    }
    
    public String getType() {
        return this.piece;
    }
    
    public boolean checkValid(final int x, final int y, final int j, final int k, final int[][] z) {
        return x != j || y != k;
    }
}