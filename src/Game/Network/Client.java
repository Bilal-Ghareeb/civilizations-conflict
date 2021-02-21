/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game.Network;

import static Game.GameLoop.player;
import Game.Player;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Jupitar-Orbit
 */
public class Client {

    private Socket socket;
    int port; 
    
    private ObjectOutputStream socketoutput;
    private ObjectInputStream socketinput;

    public Client(int port) throws IOException {
        this.port = port;
        socket = new Socket("localhost", this.port);
        
       this.socketoutput = new ObjectOutputStream(socket.getOutputStream());
       this.socketinput= new ObjectInputStream(socket.getInputStream());

    }

    public Player ClientSR(boolean multi) throws IOException, ClassNotFoundException {

        try{
        Player player2 = null;
        // player is a public static instance (current user instance)
        if (multi) {
            socketoutput.reset();
            socketoutput.writeUnshared(player);
            socketoutput.flush();
            
            System.out.println("data sent :)");
            
            player2 = (Player) socketinput.readUnshared();
            System.out.println("data read from other side <-");
            
            return player2;
        }
        
         }
        catch(SocketException e){
            System.err.println("Connection reset");
        }
        return null;
    }
}
