/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.buildings;

/**
 *
 * @author Jupitar-Orbit
 */
public class Fence extends Buildings{
    
    // view image decaration
   // 3 views
    
    public Fence(int x , int y){
        super(x,y);
        this.setCanTrain(false);
    }
}

