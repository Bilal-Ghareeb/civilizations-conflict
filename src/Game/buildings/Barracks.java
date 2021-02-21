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

public class Barracks extends Buildings implements Serializable{
    public Barracks(int x , int y){
        super(x,y);
        this.setCanTrain(true);
        BuildingSprite = Image.loadImage("/rss/BuildingSprite.png");
         view = BuildingSprite.getSubimage(128 * 0, 0, 128, 128);
    
    }
}
