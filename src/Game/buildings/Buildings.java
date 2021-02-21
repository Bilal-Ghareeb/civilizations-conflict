/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.buildings;

import static Game.Dimentions.T_H;
import static Game.Dimentions.T_W;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 *
 * @author Jupitar-Orbit
 */

public abstract class Buildings implements Serializable{
    
    
    private int x ,y;
    private int iso_X,iso_Y;
    
    protected transient BufferedImage view;
    
    protected boolean canTrain;
    
    public Buildings(int x, int y) {
        this.x = x;
        this.y = y;
        this.iso_X = x * T_W / 2 - y * T_W / 2;
        this.iso_Y = y * T_H / 2 + x * T_H / 2;
    }
    
    //=============G-S===================
    public int getIso_X() {
        return iso_X;
    }

    public void setIso_X(int iso_X) {
        this.iso_X = iso_X;
    }

    public int getIso_Y() {
        return iso_Y;
    }

    public void setIso_Y(int iso_Y) {
        this.iso_Y = iso_Y;
    }
    

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getView() {
        return view;
    }

    public void setView(BufferedImage view) {
        this.view = view;
    }

    public boolean isCanTrain() {
        return canTrain;
    }

    public void setCanTrain(boolean canTrain) {
        this.canTrain = canTrain;
    }
    
    
    
    
}
