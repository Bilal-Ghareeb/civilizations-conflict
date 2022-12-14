
package Game.Pathfinder;
import java.io.Serializable;

public class Node implements Serializable {
    
    	public boolean wall;
	public int row, col;
	public int g_cost;
	public int h_cost;
	public int tile;
	public Node parent;
	
        //Parent acts as a Node in the linked list.
        
	public Node(boolean wall, int row, int col, int tile) {
		this.wall = wall;
		this.row = row;
		this.col = col;
		this.tile = tile;
		
		g_cost = 0;
		h_cost = 0;
		parent = null;
	}
	
	public int f_cost() {
            
		return h_cost + g_cost;
	}
	
}
