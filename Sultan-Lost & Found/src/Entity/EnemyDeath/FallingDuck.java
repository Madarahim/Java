/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity.EnemyDeath;

import Entity.*;

import TileMap.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author abdulrahim
 */
public class FallingDuck extends Death{

    //when ducks die, all they do is fall off screen.
    private double fallSpeed;
    
    private String color;
    
    public FallingDuck(int x, int y, String color){
             
        this.x = x;
        this.y = y;
        
        width = 37;
        height = 32;
        
        fallSpeed = 0.1;
        
        if(color.equals("BLUE"))
            this.color = "blueduckKO.gif";
        
            else if(color.equals("GREEN"))
                    this.color = "greenduckKO.gif";
            
                else
                    this.color ="redduckKO.gif";
        
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Enemies/"+this.color
                    )
            );
            
            sprites = new BufferedImage[2];
            for(int i = 0; i < sprites.length; i++){
                sprites[i] = spriteSheet.getSubimage(
                        i * width, 
                        0, 
                        width, 
                        height
                );
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(400);
        
        
        falling = true;
        
    }
    
    private void getNextPosition(){
             dy += fallSpeed;
    }
    
    public void update(){
        //update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        
        animation.deathUpdate();
        
    }
    
    public boolean shouldRemove(){ return remove; }
    

     public void checkTileMapCollision()
    {   
        xtemp = x;
        ytemp = y;

        
        ytemp += dy;

    }
    
    public void setMapPosition(int x, int y) {
        xmap = x;
        ymap = y;
    }
    public void draw(Graphics2D g){
        
        super.draw(g);
    }
}
