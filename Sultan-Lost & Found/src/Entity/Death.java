/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author abdulrahim
 */
public class Death {
    
    protected double x;
    protected double y;
    protected int xmap;
    protected int ymap;
    
    //speed going in x or y direction
    protected double dx;
    protected double dy;
    
    // collision
    protected int currRow;
    protected int currCol;
    protected double xdest;
    protected double ydest;	
    protected double xtemp;
    protected double ytemp;
    
    protected int width;
    protected int height;
    
    protected Animation animation;
    protected BufferedImage[] sprites;
    
    protected boolean remove;
    
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
    
    
    public Death(){
    
    }
    
    public boolean shouldRemove(){ return remove; }

    //objects global position
    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void setMapPosition(int x, int y){
        xmap = x;
        ymap = y;
    }
    
    
    public void update(){}
    
    public void draw(Graphics2D g){
        g.drawImage(
                animation.getImage(),
                ((int)x) + xmap - width / 2, 
                ((int)y) + ymap - height/ 2,
                null
        );
    }
    
}
