package Game.display;


import Game.GameLoop;
import Game.Music.Music;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class Window {

    private JFrame frame;
    private String title;
    private int width, height;
    private static GameLoop map;
    public static Music m;
    public static Boolean sound;
    public Window(String name, int w, int h) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        m = new Music();
        sound =true;
        if(sound==true){
        m.playmainmenumusic();
        }
        this.title = name;
        this.height = h;
        this.width = w;
        CreateWindow();
    }

    private void CreateWindow() throws IOException {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        map = new GameLoop();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        File cursorimage = new File("cursor.png");
        String path = cursorimage.getAbsolutePath();
        java.awt.Image cursorimg = toolkit.getImage(path);
        Cursor c = toolkit.createCustomCursor(cursorimg, new Point(0,0),"cursorimg");

   
        frame.setCursor(c);
        frame.add(map);

        frame.setVisible(true);

    }
    
    public static GameLoop getMap() {
        return map;
    }

}
