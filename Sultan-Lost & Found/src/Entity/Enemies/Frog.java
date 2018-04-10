/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity.Enemies;

import Entity.*;

import TileMap.TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 *
 * @author abdulrahim
 */
public class Frog extends Enemy {
    
    //animations
    private ArrayList<BufferedImage[]> sprites; 
    
    //animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 0;
    private static final int JUMPING = 1;
    private static final int FALLING = 1;
    
    public Frog(TileMap tm) {
        
        super(tm);
        
        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;
        
        //width/height for the tile sheet "SQUARES??"
        width = 39;
        height = 34;
        //real width/height
        cwidth = 30;
        cheight = 30;
        
        health = maxHealth = 2;
        damage = 1;
        
        //load sprites
        try{
        
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                        "/Sprites/Enemies/GreenFrog.gif"
                    )
            );
            
            // we know size =  3 because the only row of the slugger gif 
            // has 3 sprites for the slugger
            sprites = new ArrayList<BufferedImage[]>();
            
            BufferedImage bi[] = new BufferedImage[3];
            
            for (int i = 0; i < bi.length; i++){
            
                bi[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height);
            }
            
            BufferedImage idleBI[] = {bi[0], bi[2]};
            BufferedImage jumpfallBI[] = {bi[1]};
            
            sprites.add(idleBI);
            sprites.add(jumpfallBI);
            
        }
        
         
        catch(Exception e){
            e.printStackTrace();
        }
        
        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(1000);
        
        //snail will head in right direction. Not necessary, but whatever
        right = true;
        jumping = true;
        facingRight = true;
    }
    
    
    private void getNextPosition() {
    
        //movement
        if(left){
            dx -= moveSpeed;
            if(dx < -maxSpeed){
                dx = -maxSpeed;
                jumping = true;
            }
        }
        
        else if(right){
            dx += moveSpeed;
            if(dx > maxSpeed){
                dx = maxSpeed;
                jumping = true;
            }
        }
        
        //jumping
        if(jumping && !falling){
            
            dy = jumpStart;
            falling = true;
        }
        
        //falling
        if(falling){
            //This particular enemy never jumps only falls
            dy += fallSpeed;
            
            if(dy > 0) jumping = false;
            
            //set max fall speed
            if(dy > maxFallSpeed) dy = maxFallSpeed;
        }
    }
    
    public void update() {
    
        //update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        
        //check flinching
        if(flinching){
            //check how much time has gone by since we started flinching
            long elapsed = 
                    (System.nanoTime() - flinchTimer) / 1000000;
            
            if(elapsed > 400) {
                flinching = false;
            }
        }
        
        //if it hits a wall, then go the other direction.
        //our code automatically sets dx = 0 if it hits a wall
        if(right && dx == 0) {
            right = false;
            //start moving in opposite direction
            left = true;
            
            facingRight = false;
        }
        
        else if(left && dx == 0) {
            left = false;
            //start moving in opposite direction
            right = true;
            
            facingRight = true;
        }
        
                //if we are falling
                else if(dy > 0){
                    //if we are regular falling
                    if(currentAction != FALLING){
                        currentAction = FALLING;
                        animation.setFrames(sprites.get(FALLING));
                        animation.setDelay(-1);
                        width = 30; 
                    }

                }
                    //if we are jumping
                    else if(dy < 0){
                        if(currentAction != JUMPING){
                                    currentAction = JUMPING;
                                    animation.setFrames(sprites.get(JUMPING));
                                    //only one animation so delay is unessecary
                                    animation.setDelay(-1);
                                    width = 30; 
                            }
                    }
        
        
        //if we are moving
                        else if(left || right){
                            if(currentAction != WALKING){
                                currentAction = WALKING;
                                animation.setFrames(sprites.get(WALKING));
                                animation.setDelay(40);
                                width = 30; 
                            }

                        }
                        
                            //idle frames (NOT DOING ANYTHING)
                            else{
                                if(currentAction != IDLE){
                                currentAction = IDLE;
                                animation.setFrames(sprites.get(IDLE));
                                animation.setDelay(400);
                                width = 30; 
                                }
                            }
        
        //update animation
        animation.update();
    }
    
    public void draw(Graphics2D g) {
        
        setMapPosition();
        
        super.draw(g);
    }
}
