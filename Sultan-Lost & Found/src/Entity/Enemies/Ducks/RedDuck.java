/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity.Enemies.Ducks;

import Entity.Animation;
import Entity.Enemies.*;

import TileMap.TileMap;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 *
 * @author abdulrahim
 */
public class RedDuck extends Duck{
    //animations
    private BufferedImage[] sprites; 
    
   
    public RedDuck(TileMap tm){
    
        super(tm);
        
        //Ducks never fall. Only when killed.
        
        moveSpeed = 2;
        maxSpeed = 2;
        
        //width/height for the tile sheet "SQUARES??"
        width = 39;
        height = 33;
        //real width/height
        cwidth = 20;
        cheight = 20;
        
        health = maxHealth = 4;
        damage = 6;
        
        try{
        
            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                        "/Sprites/Enemies/Red Duck.gif"
                    )
            );

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
        left = true;
        facingRight = false;
         
    }
}
