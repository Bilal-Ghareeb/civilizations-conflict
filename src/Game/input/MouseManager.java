package Game.input;

import Game.Dimentions;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener, Dimentions {
   
    private boolean leftPressed, rightPressed;
    private boolean leftReleased,RightReleasd;
    private int xconst,yconst;

    public boolean isLeftReleased() {
        return leftReleased;
    }

    public void setLeftReleased(boolean leftReleased) {
        this.leftReleased = leftReleased;
    }
    private int mouseX, mouseY;
   

    public boolean isLeftPressed() {
        return leftPressed;
    }
    

    public boolean isRightPressed() {
        return rightPressed;
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
          
    }
    

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            
            leftPressed = true;
            
            mouseX = e.getX();
            mouseY = e.getY();
            xconst = e.getX();
            yconst = e.getY();
            
            System.out.println("X = " + mouseX);
            System.out.println("Y = " + mouseY);
            

        } else if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = true;
            
         
            mouseX = e.getX();
            mouseY = e.getY();
           
        }
     
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
            leftReleased = true;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
           rightPressed = false;
           RightReleasd = true;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
        mouseX = e.getX();
        mouseY = e.getY();
        
    }
    
    
       public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }
       

       
    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }
    
    
    public int getMx() {
        
        float x = mouseX - 470;
        float y = mouseY - 310 + 10 * T_H / 2; // 10 here is size of map
        int col = (int) (x / T_W + y / T_H);
        
        return col;
    }
    

    public int getMy() {
        
        float x = mouseX - 470;
        float y = mouseY - 310 + 10 * T_H / 2; // 10 here is size of map
        int row = (int) (y / T_H - x / T_W);
       
        return row;
    }
    
    
    
    public int getMouseX() {

        return mouseX;
    }

    public int getMouseY() {

        return mouseY;
    }

    public boolean isRightReleasd() {
        return RightReleasd;
    }

    public int getXconst() {
        return xconst;
    }

    public int getYconst() {
        return yconst;
    }
    
    
    

}
