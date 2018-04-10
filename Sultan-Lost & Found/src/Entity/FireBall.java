/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import TileMap.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
/**
 *
 * @author abdulrahim
 */
public class FireBall extends MapObject{
    
    //if fire ball hit something in the game
    private boolean hit;
    //if we need to remove the fireball from the game
    private boolean remove;
    
    private BufferedImage[] sprites;
    private BufferedImage[] hitSprites;
    
    public FireBall(TileMap tm, Boolean right)
    {
        super(tm);
        
        facingRight = true;
        
        moveSpeed = 3.8;
        
        //if its being fired to the right
        if(right) dx = moveSpeed;
        //if its being fired to the left
        else dx = -moveSpeed;
        
        width = 30;
        height = 30;
        
        //REAL WIDTH AND HEIGHT
        cwidth = 14;
        cheight = 14;
        
        //load sprites
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/Player/fireball.gif")
            );
            
            //We Know its a size of 4 because the first row of fireball.gif has 4 pictures
            sprites = new BufferedImage[4];
            for(int i = 0; i < sprites.length; i++){
                sprites[i] = spriteSheet.getSubimage(
                        i * width, 
                        0, 
                        width, 
                        height
                );
            }
            
            
            //We Know its a size of 3 because the second row of fireball.gif has 3 pictures
            hitSprites = new BufferedImage[3];
            for(int i = 0; i < hitSprites.length; i++){
                hitSprites[i] = spriteSheet.getSubimage(
                        i * width, 
                        height, 
                        width, 
                        height
                );
            }
            
            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(70);
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void setHit(){
        //so it doesn't keep hitting
        if(hit) return;
        
        hit = true;
        animation.setFrames(hitSprites);
        animation.setDelay(70);
        //stop it from moving
        dx = 0;
    }
    
    
    public boolean shouldRemove(){ return remove; }
    
    public void update() {
    
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        
        //if the fireball stops moving(when it hits a tile dx gets set to 0)
        //and hit is still marked as false. Perform the hit animation stuff.
        if( dx == 0 && !hit){
            setHit();
        }
        
        animation.update();
        //check if we need to take the fireball out of the game
        if(hit && animation.hasPlayedOnce()){
            remove = true;
        }
        
    }
    public void draw(Graphics2D g) {
        setMapPosition();
        
        //draw object facing left or right
        super.draw(g);
      
    }
    
    
}
