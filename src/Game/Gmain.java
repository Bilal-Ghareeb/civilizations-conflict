package Game;

import Game.display.Window;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Gmain {

    public static void main(String[] args) throws InterruptedException, IOException, UnsupportedAudioFileException, LineUnavailableException {
        
        new Window("Strategy Game", 1000, 720);

    }
}
