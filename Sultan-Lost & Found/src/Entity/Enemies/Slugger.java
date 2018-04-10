/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity.Enemies;

import Entity.*;

import TileMap.*;

import java.awt.image.BufferedImage;
import java.awt.*;
        
import javax.imageio.ImageIO;
/**
 *
 * @author abdulrahim
 */
public class Slugger extends Enemy{
    
    //animations
    private BufferedImage[] sprites; 
    
    public Slugger(TileMap tm) {
        
        super(tm);
        
        moveSpeed = 0.3;
        maxSpeed = 0.3;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;
        
        //width/height for the tile sheet "SQUARES??"
        width = 30;
        height = 30;
        //real width/height
        cwidth = 20;
        cheight = 20;
        
        health = maxHealth = 2;
        damage = 1;
        
        //load sprites
        try{
        
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                        "/Sprites/Enemies/slugger.gif"
                    )
            );
            
            // we know size =  3 because the only row of the slugger gif 
            // has 3 sprites for the slugger
            sprites = new BufferedImage[3];
            
            for (int i = 0; i < sprites.length; i++){
            
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height);
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);
        
        //snail will head in right direction. Not necessary, but whatever
        right = true;
        facingRight = true;
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
        
        //falling
        if(falling){
            //This particular enemy never jumps only falls
            dy += fallSpeed;
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
        
        // notOnScreen() is not necessary, 
        // since all rendering outside of the BufferedImage is automatically ignored.
        // if(notOnScreen()) return;
        
        setMapPosition();
        
        super.draw(g);
    }
}
