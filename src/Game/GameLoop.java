package Game;

import Game.Network.GameServer;
import Game.States.StateManager;
import static Game.States.StateManager.*;
import Game.tiles.Tile;
import Game.input.MouseManager;
import Game.display.Assets;
import Game.units.*;
import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JPanel;

public class GameLoop extends JPanel implements Runnable {

    public static String state;
    public static MouseManager p1mouse;
    public static Tile draw;
    //sound button
    public static Boolean sound;
    
    private StateManager stateManager = new StateManager();
    //========================================
    private Thread thread;
    private boolean running;
    private final double updateRate = 1.0d / 60.0d;
    //=======================================

    public static Player player;
    
    public static int[][] tiles = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 2, 3, 2, 2, 2, 2, 2, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 2, 2, 2, 2, 2, 2, 2, 2, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},};

    public GameLoop() throws IOException {
        multiplayer = false;
        player = new Player(tiles);
        // initialize running used for the run of thread function
        player2 = null;
        running = false;
        //Setting running to true and starting the thread
        start();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.fillRect(0, 0, 1000, 720);

        try {
            // Satisifes the state according to state variable initialized as "menu"
            stateManager.drawState(state, g);
        } catch (IOException ex) {
            Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start() {

        //If it's already running then return without staring it again
        if (running) {
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void update() throws LineUnavailableException, IOException {
        // updates each unit frame and 
        // updates the motion if exists for each unit
        // updates the layer map of Obstacles
        if(state=="game"){
        player.playerUpdate(p1mouse);
        }
        
        if(state=="game"&& multiplayer && player2!= null){
            for (int i = 0; i < player2.getArmy().size(); i++) {
             if(!( player2.getArmy().get(i).isDying() &&  player2.getArmy().get(i).getAnimate().getIndex()==12)){
               player2.getArmy().get(i).getAnimate().update();
           }
                
            }
        }
    }

    public void Render() {
        repaint();
    }

    @Override
    public void run() {
        init();
        double accumulator = 0;

        long currenttime, lastupdate = System.currentTimeMillis();

        while (running) {
            if (bgChange == true) {
                Assets.init();
                bgChange = false;
            }

            currenttime = System.currentTimeMillis();
            double lastrendertimeinsec = (currenttime - lastupdate) / 1000d;
            accumulator += lastrendertimeinsec;
            lastupdate = currenttime;

            while (accumulator > updateRate) {
                try {
                    update();
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
                }
                accumulator -= updateRate;
            }

            Render();
        }
    }

    private void init() {

        p1mouse = new MouseManager();
        addMouseListener(p1mouse);
        addMouseMotionListener(p1mouse);
        draw = new Tile();

        sound = true;

        state = "menu";

        //initializes all the sprites of the buildings , tiles , trees 
        // and buttons
        Assets.init();
    }

}
