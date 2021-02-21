package Game.tiles;


import Game.Dimentions;
import Game.display.Assets;
import Game.units.Units;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;


public class Tile implements Dimentions,Serializable{
    
    public static void drawTile(int x, int y, BufferedImage tile, Graphics g) {
            
		int iso_x = (x * T_W / 2 - y * T_W / 2);
		int iso_y = (y * T_H/ 2 + x * T_H / 2);
		
		int diff_y = tile.getHeight() - T_H;
		
		g.drawImage(tile, iso_x - T_H/2-16, iso_y - diff_y+8, null);
	}
    
    
    public static void drawUnit(Units unit ,BufferedImage tile, Graphics g) {
        
		int diff_y = tile.getHeight() - T_H; 
                
		// Supposing the tiles are 64*32 in iso only
		g.drawImage(tile, unit.getIso_x() - T_H/2 -18, unit.getIso_y() - diff_y-6, null);
}
    
       public static void drawUI(Graphics g) {
       
		g.drawImage(Assets.inGameMenu2,0,-30, null);
                g.drawImage(Assets.menuBuildings.getSubimage(64, 0, 64, 64),800,510,null);
                g.drawImage(Assets.menuBuildings.getSubimage(64 * 2, 0, 64, 64),900,510,null);
} 
    
    
    
    
    
    
}
