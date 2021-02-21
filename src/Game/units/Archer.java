package Game.units;

import java.awt.image.BufferedImage;
import Game.animation.*;
import Game.display.*;
import java.io.Serializable;

public class Archer extends Units implements Serializable{
    
    public Archer(int x , int y){
        
      //Saving starting position
      super(x,y);
      
      //Default Health for Archer
      this.setHealth(100);
      
      walking_right_array = new BufferedImage[15];
      idle_right_array = new BufferedImage[11];
      
      walking_left_array = new BufferedImage[15];
      idle_left_array = new BufferedImage[11];
      
      walking_down_array = new BufferedImage[15];
      idle_down_array = new BufferedImage[11];
      
      walking_up_array = new BufferedImage[15];
      idle_up_array = new BufferedImage[11];
      
        
      attack_Sprite1 = new BufferedImage[16];
      attack_Sprite2 = new BufferedImage[16];
      
      dying_sprite = new BufferedImage[14];
      
      
      
      //Archer's own Walking sprites in 4 directions
      walkingRightSprite = Image.loadImage("/rss/Archer/walkingright.png");
      walkingLeftSprite = Image.loadImage("/rss/Archer/walkingleft.png");
      walkingUpSprite = Image.loadImage("/rss/Archer/walkingup.png");
      walkingDownSprite = Image.loadImage("/rss/Archer/walkingdown.png");
      
      //Archer's idle Sprites in 4 directions
      idleUpSprite = Image.loadImage("/rss/Archer/idleup.png");
      idleDownSprite = Image.loadImage("/rss/Archer/idledown.png");
      idleRightSprite = Image.loadImage("/rss/Archer/idleright.png");
      idleLeftSprite = Image.loadImage("/rss/Archer/idleleft.png");
      
      
    
      //attack sprite while idle in 2 directions
      attackSprite1 = Image.loadImage("/rss/Archer/Attack1.png");
      attackSprite2 = Image.loadImage("/rss/Archer/Attack2.png");
      
      // Dying Sprite while idle in 1 direction
      dyingSprite = Image.loadImage("/rss/Archer/Dying.png");
      
      //Attack
      SpritesCrop(16,attack_Sprite1,attackSprite1,64);
      SpritesCrop(16,attack_Sprite2,attackSprite2,64);
      
      //Death 
      SpritesCrop(13,dying_sprite,dyingSprite,64);
      
      //Right
      SpritesCrop(15,walking_right_array,walkingRightSprite,64);
      SpritesCrop(10,idle_right_array,idleRightSprite,64);
      //left
      SpritesCrop(15,walking_left_array,walkingLeftSprite,64);
      SpritesCrop(10,idle_left_array,idleLeftSprite,64);
      //UP
      SpritesCrop(15,walking_up_array,walkingUpSprite,64);
      SpritesCrop(10,idle_up_array,idleUpSprite,64);
      //DOWN
      SpritesCrop(15,walking_down_array,walkingDownSprite,64);
      SpritesCrop(10,idle_down_array,idleDownSprite,64);
      
      right_walking = new Animate(walking_right_array);
      right_idle = new Animate(idle_right_array);
      
      left_walking = new Animate(walking_left_array);
      left_idle = new Animate(idle_left_array);
      
      up_walking = new Animate(walking_up_array);
      up_idle = new Animate(idle_up_array);
      
      down_walking = new Animate(walking_down_array);
      down_idle = new Animate(idle_down_array);
      
      attack1 = new Animate(attack_Sprite1);
      attack2 = new Animate(attack_Sprite2);
      
      death = new Animate(dying_sprite);
    }
}
