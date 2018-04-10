/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity.Enemies;

import Entity.*;

import TileMap.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 *
 * @author abdulrahim
 */
public class Duck extends Enemy{
    
    //animations
    private BufferedImage[] sprites; 
    
   
    public Duck(TileMap tm){
    
        super(tm);         
    }
    
    
    private void getNextPosition() {
    
        //movement
        if(left){
            dx -= moveSpeed;
            if(dx < -maxSpeed){
                dx = -maxSpeed;
            }
        }
        
        else if(right){
            dx += moveSpeed;
            if(dx > maxSpeed){
                dx = maxSpeed;
            }
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
        
        //update animation
        animation.update();
    }
    
    
    public void draw(Graphics2D g) {
        
        setMapPosition();
        super.draw(g);
    }
    
}
