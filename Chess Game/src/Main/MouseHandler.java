package Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener
{
    public int x;
    public int y;
    public int j;
    public int k;
    public boolean start;
    
    public MouseHandler() {
        this.start = false;
    }
    
    @Override
    public void mouseClicked(final MouseEvent e) {
        this.start = true;
        this.x = e.getX();
        this.y = e.getY();
        this.x = this.x / 80 * 80;
        this.y = this.y / 80 * 80;
        this.j = e.getX();
        this.k = e.getY();
    }
    
    @Override
    public void mousePressed(final MouseEvent e) {
        this.start = true;
        this.x = e.getX();
        this.y = e.getY();
        this.x = this.x / 80 * 80;
        this.y = this.y / 80 * 80;
        this.j = e.getX();
        this.k = e.getY();
    }
    
    @Override
    public void mouseReleased(final MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(final MouseEvent e) {
    }
    
    @Override
    public void mouseExited(final MouseEvent e) {
    }
}