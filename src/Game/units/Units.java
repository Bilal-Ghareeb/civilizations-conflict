package Game.units;

import Game.Dimentions;
import Game.Pathfinder.Node;
import Game.animation.*;
import static Game.units.direction.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

enum direction {RIGHT,LEFT,UP,DOWN};


public abstract class Units implements Dimentions,Serializable{
   
    private int x , y , health;
    private int Iso_x;
    private int Iso_y;
    
    // transient word means that those variables won't be written when using 
    // writeObject()method of the Object Stream 
    protected transient BufferedImage walkingRightSprite;
    protected transient BufferedImage walkingLeftSprite;
    protected transient BufferedImage walkingUpSprite;
    protected transient BufferedImage walkingDownSprite;
    protected transient BufferedImage idleRightSprite;
    protected transient BufferedImage idleLeftSprite;
    protected transient BufferedImage idleUpSprite;
    protected transient BufferedImage idleDownSprite;
    protected transient BufferedImage attackSprite1;
    protected transient BufferedImage attackSprite2;
    protected transient BufferedImage dyingSprite;
    
    protected transient BufferedImage[] idle_right_array;
    protected transient BufferedImage[] idle_down_array;
    protected transient BufferedImage[] idle_up_array;
    protected transient BufferedImage[] idle_left_array;
    protected transient BufferedImage[] walking_down_array;
    protected transient BufferedImage[] walking_up_array;
    protected transient BufferedImage[] walking_left_array;
    protected transient BufferedImage[] walking_right_array;
    protected transient BufferedImage[] dying_sprite;
    protected transient BufferedImage[] attack_Sprite1;
    protected transient BufferedImage[] attack_Sprite2;
    
    
    protected direction mydir = LEFT;
    //=========================================
    
    protected transient Animate down_walking;
    protected transient Animate left_walking;
    protected transient Animate right_walking;
    protected transient Animate up_walking;
    protected transient Animate down_idle;
    protected transient Animate left_idle;
    protected transient Animate right_idle;
    protected transient Animate up_idle;
    protected transient Animate death;
    protected transient Animate attack1;
    protected transient Animate attack2;
    protected transient Animate current;
    
    private boolean moving;
    private boolean Selected;
    private boolean UnitMenu;
    private boolean attack;
    private boolean dying;
    //==========================================

    
    public Units(int x , int y){
        UnitMenu = false;
        path = new ArrayList<Node>();
        t = 0;
        setMoving(false);
        Selected =false;
        this.x = x;
        this.y = y;
        this.Iso_x = x * T_W / 2 - y * T_W / 2;
        this.Iso_y = y * T_H / 2 + x * T_H / 2;
        this.attack=false;
        this.dying=false;
    }
    
    public void setDirection(Units unit,int pathx , int pathy){
        int x = unit.getX();
        int y = unit.getY();
        if (x - pathx > 0) {
            mydir=LEFT;
        } else if (x - pathx < 0) {
            mydir=RIGHT;
        } else if (y - pathy > 0) {
            mydir = UP;
        } else if (y - pathy < 0) {
            mydir = DOWN;
        }
    }
    
    protected Animate UnitStand(direction x){
        //----death animation soon----//
        if(dying){
            return death;
        }
        if(attack&& (x ==RIGHT || x==DOWN)){
            return attack1;
        }
        else if(attack&& (x ==LEFT|| x==UP)){
            return attack2;
        }
        
        //change animation depending on direction and moving state
        if(isMoving()&& x == RIGHT)
           return right_walking;
        else if(!(isMoving())&&x==RIGHT)
           return right_idle;
        else if(isMoving()&& x == LEFT)
            return left_walking;
         else if(!(isMoving())&& x == LEFT)
            return left_idle;
        else if(isMoving()&& x == UP)
            return up_walking;
        else if(!(isMoving()) && x == UP)
            return up_idle;
        else if(isMoving()&& x == DOWN)
            return down_walking;
        else if(!(isMoving()) && x == DOWN)
            return down_idle;
        else 
            return down_idle;
    }   
    
    public Animate getAnimate(){
        
        switch(mydir){
            case RIGHT: current =UnitStand(RIGHT); return current ;
            case UP: current =UnitStand(UP);return current;
            case DOWN:current = UnitStand (DOWN);return current;
            case LEFT : current = UnitStand(LEFT);return current;
            default:current = UnitStand(RIGHT);return current;
        }   
    }

    public Animate getCurrent() {
        return current;
    }

    public void setCurrent(Animate current) {
        this.current = current;
    }
    
    public static void SpritesCrop(int spriteNum , BufferedImage ar[] , BufferedImage sprite , int dim){
        for(int i =0;i<spriteNum;i++){
            ar[i] = sprite.getSubimage(dim*(i), 0, dim, dim);
        }
    }
    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public int getIso_x() {
        return Iso_x;
    }

    public void setIso_x(int Iso_x) {
        this.Iso_x = Iso_x;
    }

    public int getIso_y() {
        return Iso_y;
    }

    public void setIso_y(int Iso_y) {
        this.Iso_y = Iso_y;
    }
    
    public boolean isDying() {
        return dying;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    
    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean Selected) {
        this.Selected = Selected;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    
       public boolean isUnitMenu() {
        return UnitMenu;
    }

    public void setUnitMenu(boolean UnitMenu) {
        this.UnitMenu = UnitMenu;
    }
    
    
    public direction getMydir() {
        return mydir;
    }

    public void setMydir(direction mydir) {
        this.mydir = mydir;
    }
    
    //===================================================
    //=========== A* pathfinder Algorithm =======================
    // an algorithm and path for each unit so many moves happen
    // at same time 
    private Node target;
    private Node start;
    private float t;
    private ArrayList<Node>path;

    public ArrayList<Node> getPath() {
        return path;
    }

    public void setPath(ArrayList<Node> path) {
        this.path = path;
    }
    
    private Node [][] map1;
    
    void setMap(Node[][] map){
        map1 = new Node[map.length][map[0].length];
        for(int i = 0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                map1[i][j] = map[i][j];
            }
        }
     }
    
    
    
    private void findPath() {
        ArrayList<Node> open = new ArrayList<Node>();
        ArrayList<Node> closed = new ArrayList<Node>();

        open.add(start);

        while (!open.isEmpty()) {

            Node currentNode = open.get(0);

            for (int i = 1; i < open.size(); i++) {
                Node q = open.get(i);
                if (q.f_cost() < currentNode.f_cost()|| q.f_cost() == currentNode.f_cost()&& q.h_cost < currentNode.h_cost) {
                    currentNode = q;
                }
            }

            open.remove(currentNode);
            closed.add(currentNode);

            if (currentNode.equals(target)) {
                 retracePath();
                 return;
            }

            for (Node n : getNeighbours(currentNode)) {
                if (n.wall || closed.contains(n)) {
                    continue;
                }

                int newCostToNeighbour = currentNode.g_cost
                        + getDistance(currentNode, n);
                if (newCostToNeighbour < n.g_cost || !open.contains(n)) {
                    n.g_cost = newCostToNeighbour;
                    n.h_cost = getDistance(n, target);
                    n.parent = currentNode;
                    
                    if (!open.contains(n)) {
                        open.add(n);
                    }
                }
            }
        }

        System.out.println("Path not found	");
        
      
    }

    private ArrayList<Node> retracePath() {

        Node currentNode = target;

        while (!currentNode.equals(start)) {
            path.add(currentNode);
            currentNode = currentNode.parent;
        }

        //swap
        for (int i = 0; i < path.size() / 2; i++) {

            Node n = path.get(i);
            path.set(i, path.get(path.size() - i - 1));
            path.set(path.size() - i - 1, n);
            
        }
        return path;
    }

    // Manhattan distance
    private int getDistance(Node a, Node b) {
        int distX = Math.abs(a.col - b.col);
        int distY = Math.abs(a.row - b.row);

        if (distX > distY) {
            return  distY + (distX - distY);
        }
        return distX + (distY - distX);
    }

    private ArrayList<Node> getNeighbours(Node n) {
        ArrayList<Node> neighbours = new ArrayList<Node>();

        neighbours.add(map1[n.row - 1][n.col]);
        neighbours.add(map1[n.row + 1][n.col]);
        neighbours.add(map1[n.row][n.col - 1]);
        neighbours.add(map1[n.row][n.col + 1]);

        return neighbours;
    }
    
    public void requestPath(Units unit,Node target,Node map[][]) {
        int y = unit.getY();
        int x = unit.getX();
        setMap(map);
        if(y>1 && y < 9 && x > 1 && x < 9){
        this.start = map1[y][x];
        this.target = target;
        findPath();
        if (path.isEmpty()) {
            return;
        }
        }
        return;
    }
    
    public float getT(){
        return t;
    }
    
    public void setT(float t){
        this.t = t;
    }
    
       public static int lerp(float t,int start, int end) {
        return (int) (start + ((end - start))*t);
    }
 }
