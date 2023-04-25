package Main;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Pawn extends ChessPiece
{
    BufferedImage piece;
    String strpiece;
    private Boolean firstTurn;
    
    public Pawn(final boolean white) {
        super(white);
        this.strpiece = "p";
        this.firstTurn = true;
        String choice = "";
        if (white) {
            choice = "white";
        }
        else {
            choice = "black";
        }
        try {
            this.piece = ImageIO.read(this.getClass().getResourceAsStream("/pieces/"+choice + "Pawn.png"));
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
    public String getType() {
    	return strpiece;
    }
    
    @Override
    public boolean checkValid(final int x, final int y, final int j, final int k, final int[][] z) {
        if (x == j && y == k) {
            return false;
        }
        if(getColor()) {
        	if(firstTurn&&(y-k)==2) {
            	firstTurn=false;
            	return true;
            }
            else if((y-k)==1) {
            	firstTurn=false;
            	return true;
            }
            else if(y-k==1&&Math.abs(x-j)==1) {
            	firstTurn=false;
            	return true;
            }
        }
        else {
        	if(firstTurn&&(k-y)==2) {
            	firstTurn=false;
            	return true;
            }
            else if((k-y)==1) {
            	firstTurn=false;
            	return true;
            }
            else if(k-y==1&&Math.abs(x-j)==1) {
            	firstTurn=false;
            	return true;
            }
        }
        return false;
    }
}