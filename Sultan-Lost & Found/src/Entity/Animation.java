/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.awt.image.BufferedImage;
/**
 *
 * @author abdulrahim
 */
public class Animation {
    
    //array of buffered images to hold all the frames
    private BufferedImage[] frames;
    private int currentFrame;
    
    //timer between frames
    private long startTime;
    //how long between each frame
    private long delay;
    
    //tells us if animation has been played already. If it has looped. 
    //Useful for attack animation, where attack animation needs to only happen one time
    private boolean playedOnce;
    
    
    public Animation(){
        playedOnce = false;
    }
    
    
    public void setFrames(BufferedImage[] frames)
    {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        playedOnce = false;
    }
    
    
    public void setDelay (long d) { delay = d; }
    public void setFrame (int i)  { currentFrame = i; }
    
    //when to change frame
    public void update(){
        if(delay == -1) return;
        
        //how long has it been since last frame came up
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        
        if(elapsed > delay){
            //move to next frame
            currentFrame++;
            //reset the timer
            startTime = System.nanoTime();
        }
        
        //make sure we don't go past the amount of frames we have
        if(currentFrame == frames.length){
            currentFrame = 0;
            //this means that the animation has played once
            playedOnce = true;
            
        }
       
    }
    
    
    public void deathUpdate(){
        
        if(delay == -1) return;
        
        //how long has it been since last frame came up
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        
        if(elapsed > delay && currentFrame != (frames.length - 1)){
            //move to next frame
            currentFrame++;
            //reset the timer
            startTime = System.nanoTime();
        }
        
        if(currentFrame == frames.length){
            currentFrame = frames.length - 1;
        }
        
    }
    
            
    //get image number or index
    public int getFrame() { return currentFrame; }
    //get image we need to draw
    public BufferedImage getImage() { return frames[currentFrame]; }
    public boolean hasPlayedOnce() { return playedOnce; }
    
}
