package Engine;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {

    private ArrayList<AnimFrame> frames;
    private int currFrameIndex;
    private long animTime;
    private long totalDuration;

    public Animation(){
        frames = new ArrayList<>();
        totalDuration = 0;
        start();
    }

    /**
     * Adds every frame for the player and enemy
     * @param image
     * @param duration
     */
    public synchronized void addFrame(BufferedImage image, long duration){
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }

    /**
     * Initializing the time for animation loop to start
     */
    public synchronized void start(){
        animTime = 0;
        currFrameIndex = 0;
    }

    /**
     * The update method for the whole game
     * @param elapsedTime
     */
    public synchronized void update(long elapsedTime){
        if (frames.size() > 1){
            animTime += elapsedTime;
            if (animTime >= totalDuration){
                animTime = animTime % totalDuration;
                currFrameIndex = 0;
            }
            while (animTime > getFrame(currFrameIndex).endTime){
                currFrameIndex++;
            }
        }
    }

    /**
     * Returns the image
     * @return
     */
    public synchronized BufferedImage getImage(){
        if (frames.size() == 0){
            return null;
        }
        else{
            return getFrame(currFrameIndex).image;
        }
    }

    private AnimFrame getFrame(int i){
        return (AnimFrame) frames.get(i);
    }

    private class AnimFrame{

        BufferedImage image;
        long endTime;

        public AnimFrame(BufferedImage image, long endTime){
            this.image = image;
            this.endTime = endTime;
        }
    }

}
