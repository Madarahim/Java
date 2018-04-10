/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import TileMap.TileMap;
import TileMap.Tile;

import sultan.lost.found.GamePanel;

import java.awt.*;


/**
 *
 * @author abdulrahim
 */
public abstract class MapObject {
    
    // tile stuff
    protected TileMap tileMap;
    protected int tileSize;
    protected double xmap;	
    protected double ymap;

    // position and vector
    protected double x; //upper right corner x value of collision or character box
    protected double y;	//upper left corner y value of collision or character box
    protected double dx;
    protected double dy;
	
    // dimensions
    protected int width;
    protected int height;
    
    // collision box
    protected int cwidth;
    protected int cheight;
	
    // collision
    protected int currRow;
    protected int currCol;
    protected double xdest;
    protected double ydest;	
    protected double xtemp;
    protected double ytemp;
    //Check for collision using 4 point method
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;	
    protected boolean bottomRight;
	
    // animation
    protected Animation animation;
    protected int currentAction; //which animation we are currently using
    protected int previousAction;
    protected boolean facingRight; //used to tell if sprite is facing right or left
	
    // movement
    protected boolean left; //object or entity going left... etc for below
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean jumping;
    protected boolean falling;
	
    // movement attributes
    protected double moveSpeed; //how fast the object accelerates
    protected double maxSpeed; //how fast the object can go
    protected double stopSpeed; //deacceleration speed if ur not pressing left or right, so obj slows down
    protected double fallSpeed; //gravity
    protected double maxFallSpeed; //terminal velocity of gravity
    protected double jumpStart;	//how high the object jumps
    protected double stopJumpSpeed; //the longer u press the jump button the higher you go and vice versa    
    
    // constructor
    public MapObject(TileMap tm) {
        tileMap = tm;
        tileSize = tm.getTileSize(); 
    }
	
    
    //useful for attacking other objects
    public boolean intersects(MapObject o) {
        Rectangle r1 = getRectangle();
	Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);	
    }
	
    
    //check if we have run into a blocked tile or a normal tile
    public void checkTileMapCollision()
    {
        currCol = (int)x / tileSize;
        currRow = (int)y / tileSize;
        
        xdest = x + dx;
        ydest = y + dy;
        
        xtemp = x;
        ytemp = y;
        
        ////////////////////////////////////////////////////////////////////////
        //CALCULATE CORNERS FOR THE Y-DIRECTION!!!!!
        calculateCorners(x, ydest);
        
        //if we are going UPWARDS
        if(dy < 0){
        
            //if any of the top 2 corners are a blocked tile
            if(topLeft || topRight){
                //stop moving chacacter upwards
                dy = 0;
                //set object just below the tile that it just hit
                ytemp = currRow * tileSize + cheight / 2;
            }
            
            //Otherwise we are free to keep going upwards since the tiles arent blocked above us
                else{
                    //keep going higher
                    ytemp += dy;
                }
        }
        
        
        //if we are going DOWNWARDS
        if(dy > 0)
        {
         //check bottom 2 corners if we landed on a blocked tile
            if(bottomLeft || bottomRight){
              //stop moving downwards
                dy = 0;
              //if we were falling, we need to set it to false since we landed on a blocked tile (LAND HO!)
                falling = false;
              //set y position to 1 pixel above the tile we just landed on. Basically right above the tile we landed on
                ytemp = (currRow + 1) * tileSize - cheight / 2;
            }
            
            //Otherwise we can keep falling since there are no blocked tiles below us
                else{
                    //keep falling
                    ytemp += dy;
                }
            
        }
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////

        
        
        ////////////////////////////////////////////////////////////////////////
        //CALCULATE CORNERS FOR THE X-DIRECTION!!!!!
        calculateCorners(xdest, y);
        
        //if we are going LEFT
        if(dx < 0)
        {
            //if we hit a blocked tile to the left
            if(topLeft || bottomLeft){
                //stop moving to the left
                dx = 0;
                //set the x position to just right of the tile we just hit.
                xtemp = currCol * tileSize + cwidth / 2;
            }
            
            //Otherwise, we are free to keep moving to the left.
                else{
                    //keep moving to the left
                    xtemp += dx;
                }
            
        }
        
        
        //if we are going to the RIGHT
        if(dx > 0)
        {
            //check 2 right corners if we hit a blocked tile to the right
            if(topRight || topLeft){
                
                //stop moving to the right
                dx = 0;
                //set our x position to just before the right blocked tile. The left of the blocked tile basically.
                xtemp = (currCol + 1) * tileSize - cwidth / 2;
            }
            
                //Otherwise we are free to keep going to the right.
                else{
                    //keep moving to the right
                    xtemp += dx;
                }
        
        }
        
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////

        
        ////////////////////////////////////////////////////////////////////////
        //CHECK IF WE ARE FALLING OFF A CLIFF
        if(!falling)
        {
            //check the ground to see if we didn't fall off a cliff
            calculateCorners(x, ydest + 1);
            //if we are not on solid ground (AKA a blocked tile)
            if(!bottomLeft && !bottomRight){
                //set falling to true since we are now falling
                falling = true;
            }
           
        }
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        
    }
    
    
    public Rectangle getRectangle() {
            
            return new Rectangle(
			(int)x - cwidth, //upper left corner x value
                        (int)y - cheight, // upper left corner y value
			cwidth, //width of rectangle
			cheight // height of rectangle
                );
        }
    
    
    //check which tiles around your object are blocked or normal by calculating their coordinates
    public void calculateCorners(double x, double y) {
        int leftTile = (int)(x - cwidth / 2) / tileSize;
        int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
        int topTile = (int)(y - cheight / 2) / tileSize;
        int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
        if(topTile < 0 || bottomTile >= tileMap.getNumRows() ||
                leftTile < 0 || rightTile >= tileMap.getNumCols()) {
                topLeft = topRight = bottomLeft = bottomRight = false;
                return;
        }
        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);
        topLeft = tl == Tile.BLOCKED;
        topRight = tr == Tile.BLOCKED;
        bottomLeft = bl == Tile.BLOCKED;
        bottomRight = br == Tile.BLOCKED;
    }
    
    
    public int getX() { return (int)x; }
    public int getY() { return (int)y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getCWidth() { return cwidth; }
    public int getCHeight() { return cheight; }
    
    //objects global position
    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void setVector(double dx, double dy)
    {
        this.dx = dx;
        this.dy = dy;
    }
    
    //tells us where to draw the character
    public void setMapPosition()
    {
        xmap = tileMap.getX();
        ymap = tileMap.getY();
    }
    
    public void setLeft (boolean b) { left = b; };
    public void setRight (boolean b) { right = b; };
    public void setUp (boolean b) { up = b; };
    public void setDown (boolean b) { down = b; };
    public void setJumping (boolean b) { jumping = b; };
    
    //return whether or not object (character) is on screen or not
    //we use this knowledge to know if we have to or don't have to draw the character on the screen
    public boolean notOnScreen(){
    
        return x + xmap + width < 0 ||
               x + xmap - width > GamePanel.WIDTH ||
               y + ymap + height < 0 ||
               y + ymap - height > GamePanel.HEIGHT;
    }
    //YOUTUBE COMMENT SOLUTION??
    /*
    Hey guys, if you want to fix the notOnScreen method (Helpful for large scale games and memory), use this method I created but it has to be in your Enemies classes (Slugger, any npc). It returns true if it's on your screen. Ask away for any questions.

public boolean onScreen() {
  return Math.abs(p.getx() - this.x) < GamePanel.WIDTH &&
      Math.abs(p.gety() - this.y) < GamePanel.HEIGHT;
 }﻿
    */
    
    
    public void draw(Graphics2D g) {
        //draw facing right
            if(facingRight){
                g.drawImage(
                        animation.getImage(), 
                        (int) (x + xmap - width / 2), 
                        (int) (y + ymap - height / 2),
                        null);
                
            }
            
            //draw facing left
            else{
                g.drawImage(
                        animation.getImage(), 
                        (int) (x + xmap - width / 2 + width), 
                        (int) (y + ymap - height / 2),
                        -width,
                        height,
                        null);
            
            }
    }
    
}
