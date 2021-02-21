package Game.States;

import Game.Dimentions;
import static Game.GameLoop.*;
import static Game.Player.civilizations.*;
import Game.display.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JTextField;
import static Game.GameLoop.p1mouse;
import static Game.GameLoop.player;
import static Game.GameLoop.state;
import Game.Network.*;
import Game.Player;
import Game.buildings.*;
import static Game.display.Assets.Tiles;
import static Game.display.Window.m;
import Game.input.Load;
import static Game.input.Load.*;
import Game.units.Archer;
import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;

enum civilizations {
    Knights, Nomads
};

public class StateManager implements myListener, Dimentions {

    private JTextField name = new JTextField("player");
    private JTextField cardNumber = new JTextField("XXXX-XXXX-XXXX-XXXX");
    private JTextField cardHolderName = new JTextField("");
    private JTextField expiryDate = new JTextField("XX/XX");
    private JTextField cardVerificationCode = new JTextField("XXX");
    private JTextField deposit = new JTextField();

    public static boolean multiplayer;
    public static boolean bgChange = false;
    private int balance = 0;
    // player 2 is used  only for drawing and data is recieved 
    //from the other socket side
    private GameServer server;
    private Client client;
    public static Player player2;

    public int map = 1;

    private boolean trueserver = true;

    public void drawState(String state, Graphics g) throws IOException, ClassNotFoundException, LineUnavailableException {

        if (state == "menu") {
            drawMenuState(g);
        } else if (state == "mode") {
            drawModeState(g);
        } else if (state == "civilization") {
            drawCivState(g);
        } else if (state == "game") {
            drawGState(g);
        } else if (state == "options") {
            drawOPState(g);
        } else if (state == "credits") {
            drawCRState(g);
        } else if (state == "shop") {
            drawSHState(g);
        } else if (state == "Multi") {
            drawMultiScreen(g);
        } else if (state == "map") {
            drawMapState(g);
        }

        if (p1mouse.isLeftReleased() == true) {
            p1mouse.setLeftReleased(false);
        }
    }

    public void drawButton(Graphics g, BufferedImage button, BufferedImage buttonPushed, int x, int y, int w, int h, myListener listener) {
        g.drawImage(button, x, y, null);
        if (p1mouse.getMouseX() >= x && p1mouse.getMouseX() <= (x + w)) {
            if (p1mouse.getMouseY() >= y && p1mouse.getMouseY() <= (y + h)) {
                g.drawImage(buttonPushed, x, y, null);
                if (p1mouse.isLeftReleased()) {
                    listener.runAction();
                }
            }
        }
    }

    public void drawMenuState(Graphics g) {
        trueserver = false;
        g.drawImage(Assets.background, 0, 0, null);
        g.drawImage(Assets.logo, 300, 50, null);

        drawButton(g, Assets.start, Assets.start_pushed, 440, 260, 120, 64, () -> {
            state = "mode";
            Window.getMap().add(name);
        });

        drawButton(g, Assets.options, Assets.options_pushed, 440, 340, 120, 64, () -> {
            state = "options";
        });

        drawButton(g, Assets.quit, Assets.quit_pushed, 440, 420, 120, 64, () -> {
            System.exit(0);
        });

        drawButton(g, Assets.help, Assets.help_pushed, 950, 650, 32, 32, () -> {
            state = "credits";
        });
        drawButton(g, Assets.shop, Assets.shop_pushed, 18, 650, 32, 32, () -> {
            state = "shop";
            Window.getMap().add(cardNumber);
            Window.getMap().add(deposit);
            Window.getMap().add(cardHolderName);
            Window.getMap().add(expiryDate);
            Window.getMap().add(cardVerificationCode);
        });
    }

    public void drawModeState(Graphics g) {

        g.drawImage(Assets.background, 0, 0, null);

        name.setBounds(360, 460, 300, 30);

        drawButton(g, Assets.multiplayer, Assets.multiplayer_pushed, 320, 350, 120, 64, () -> {
            multiplayer = true;
            Window.getMap().remove(name);
            player = new Player(tiles);
            state = "Multi";
        });
        drawButton(g, Assets.singleplayer, Assets.singleplayer_pushed, 560, 350, 120, 64, () -> {

            
            //Textfield for taking name of player;
            player.setName(name.getText());
            Window.getMap().remove(name);
            multiplayer = false;
            player.getArmy().clear();
            player.getBuildings().clear();
            
            player.getBuildings().add(new Townhall(4,5));
            player.getArmy().add(new Archer(3, 5));
            state = "map";
        });

        drawButton(g, Assets.profile, Assets.profile_pushed, 950, 650, 32, 32, () -> {
            try {

                ObjectInputStream input = new ObjectInputStream(new FileInputStream(name.getText() + ".bin"));

                player = (Player) input.readObject();
                player.setArmy(renewarmy(player.getArmy()));
                player.setBuildings(renewbuildings(player.getBuildings()));
                player.setName(name.getText());

                input.close();
                Window.getMap().remove(name);
            } catch (IOException ex) {
                Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            state = "game";

        });
        drawButton(g, Assets.back, Assets.back_pushed, 15, 15, 32, 32, () -> {
            state = "menu";
            Window.getMap().remove(name);
        });
    }

    public void drawCivState(Graphics g) {
        g.drawImage(Assets.castle, 0, 0, null);
        g.drawImage(Assets.desert_tent, 500, 0, null);
        g.drawImage(Assets.knights_description, 50, 560, null);
        g.drawImage(Assets.nomads_description, 550, 560, null);

        drawButton(g, Assets.knights, Assets.knights_pushed, 190, 40, 120, 64, () -> {
            
            try {
                m.stopbgmusic();
                if (sound) {
                    m.playbgmusic();
                }
                m.stopmainmenumusic();
            } catch (IOException ex) {
                Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
            }

            player.setCurrentCivil(Knights);
            state = "game";
        });
        drawButton(g, Assets.nomads, Assets.nomads_pushed, 690, 40, 120, 64, () -> {
            
            try {
                m.stopbgmusic();
                if (sound) {
                    m.playbgmusic();
                }
                m.stopmainmenumusic();
            } catch (IOException ex) {
                Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
            }

            player.setCurrentCivil(Nomads);
            state = "game";
        });
        drawButton(g, Assets.back, Assets.back_pushed, 15, 15, 32, 32, () -> {

            multiplayer = false;
            state = "mode";
        });
    }

    //|=============================================================|
    //|=====================   Game State   ========================|
    //|=============================================================|
    public void drawGState(Graphics g) throws IOException, ClassNotFoundException {
        
        g.drawImage(Assets.inGameMenu, 0, -30, null);
        
        // writing resources and updating it in menu
        g.setColor(Color.WHITE);
        g.setFont(new Font("default", Font.PLAIN, 11));
        g.drawString(String.valueOf(player.getResource1()), 700, 537);
        g.drawString(String.valueOf(player.getResource2()), 700, 560);
        
        g.setFont(new Font("default", Font.PLAIN, 13));
        g.setColor(Color.YELLOW);
        g.drawString("Gold : " + String.valueOf(player.getGold()), 660, 585);
        for (int i = 0; i < player.getArmy().size(); i++) {
            if (player.getArmy().get(i).isUnitMenu()) {
                g.drawImage(Assets.inGameMenu2, 0, -30, null);
                g.drawImage(Assets.menuBuildings.getSubimage(64, 0, 64, 64), 800, 510, null);
                g.drawImage(Assets.menuBuildings.getSubimage(64 * 2, 0, 64, 64), 900, 510, null);
                
                g.setFont(new Font("default", Font.PLAIN, 12));
                g.setColor(Color.CYAN);
                g.drawString("Townhall", 810, 580);
                g.drawString("Barracks", 910, 580);
            }
        }
        //----------------------------------------------------------------------------------------------------------------------------------//
        // Pause Button closes the multiplayer if it is working and saves profile to binary file 
        // with the name of the player assigned inside player class then 
        // returns to Main Menu of the game;

        drawButton(g, Assets.pause, Assets.pause_pushed, 15, 15, 32, 32, () -> {

            try {
                m.stopbgmusic();
                if (sound) {
                    m.playmainmenumusic();
                }
            } catch (IOException ex) {
                Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
            }

            multiplayer = false;
            state = "menu";
            try {
                ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(player.getName() + ".bin"));
                output.writeObject(player);
            } catch (FileNotFoundException ex) {
                System.err.println(ex);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        });

        g.translate(477, 320 - tiles.length * T_H / 2);

        if (multiplayer) {
            map = 1;
        }
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                if (map == 1) {
                    draw.drawTile(col, row, Assets.grass, g);
                } else if(map==2){
                    draw.drawTile(col, row, Tiles[2], g);
                    if(col == 7 && row == 5){
                        draw.drawTile(col, row, Tiles[6], g);
                    }
                }
            }
        }
//=========================Condition for the Server ==================        
        if (multiplayer && trueserver) {
            int Ucurrent;
            int Bcurrent;
            if (player2 == null) {
                Ucurrent = -1;
                Bcurrent = -1;
                player2 = new Player(tiles);
            } else {
                Ucurrent = player2.getArmy().size();
                Bcurrent = player2.getBuildings().size();
            }

            Player temp = null;
            temp = server.ServerSR(true);
            if (temp == null) {
                multiplayer = false;
            }

            if (temp != null) {
                if (temp.getArmy().size() != Ucurrent) {
                    player2.setArmy(renewarmy(temp.getArmy()));
                } else if (temp.getArmy().size() == Ucurrent) {
                    player2.setArmy(Load.refreshArmy(temp.getArmy(), player2.getArmy()));
                }

                if (temp.getBuildings().size() != Bcurrent) {
                    player2.setBuildings(renewbuildings(temp.getBuildings()));
                    player.setLayermap(Load.mixLayer(player.getLayermap(), temp.getLayermap()));
                }
                player2.setEnemyIndex(temp.getEnemyIndex());
            }
        } //=============Condition for the Client =====================
        else if (multiplayer && (!trueserver)) {
            int Ucurrent;
            int Bcurrent;
            if (player2 == null) {
                Ucurrent = -1;
                Bcurrent = -1;
                player2 = new Player(tiles);
            } else {
                Ucurrent = player2.getArmy().size();
                Bcurrent = player2.getBuildings().size();
            }

            Player temp = null;
            temp = client.ClientSR(true);
            if (temp == null) {
                multiplayer = false;
            }
            if (temp != null) {
                if (temp.getArmy().size() != Ucurrent) {
                    player2.setArmy(renewarmy(temp.getArmy()));
                } else if (temp.getArmy().size() == Ucurrent) {
                    player2.setArmy(Load.refreshArmy(temp.getArmy(), player2.getArmy()));
                }

                if (temp.getBuildings().size() != Bcurrent) {
                    player2.setBuildings(renewbuildings(temp.getBuildings()));
                    player.setLayermap(Load.mixLayer(player.getLayermap(), temp.getLayermap()));
                }
                player2.setEnemyIndex(temp.getEnemyIndex());
            }
        }

        player.drawBuildings(g);
        player.drawArmy(g);

        if (multiplayer && player2 != null) {
            player2.drawArmy(g);
            player2.drawBuildings(g);

            if (player2.getEnemyIndex() != -1 && player.getArmy().size() > 0) {
                player.getArmy().get(player2.getEnemyIndex()).setDying(true);
            }
        }
    }

    public void drawOPState(Graphics g) throws IOException, LineUnavailableException {
        g.drawImage(Assets.background, 0, 0, null);
        drawButton(g, Assets.back, Assets.back_pushed, 15, 15, 32, 32, () -> {
            state = "menu";
        });
        if (sound == false) {
            drawButton(g, Assets.sound_off, Assets.sound_on, 100, 100, 32, 32, () -> {
                sound = !sound;
                try {
                    m.playmainmenumusic();
                } catch (IOException ex) {
                    Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } else {

            drawButton(g, Assets.sound_on, Assets.sound_on, 100, 100, 32, 32, () -> {
                try {
                    m.stopmainmenumusic();
                } catch (IOException ex) {
                    Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                sound = !sound;
            });
        }
        drawButton(g, Assets.background1, Assets.background1_pushed, 200, 85, 120, 64, () -> {
            Assets.bg = 1;
            bgChange = true;
        });
        drawButton(g, Assets.background2, Assets.background2_pushed, 350, 85, 120, 64, () -> {
            Assets.bg = 2;
            bgChange = true;
        });
        drawButton(g, Assets.background3, Assets.background3_pushed, 500, 85, 120, 64, () -> {
            Assets.bg = 3;
            bgChange = true;
        });
        drawButton(g, Assets.background4, Assets.background4_pushed, 650, 85, 120, 64, () -> {
            Assets.bg = 4;
            bgChange = true;
        });
    }

    public void drawCRState(Graphics g) {
        g.drawImage(Assets.background, 0, 0, null);
        drawButton(g, Assets.back, Assets.back_pushed, 15, 15, 32, 32, () -> {
            state = "menu";
        });
        g.drawImage(Assets.credits, 300, 50, null);
    }

    public void drawSHState(Graphics g) {
        g.drawImage(Assets.background, 0, 0, null);
        g.setColor(Color.WHITE);
        g.drawString("Card Number", 360, 450);
        cardNumber.setBounds(360, 460, 200, 30);
        g.drawString("Deposit Money", 580, 450);
        deposit.setBounds(580, 460, 80, 30);
        drawButton(g, Assets.shop, Assets.shop_pushed, 670, 460, 32, 32, () -> {
            try {
                if (Integer.parseInt(deposit.getText()) < 0) {
                    System.out.println("enter a positive number");
                } else {
                    balance += Integer.parseInt(deposit.getText());
                }
                deposit.setText("");
            } catch (Exception e) {
                System.out.println("enter only numbers");
                deposit.setText("");
            }

        });
        g.drawString("Card Holder's Name", 360, 510);
        cardHolderName.setBounds(360, 520, 200, 30);
        g.drawString("Expiry Date", 360, 570);
        expiryDate.setBounds(360, 580, 200, 30);
        g.drawString("Card Verification Code", 360, 630);
        cardVerificationCode.setBounds(360, 640, 200, 30);

        g.drawImage(Assets.visa, 700, 550, null);

        g.drawImage(Assets.wood, 100, 100, null);
        g.drawImage(Assets.stone, 400, 100, null);
        g.drawImage(Assets.gold, 700, 100, null);

        g.setColor(Color.DARK_GRAY);

        g.drawImage(Assets.emptystone, 130, 320, null);
        g.drawString("Buy 500 Wood", 140, 343);
        g.drawString("for $50", 160, 355);
        drawButton(g, Assets.shop, Assets.shop_pushed, 240, 330, 32, 32, () -> {
            if (balance >= 50) {
                balance = balance - 50;
                player.addResource1(500);

            } else {
                System.out.println("You don't have enough money.");
            }
        });
        g.drawImage(Assets.emptystone, 430, 320, null);
        g.drawString("Buy 500 Stone", 440, 343);
        g.drawString("for $50", 460, 355);
        drawButton(g, Assets.shop, Assets.shop_pushed, 540, 330, 32, 32, () -> {
            if (balance >= 50) {
                balance = balance - 50;
                player.addResource2(500);
            } else {
                System.out.println("You don't have enough money.");
            }
        });
        g.drawImage(Assets.emptystone, 730, 320, null);
        g.drawString("Buy 500 Gold", 740, 343);
        g.drawString("for $50", 760, 355);
        drawButton(g, Assets.shop, Assets.shop_pushed, 840, 330, 32, 32, () -> {
            if (balance >= 50) {
                balance = balance - 50;
                player.addGold(500);
            } else {
                System.out.println("You don't have enough money.");
            }
        });

        g.drawImage(Assets.emptystone, 880, 20, null);
        g.drawString("$" + balance, 890, 50);

        drawButton(g, Assets.back, Assets.back_pushed, 15, 15, 32, 32, () -> {
            Window.getMap().remove(cardNumber);
            Window.getMap().remove(deposit);
            Window.getMap().remove(cardHolderName);
            Window.getMap().remove(expiryDate);
            Window.getMap().remove(cardVerificationCode);
            state = "menu";
        });
    }

    @Override
    public void runAction() {

    }

    private void drawMultiScreen(Graphics g) {
        g.drawImage(Assets.background, 0, 0, null);
        drawButton(g, Assets.back, Assets.back_pushed, 15, 15, 32, 32, () -> {
            state = "menu";
            multiplayer = false;
        });

        drawButton(g, Assets.emptystone, Assets.emptystone, 320, 350, 120, 64, () -> {
            System.out.println("SelectMuliplayer and creating a client ");

            multiplayer = true;
            trueserver = false;

            try {
                client = new Client(4000);
            } catch (IOException ex) {
                System.err.println("Client didn't initialize in StateM class");
                Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            state = "game";
            player.addBuilding(new Townhall(2, 7));
            player.addUnit(new Archer(2, 8));
        });
        g.setColor(Color.CYAN);
        g.setFont(new Font("default", Font.BOLD, 16));
        g.drawString("Join", 350, 380);

        g.setColor(Color.GREEN);
        g.setFont(new Font("default", Font.BOLD, 24));
        g.drawString(" \" Make sure to host first before joining from other game window \"", 120, 565);

        drawButton(g, Assets.emptystone, Assets.emptystone, 560, 350, 120, 64, () -> {
            multiplayer = true;
            trueserver = true;

            try {
                server = new GameServer(4000);
            } catch (IOException ex) {
                System.err.println("Server didn't initialize in StateM class");
                Logger.getLogger(StateManager.class.getName()).log(Level.SEVERE, null, ex);
            }

            player.addBuilding(new Townhall(7, 2));
            player.addUnit(new Archer(7, 3));
            state = "game";

        });
        g.setColor(Color.CYAN);
        g.setFont(new Font("default", Font.BOLD, 16));
        g.drawString("Host", 590, 380);
    }

    public void drawMapState(Graphics g) {
        g.drawImage(Assets.background, 0, 0, null);
        drawButton(g, Assets.back, Assets.back_pushed, 15, 15, 32, 32, () -> {
            Window.getMap().add(name);
            multiplayer = false;
            state = "mode";
        });
        drawButton(g, Assets.map1, Assets.map1_pushed, 320, 350, 120, 64, () -> {
            map = 1;
            state = "civilization";
            multiplayer = false;
        });
        drawButton(g, Assets.map2, Assets.map2_pushed, 560, 350, 120, 64, () -> {
            map = 2;
            state = "civilization";
            multiplayer = false;
        });
    }
}
