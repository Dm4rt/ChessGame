package Main;

import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Board extends JPanel
{
    final int originalTileSize = 16;
    final int scale = 5;
    final int tileSize = 80;
    final int col = 8;
    final int row = 8;
    final int width = 640;
    final int height = 640;
    final int xS = 80;
    final int yS = 80;
    BufferedImage blackTile;
    BufferedImage whiteTile;
    public boolean win;
    public boolean turn;
    ChessPiece[][] playerBoard;
    public boolean check;
    public boolean whiteCheck;
    public boolean blackCheck;
    int[][] numBoard;
    
    public Board() {
        this.win = false;
        this.turn = false;
        this.playerBoard = new ChessPiece[8][8];
        this.numBoard = new int[8][8];
        this.check = false;
        this.blackCheck = false;
        this.whiteCheck = false;
        this.numBoard = this.setNumBoard();
        try {
            this.blackTile = ImageIO.read(this.getClass().getResourceAsStream("/hud/blackTile.png"));
            this.whiteTile = ImageIO.read(this.getClass().getResourceAsStream("/hud/whiteTile.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ChessPiece[][] getBoard() {
        return this.playerBoard;
    }
    
    public int[][] setNumBoard() {
        final int[][] origBoard = { { 4, 3, 2, 5, 6, 2, 3, 4 }, { 1, 1, 1, 1, 1, 1, 1, 1 }, new int[8], new int[8], new int[8], new int[8], { 1, 1, 1, 1, 1, 1, 1, 1 }, { 4, 3, 2, 5, 6, 2, 3, 4 } };
        return origBoard;
    }
    
    public void printNumBoard() {
        for (int x = 0; x < 8; ++x) {
            for (int y = 0; y < 8; ++y) {
                System.out.print(this.numBoard[x][y]);
            }
            System.out.println();
        }
    }
    
    public void resetBoard() {
        for (int x = 0; x < 8; ++x) {
            for (int y = 0; y < 8; ++y) {
                this.playerBoard[x][y] = new ChessPiece(true);
            }
        }
        for (int x = 0; x < 8; ++x) {
            if (x == 0 || x == 7) {
                this.playerBoard[0][x] = (ChessPiece)new Rook(true);
                this.playerBoard[1][x] = (ChessPiece)new Pawn(true);
            }
            else if (x == 1 || x == 6) {
                this.playerBoard[0][x] = (ChessPiece)new Knight(true);
                this.playerBoard[1][x] = (ChessPiece)new Pawn(true);
            }
            else if (x == 2 || x == 5) {
                this.playerBoard[0][x] = new Bishop(true);
                this.playerBoard[1][x] = (ChessPiece)new Pawn(true);
            }
            else if (x == 3) {
                this.playerBoard[0][x] = (ChessPiece)new Queen(true);
                this.playerBoard[1][x] = (ChessPiece)new Pawn(true);
            }
            else if (x == 4) {
                this.playerBoard[0][x] = (ChessPiece)new King(true);
                this.playerBoard[1][x] = (ChessPiece)new Pawn(true);
            }
        }
        for (int x = 0; x < 8; ++x) {
            if (x == 0 || x == 7) {
                this.playerBoard[7][x] = (ChessPiece)new Rook(false);
                this.playerBoard[6][x] = (ChessPiece)new Pawn(false);
            }
            else if (x == 1 || x == 6) {
                this.playerBoard[7][x] = (ChessPiece)new Knight(false);
                this.playerBoard[6][x] = (ChessPiece)new Pawn(false);
            }
            else if (x == 2 || x == 5) {
                this.playerBoard[7][x] = new Bishop(false);
                this.playerBoard[6][x] = (ChessPiece)new Pawn(false);
            }
            else if (x == 3) {
                this.playerBoard[7][x] = (ChessPiece)new Queen(false);
                this.playerBoard[6][x] = (ChessPiece)new Pawn(false);
            }
            else if (x == 4) {
                this.playerBoard[7][x] = (ChessPiece)new King(false);
                this.playerBoard[6][x] = (ChessPiece)new Pawn(false);
            }
        }
    }
    
    public void printBoard() {
        for (int x = 0; x < 8; ++x) {
            for (int y = 0; y < 8; ++y) {
                System.out.print(this.playerBoard[x][y] + " ");
            }
            System.out.println();
        }
    }
    
    public boolean block(final int x, final int y, final int j, final int k) {
    	if(playerBoard[k][j].getType().equals("p")) {
    		if(Math.abs(x-j)==1&&Math.abs(y-k)==1&&playerBoard[y][x].getColor()!=turn) {
    			return false;
    		}
    		if(playerBoard[y][x].getPiece()!=null) {
    			return true;
    		}
    		if(playerBoard[k][j].getColor()) {
    			if(k==7) {
    				return true;
    			}
    			else {
    				if(playerBoard[k+1][j].getPiece()!=null) {
    					return true;
    				}
    				return false;
    			}
    		}
    		else {
    			if(k==0) {
    				return true;
    			}
    			else {
    				if(playerBoard[k-1][j].getPiece()!=null) {
    					return true;
    				}
    				return false;
    			}
    		}
    	}
        if (x == j) {
            if (y < k) {
                for (int g = y; g < k; ++g) {
                    if (g != k && g != y && this.playerBoard[g][x].getPiece() != null) {
                        return true;
                    }
                }
            }
            else {
                for (int g = y; g > k; --g) {
                    if (g != k && g != y && this.playerBoard[g][x].getPiece() != null) {
                        return true;
                    }
                }
            }
        }
        else if (y == k) {
            if (x < j) {
                for (int g = x; g < j; ++g) {
                    if (g != j && g != x && this.playerBoard[y][g].getPiece() != null) {
                        return true;
                    }
                }
            }
            else {
                for (int g = x; g > j; --g) {
                    if (g != j && g != x && this.playerBoard[y][g].getPiece() != null) {
                        return true;
                    }
                }
            }
        }
        else {
            if (x < j && y < k) {
                for (int g = y, h = x; g < k && h < j; ++g, ++h) {
                    if (g != y && g != k && h != x && h != j && this.playerBoard[g][h].getPiece() != null) {
                        return true;
                    }
                }
            }
            if (x > j && y > k) {
                for (int g = y, h = x; g > k && h > j; --g, --h) {
                    if (g != y && g != k && h != x && h != j && this.playerBoard[g][h].getPiece() != null) {
                        return true;
                    }
                }
            }
            if (x > j && y < k) {
                for (int g = y, h = x; g < k && h > j; ++g, --h) {
                    if (g != y && g != k && h != x && h != j && this.playerBoard[g][h].getPiece() != null) {
                        return true;
                    }
                }
            }
            if (x < j && y > k) {
                for (int g = y, h = x; g > k && h < j; --g, ++h) {
                    if (g != y && g != k && h != x && h != j && this.playerBoard[g][h].getPiece() != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void playerMove(final int a, final int b, final int c, final int d) {
        if (this.playerBoard[b][a].getColor() == this.turn) {
            this.playerBoard[d][c] = this.playerBoard[b][a];
            this.playerBoard[b][a] = new ChessPiece(this.turn);
            this.numBoard[d][c] = this.numBoard[b][a];
            this.numBoard[b][a] = 0;
        }
        if (this.turn) {
            this.turn = false;
        }
        else {
            this.turn = true;
        }
    }
    
    public boolean checkForPiece(final int x, final int y) {
        return this.playerBoard[y][x].getPiece() != null && this.turn == this.playerBoard[y][x].getColor();
    }
    
    public boolean checkPossible(final int x, final int y, final int j, final int k) {
        return this.playerBoard[k][j].checkValid(x, y, j, k, this.numBoard) && !this.block(x,y,j,k);
    }
    
    public boolean checkForCheckBlack() {
        int j = 0;
        int k = 0;
        for (int x = 0; x < 8; ++x) {
            for (int y = 0; y < 8; ++y) {
                if (this.playerBoard[x][y].toString().equals("k") && !this.playerBoard[x][y].getColor()) {
                    j = x;
                    k = y;
                    break;
                }
            }
        }
        for (int x = 0; x < 8; ++x) {
            for (int y = 0; y < 8; ++y) {
                if (this.playerBoard[x][y].getPiece() != null && this.playerBoard[x][y].getColor() && this.checkPossible(k, j, y, x)) {
                    return this.blackCheck = true;
                }
            }
        }
        return this.blackCheck = false;
    }
    
    public boolean checkForCheckWhite() {
        int j = 0;
        int k = 0;
        for (int x = 0; x < 8; ++x) {
            for (int y = 0; y < 8; ++y) {
                if (this.playerBoard[x][y].toString().equals("k") && this.playerBoard[x][y].getColor()) {
                    j = x;
                    k = y;
                    break;
                }
            }
        }
        for (int x = 0; x < 8; ++x) {
            for (int y = 0; y < 8; ++y) {
                if (this.playerBoard[x][y].getPiece() != null && !this.playerBoard[x][y].getColor() && this.checkPossible(k, j, y, x)) {
                    return this.whiteCheck = true;
                }
            }
        }
        return this.whiteCheck = false;
    }
    
    public boolean checkForCheck() {
        if (this.checkForCheckWhite()) {
            return this.check = true;
        }
        if (this.checkForCheckBlack()) {
            return this.check = true;
        }
        return this.check = false;
    }
    
    public void checkForWin() {
        this.win = false;
    }
    
    public void draw(final Graphics g) {
        super.paintComponent(g);
        final Graphics g2 = g;
        boolean shaded = false;
        for (int x = 0; x < 8; ++x) {
            for (int y = 0; y < 8; ++y) {
                if (shaded) {
                    g2.drawImage(this.blackTile, 80 + x * 80, 80 + y * 80, 80, 80, null);
                    shaded = false;
                }
                else {
                    g2.drawImage(this.whiteTile, 80 + x * 80, 80 + y * 80, 80, 80, null);
                    shaded = true;
                }
                g2.drawImage(this.playerBoard[y][x].getPiece(), 80 + x * 80 + 8, 80 + y * 80 + 8, 64, 64, null);
            }
            shaded = !shaded;
        }
    }
}