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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Jupitar-Orbit
 */
public class GameServer {

    private ServerSocket server;
    private Socket socket;
    private ObjectInputStream socketinput;
    private ObjectOutputStream socketoutput;

    public GameServer(int port) throws IOException {
        this.server = new ServerSocket(port);
        System.out.println("Waiting for connection");
        this.socket = server.accept();
        System.out.println("Connected to other side ->  ");

        this.socketinput = new ObjectInputStream(socket.getInputStream());

        this.socketoutput = new ObjectOutputStream(socket.getOutputStream());

    }

    public Player ServerSR(boolean multi) throws IOException, ClassNotFoundException {

        try{
        Player player2 = null;

        if (multi) {
           
            player2 = (Player) socketinput.readUnshared();
            System.out.print(" player 2, already read ");
            socketoutput.reset();
            socketoutput.writeUnshared(player);
           
            System.out.println("current player data sent to client");
            socketoutput.flush();
            return player2;
        }
        }
        catch(SocketException e){
            System.err.println("Connection reset");
        }
       
        return null;
    }
    
}
