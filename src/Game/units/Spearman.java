
package Game.units;


import java.io.Serializable;

public class Spearman extends Units implements Serializable {
    
     public Spearman(int x , int y){
         
      //Saving starting position
      super(x,y);
      
      //Default Health for Spearman
      this.setHealth(100);
      
    }
     
}
