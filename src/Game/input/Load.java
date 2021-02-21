/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game.input;
import Game.Pathfinder.Node;
import Game.buildings.*;
import static Game.input.Load.types.*;
import Game.units.*;
import java.util.ArrayList;

/**
 *
 * @author Jupitar-Orbit
 */
public class Load {
    
   public interface types{
       String archer = "Game.units.Archer";
       String swordman = "Game.units.Spearman";
       String worker = "Game.units.Worker";
       
       String barracks = "Game.buildings.Barracks";
       String townhall = "Game.buildings.Townhall";
   }
    
    public static ArrayList<Units> renewarmy(ArrayList<Units> army){
        ArrayList<Units> loadedarmy = new ArrayList<Units>();
        
        for(int i=0;i<army.size();i++){
            Class a = army.get(i).getClass();
            String name = a.getName();
            int ux = army.get(i).getX();
            int uy = army.get(i).getY();
            
            loadedarmy.add(new Archer(ux,uy));
                //case swordman:loadedarmy.add(new Swordman(ux,uy));break;
                //case worker:loadedarmy.add(new Worker(ux,uy));break;
            
        }
        return loadedarmy;
    }
    
    public static ArrayList<Buildings> renewbuildings(ArrayList<Buildings> build){
        ArrayList<Buildings>loadedbuildings = new ArrayList<Buildings>();
        
        for(int i=0;i<build.size();i++){
            Class a = build.get(i).getClass();
            String name = a.getName();
            int ux = build.get(i).getX();
            int uy = build.get(i).getY();
            switch(name){
                case barracks:loadedbuildings.add(new Barracks(ux,uy));break;
                case townhall:loadedbuildings.add(new Townhall(ux,uy));break;
            }  
        }
        return loadedbuildings;
    }
    
    public static ArrayList<Units> refreshArmy(ArrayList<Units>army, ArrayList<Units> current){
        
        for(int i =0;i<current.size()&&i<army.size();i++){
            current.get(i).setX(army.get(i).getX());
            current.get(i).setY(army.get(i).getY());
            current.get(i).setIso_x(army.get(i).getIso_x());
            current.get(i).setIso_y(army.get(i).getIso_y());
            current.get(i).setMoving(army.get(i).isMoving());
            current.get(i).setMydir(army.get(i).getMydir());
            current.get(i).setAttack(army.get(i).isAttack());
            current.get(i).setDying(army.get(i).isDying());
        }
        
        return current;
    }
    
    // there is no refresh buildings function as the buildings don't move ..

    public static Node[][] mixLayer(Node[][]current,Node[][]recieved){
        
        for(int i =0;i<current.length;i++){
            for(int j=0;j<current.length;j++){
                
                if(recieved[j][i].tile ==1){
                    current[j][i].wall = true;
                    current[j][i].tile=1;
                }
            }
        }
        return current;
    }
}
