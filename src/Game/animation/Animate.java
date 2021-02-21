package Game.animation;

import java.awt.image.BufferedImage;

public class Animate {

    private final int SPEED = 70;
    private BufferedImage[] frames;
    private int length;
    private int index;

    
    private long t, dt;

    public Animate(BufferedImage[] frames) {

        this.frames = frames;
        length = frames.length;
        index = 0;
        t = System.currentTimeMillis();
        dt = 0;

    }

    public void update() {

        dt += System.currentTimeMillis() - t;
        t = System.currentTimeMillis();

        if (dt > SPEED) {
            index++;
            dt = 0;
        }
        if (index == length - 1) {
            reset();
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }

    public void reset() {
        index = 0;
    }
    public int getIndex() {
        return index;
    }
}
