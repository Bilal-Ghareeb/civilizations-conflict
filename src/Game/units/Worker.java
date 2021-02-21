
package Game.units;

public class Worker extends Units{
    
    public Worker(int x , int y){
        
      //Saving starting position
      super(x,y);
     
      //Default Health for Worker
      this.setHealth(75);
     
    }
}
