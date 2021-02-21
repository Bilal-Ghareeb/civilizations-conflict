/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.buildings;

import static Game.display.Assets.BuildingSprite;
import Game.display.Image;
import java.io.Serializable;

/**
 *
 * @author Jupitar-Orbit
 */
public class Townhall extends Buildings implements Serializable{
 
    // get the sprites and crop
    //view = Image.loadimage("");
    
    //will be able to create workers and added to the army arraylist
    
    public Townhall(int x , int y){
        super(x,y);
        this.setCanTrain(false);
        BuildingSprite = Image.loadImage("/rss/BuildingSprite.png");
        view = BuildingSprite.getSubimage(128 * 4, 0, 128, 128);
    }
}
