package Game;

import static Game.GameLoop.sound;
import Game.Pathfinder.Node;
import static Game.States.StateManager.multiplayer;
import static Game.States.StateManager.player2;

import Game.buildings.*;
import static Game.display.Window.m;
import Game.input.MouseManager;
import Game.tiles.Tile;
import Game.units.*;
import static Game.units.Units.*;
import java.awt.Graphics;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.sound.sampled.LineUnavailableException;

public class Player implements Dimentions, Serializable {

    public enum civilizations {
        Knights, Nomads
    };
    private civilizations currentCivil;

    private int resource1;
    private int resource2;
    private int gold;

    //binary file is saved with this string name in S-Manager class;
    private String name;
    private Graphics g;
    private int BuildingType;

    private ArrayList<Units> army;

    private int enemyIndex;

    private ArrayList<Buildings> buildings;

    //Map layer used in path finder algorithm for the units;
    private Node layermap[][];

    //Constructor to initialize for any new player
    public Player(int map[][]) {
        this.name = name;
        this.resource1 = 1000;
        this.resource2 = 1000;
        this.gold = 5000;
        this.army = new ArrayList<>();
        this.buildings = new ArrayList<>();
        this.BuildingType = 0;
        this.setMap(map);
        enemyIndex = -1;
    }

    private void setMap(int map[][]) {
        // Assigning the elements of map to the Nodes array of Maze
        // if the element equals 2 then it isn't a wall and the units
        // Can move on it
        layermap = new Node[map.length][map[0].length];

        for (int r = 0; r < layermap.length; r++) {
            for (int c = 0; c < layermap[0].length; c++) {
                int temp = map[r][c];
                boolean wall = true;
                if (temp == 2) {
                    wall = false;
                }
                layermap[r][c] = new Node(wall, r, c, 0);
            }
        }
    }

    public ArrayList<Buildings> getBuildings() {
        return buildings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Units> getArmy() {
        return army;
    }

    public Node[][] getLayermap() {
        return layermap;
    }

    public void setLayermap(Node[][] layermap) {
        this.layermap = layermap;
    }

    public void setArmy(ArrayList<Units> army) {
        this.army = army;
    }

    public void setBuildings(ArrayList<Buildings> buildings) {
        this.buildings = buildings;
    }

    public void addUnit(Units unit) {
        if(resource1>=75 && gold >=150){
        resource1-=75;
        gold-=150;
        army.add(unit);
        }
    }

    public void addBuilding(Buildings build) {
        int x = build.getX();
        int y = build.getY();
        
        if(resource1 >= 120 && resource2>=200 && gold >=200){
        // first if condition to make sure that the building will be inside the map
       //second if condition to make sure houses won't be built on each other
        
           if ((y - 1) > 0 && (x - 1) > 0 && ((x + 1) < 10)) {
                if((!layermap[y][x].wall && !layermap[y - 1][x].wall && !layermap[y][x + 1].wall && !layermap[y - 1][x + 1].wall) ){
                    resource1-=120;
                    resource2-=200;
                    gold-=200;
            buildings.add(build);
            layermap[y][x] = new Node(true, y, x, 1);
            layermap[y - 1][x] = new Node(true, y, x, 1);
            layermap[y][x + 1] = new Node(true, y, x, 1);
            layermap[y - 1][x + 1] = new Node(true, y, x, 1);
        }
        }
        }
    }

    public void drawArmy(Graphics g) {
        for (int i = 0; i < army.size(); i++) {
            Tile.drawUnit(army.get(i), army.get(i).getAnimate().getCurrentFrame(), g);
        }
    }

    public void drawBuildings(Graphics g) {
        for (int i = 0; i < buildings.size(); i++) {
            Tile.drawTile(buildings.get(i).getX(), buildings.get(i).getY(), buildings.get(i).getView(), g);
        }
    }

    public void drawmenu(int x, int y, int i, MouseManager mouseManager) {

        if (mouseManager.getXconst() >= x && mouseManager.getXconst() <= (x + 108)) {
            if (mouseManager.getYconst() >= y && mouseManager.getYconst() <= (y + 73)) {
                if (mouseManager.isLeftPressed()) {
                    System.out.println("1 is selected");
                    this.army.get(i).setSelected(false);
                    BuildingType = 1;
                }
            }
        } else if (mouseManager.getXconst() >= x && mouseManager.getXconst() <= (x + 2 * 100)) {
            if (mouseManager.getYconst() >= y && mouseManager.getYconst() <= (y + 73)) {
                if (mouseManager.isLeftPressed()) {
                    System.out.println("2 is selected");
                    this.army.get(i).setSelected(false);
                    BuildingType = 2;
                }
            }
        }

    }

    // function Update to update all the units frames and their animation
    // when moving
    public void playerUpdate(MouseManager mouseManager) throws LineUnavailableException, IOException {

        for(int u=0;u<buildings.size();u++){
            if(buildings.get(u).isCanTrain()){
            if ( (mouseManager.isLeftReleased() )
                && ((mouseManager.getMx() == buildings.get(u).getX()
                && mouseManager.getMy() == buildings.get(u).getY())
                    
                ||((mouseManager.getMy() == buildings.get(u).getY()-1)
                 && mouseManager.getMx() == buildings.get(u).getX())
                    
                || ((mouseManager.getMy() == buildings.get(u).getY())
                 && mouseManager.getMx() == buildings.get(u).getX()+1 )
                    
                 || ((mouseManager.getMy() == buildings.get(u).getY()-1)
                 && mouseManager.getMx() == buildings.get(u).getX()+1 )))
            {
            System.out.println("respawn solider");
            addUnit(new Archer(buildings.get(u).getX(),buildings.get(u).getY()+1));
        }
        }
        }

        for (int i = 0; i < army.size(); i++) {

            // updates the frames of each unit from the army
            if (!(army.get(i).isDying() && army.get(i).getAnimate().getIndex() == 12)) {
                army.get(i).getAnimate().update();
            }

            if (!army.get(i).getPath().isEmpty() && army.get(i).isMoving()) {

                // interpolate player motion to the next node in the path
                int x_start = army.get(i).getX() * T_W / 2 - army.get(i).getY() * T_W / 2;
                int y_start = army.get(i).getY() * T_H / 2 + army.get(i).getX() * T_H / 2;

                // both variables contains the first node that the player will go 
                // to in the path
                int path_x = army.get(i).getPath().get(0).col;
                int path_y = army.get(i).getPath().get(0).row;

                // sets the direction depending on the tile that the unit will go 
                // to
                army.get(i).setDirection(army.get(i), path_x, path_y);

                // isomteric coordinates used for the interpolation from a tile to another
                // using a t factor used from each unit
                int x_end = path_x * T_W / 2 - path_y * T_W / 2;
                int y_end = path_y * T_H / 2 + path_x * T_H / 2;

                // interpolation function used to set the isometric coords of each
                // unit so that the unit is drawn in a smooth animation while
                // moving
                army.get(i).setIso_x(lerp(army.get(i).getT(), x_start, x_end));
                army.get(i).setIso_y(lerp(army.get(i).getT(), y_start, y_end));

                // increments the t factor inside each unit until it reaches 1 ..
                // this t will be the factor used so it moves from one tile to another
                // till reaches 1 then it's end of 1 tile movement
                // t becomes 1 again and pop first element of path 
                army.get(i).setT(army.get(i).getT() + 0.023f);

                if (army.get(i).getT() > 1) {

                    army.get(i).setT(0f);
                    army.get(i).setX(path_x);
                    army.get(i).setY(path_y);
                    army.get(i).getPath().remove(0);

                    // when path ends , unit stops moving and the animation changes 
                    // to idle + last direction.
                    if (army.get(i).getPath().isEmpty()) {
                        army.get(i).setMoving(false);
                    }
                }
            }
            // if left clicked and the mouse isometric coordinates equals the coordinates of one of the
            // units then the unit is selected.
            if (mouseManager.isLeftPressed() && (mouseManager.getMx() == army.get(i).getX()) && mouseManager.getMy() == army.get(i).getY()) {
                //solider selected music
                m.resetsolidermusic();
                if (sound) {
                    m.playsolidermusic();
                }
                army.get(i).setUnitMenu(true);
                army.get(i).setSelected(true);
                mouseManager.setLeftPressed(false);
            }

            if (multiplayer && player2 != null) {
                if (army.get(i).isSelected() && mouseManager.isLeftReleased()) {
                    for (int r = 0; r < player2.army.size(); r++) {
                        if (mouseManager.getMx() == player2.getArmy().get(r).getX() && mouseManager.getMy() == player2.getArmy().get(r).getY()) {
                            army.get(i).setDirection(army.get(i), player2.getArmy().get(r).getX(), player2.getArmy().get(r).getY());
                            army.get(i).setAttack(true);
                            army.get(i).setMoving(false);
                            enemyIndex = r;// saving index of the unit will be attacked to change it to death animation
                            System.err.println("the index of attacked player " + r);
                        }
                    }
                }
            }

            //when right button pressed then the path will be generated to the target
            // target node is used with the coordinates of the mouse
            if (mouseManager.isRightPressed() && army.get(i).isSelected()
                    && mouseManager.getMy()<10
                    && mouseManager.getMy()>0
                    && mouseManager.getMx()<10
                    && mouseManager.getMx()>0) {
                
                army.get(i).requestPath(army.get(i), layermap[mouseManager.getMy()][mouseManager.getMx()], layermap);
                if (!army.get(i).getPath().isEmpty()) {
                    army.get(i).setMoving(true);
                    army.get(i).setAttack(false);
                }
                army.get(i).setSelected(false);
            }

            drawmenu(777, 503, i, mouseManager);

            // System.out.println(BuildingType);
            //BuildingType = drawmenu(p1mouse.getMouseX(),p1mouse.getMouseY(),i);
            if (mouseManager.isRightPressed() && army.get(i).isUnitMenu()) {

                System.out.println("Iam here");
                int x = mouseManager.getMx();
                int y = mouseManager.getMy();
                if (BuildingType == 1) {

                    System.out.println("Added Townhall");
                    addBuilding(new Townhall(x, y));
                    BuildingType = 0;
                } else if (BuildingType == 2) {
                    System.out.println("added barracks ");
                    addBuilding(new Barracks(x,y));
                    BuildingType = 0;
                }
            }
            if ((army.get(i).isDying() && army.get(i).getAnimate().getIndex() == 12)) {
                army.remove(i);
            }
            
                
        }

    }
    
    public int getResource1() {
        return resource1;
    }

    public void setResource1(int resource1) {
        this.resource1 = resource1;
    }

    public void addResource1(int add) {
        resource1 += add;
    }

    public int getResource2() {
        return resource2;
    }

    public void setResource2(int resource2) {
        this.resource2 = resource2;
    }

    public int getEnemyIndex() {
        return enemyIndex;
    }

    public void setEnemyIndex(int enemyIndex) {
        this.enemyIndex = enemyIndex;
    }

    public void addResource2(int add) {
        resource2 += add;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void addGold(int add) {
        gold += add;
    }

    public civilizations getCurrentCivil() {
        return currentCivil;
    }

    public void setCurrentCivil(civilizations current) {
        this.currentCivil = current;
    }

}
