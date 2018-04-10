/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import Audio.AudioPlayer;

import TileMap.*;

import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;        
/**
 *
 * @author abdulrahim
 */
public class Player extends MapObject{
    
    //player stuff
    private int health;
    private int maxHealth;
    private int fire;
    private int maxFire;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;
    
    //fireball
    private boolean firing;
    private int fireCost;
    private int fireBallDamage;
    private ArrayList<FireBall> fireBalls;
    
    //scratch
    private boolean scratching;
    private int scratchDamage;
    private int scratchRange;
    
    //gliding
    private boolean gliding;
    
    
    //animations
    private ArrayList<BufferedImage[]> sprites; 
    //number of frames inside each of the below animation actions
    //we get these numbers from looking at the gif of the character
    private final int[] numFrames = {2, 5, 1, 2, 1, 4, 5};
    
    
    //animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int GLIDING = 4;
    private static final int FIREBALL = 5;
    private static final int SCRATCHING = 6;
    
    private HashMap<String, AudioPlayer> soundEffects;
    
    public Player(TileMap tm)
    {
        super(tm);
        
        //each sprite is 30 by 30 pixels
        width = /*47;*/30;
        height = /*59;*/30;
        cwidth = 20;
        cheight = 20;
        
        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;
        
        facingRight = true;
        
        health = maxHealth = 5;
        fire = maxFire = 2500;
        
        fireCost = 200;
        fireBallDamage = 5;
        fireBalls = new ArrayList<FireBall>();
        
        scratchDamage = 8;
        scratchRange = 40;
        
        
        //load sprites
        try{
            BufferedImage spriteSheet = ImageIO.read(
                    getClass().getResourceAsStream(
                    "/Sprites/Player/sultanskin.gif"
                    )
            );
            
            //initialize sprites arraylist
            sprites = new ArrayList<BufferedImage[]>();
            
            for(int i = 0; i < 7; i++) {
                
                BufferedImage bi[] = new BufferedImage[numFrames[i]];
                
                for(int j = 0; j < numFrames[i]; j++){
                 
                    //if the row of sprites is not the scratch row
                    //because each of those are 60 x 30 pixels instead of 30 x 30
                    if(i != 6){
                        
                    bi[j] = spriteSheet.getSubimage(
                            j * width, 
                            i * height, 
                            width, 
                            height);
                    }
                    
                    //if we are loading the larger row of scratch images 
                    //that are 60 x 30 pixels instead of 30 x 30
                        else{
                            bi[j] = spriteSheet.getSubimage(
                            j * width * 2, 
                            i * height, 
                            width * 2, 
                            height);
                        }
                    
                }
                
                //add row of sprites
                sprites.add(bi);
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
           
        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);
        
        soundEffects = new HashMap<String, AudioPlayer>();
        soundEffects.put("jump", new AudioPlayer("/SFX/jump.mp3"));
        soundEffects.put("scratch", new AudioPlayer("/SFX/scratch.mp3"));
        soundEffects.put("poop", new AudioPlayer("/SFX/fart.mp3"));

    }
    
    
    public int getHealth() { return health; }
    public int getMaxHealth() {return maxHealth; }
    public int getFire() { return fire; }
    public int getMaxFire() { return maxFire; }
    
    
    public void setFire(){
        firing = true;
    }
    public void setScratching(){
        scratching = true;
    }
    public void setGliding(boolean b){
        gliding = b;
    }
    
    
    public void checkAttack(ArrayList<Enemy> enemies){
        //loop through enemies
        for(int i = 0; i < enemies.size(); i++){
            Enemy e = enemies.get(i);
            
            ////////////////////////////////////////////////////////////////////
            //scratch attack
            ////////////////////////////////////////////////////////////////////
            
            if(scratching){
                //check which direction we are scratching so we know which collision box we are checking
                //if we are facing right
                if(right){
                        //check if enemy is within the scratch attack range

                        //if enemy is to the right of us AND if its within range
                        if(
                                e.getX() > x && 
                                e.getX() < x + scratchRange &&
                                e.getY() > y - height / 2 &&
                                e.getY() < y + height /2
                        ) {

                            e.hit(scratchDamage);
                        }     

                }
                    //if we are facing left
                    else{
                            //check if enemy is within the scratch attack range

                            //if enemy is to the left of us AND if its within range
                            if(
                                    e.getX() < x && 
                                    e.getX() > x - scratchRange &&
                                    e.getY() > y - height / 2 &&
                                    e.getY() < y + height /2
                            ) {

                                e.hit(scratchDamage);
                            }     

                    }
            }
            
            ////////////////////////////////////////////////////////////////////
            //fireballs
            ////////////////////////////////////////////////////////////////////
            for(int j = 0; j < fireBalls.size(); j++){
           
                if(fireBalls.get(j).intersects(e)){
                    e.hit(fireBallDamage);
                    fireBalls.get(j).setHit();
                    break;
                }
            }
         
            ////////////////////////////////////////////////////////////////////
            //check enemy collisions (when they hurt you)
            ////////////////////////////////////////////////////////////////////
            if(intersects(e)){
                hit(e.getDamage());
            }
        }        
        
    }
    
            
    public void hit(int damage){
        //we don't wanna get hurt/hit when we are flinching
        if(flinching) return;
        
        health -= damage;
        if(health < 0) health = 0;
        if(health == 0) dead = true;
        flinching = true;
        flinchTimer = System.nanoTime();
    }
    
    
    //where the next position of the player is by reading the keyboard input
    private void getNextPosition(){
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
        
        //if we are not going left or right then we have to stop/slow down to stop
        else{
            //slow down from both directions
            if(dx > 0){
                dx -= stopSpeed;
                if(dx < 0){
                    dx = 0;
                }
            }
            
            else if(dx < 0){
                dx += stopSpeed;
                if(dx > 0){
                    dx = 0;
                }
            }
        }
        
         
        //cannot move while attacking, unless you are in the air
        if((currentAction == SCRATCHING || currentAction == FIREBALL) &&
                !(jumping || falling)){
                
            dx = 0;
        }
        
        //jumping
        if(jumping && !falling){
            soundEffects.get("jump").play();
            dy = jumpStart;
            falling = true;
        }
        
        //falling
        if(falling){
            //if gliding while falling then fall at 10% of the normal falling speed
            if(dy > 0 && gliding) dy += fallSpeed * 0.1;
            else dy += fallSpeed;
            
            if(dy > 0) jumping = false;
            if(dy < 0 && !jumping) dy += stopJumpSpeed; //makes it so the longer you hold jump, the higher you'll jump
            
            //set max fall speed
            if(dy > maxFallSpeed) dy = maxFallSpeed;
        }
    }
    
    
    public void update(){
    
        //update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        
        //check attack has stopped (Basically stop glitchy repeat of attack sprites when using attacks)
        if(currentAction == SCRATCHING) {
            if(animation.hasPlayedOnce()) scratching = false;
        }
        if(currentAction == FIREBALL) { 
            if(animation.hasPlayedOnce()) firing = false;
        }
        
        //fireball attack
        fire += 1;
        if(fire > maxFire) fire = maxFire;
        //check if we are firing and if the animation is not set yet
        if(firing && currentAction != FIREBALL){
            //if we still have enough energy to perform a fireball
            if(fire > fireCost){
                fire -= fireCost;
                FireBall fb = new FireBall(tileMap, facingRight);
                fb.setPosition(x, y);
                fireBalls.add(fb);
            }
        }
        
        
        //update fireballs
        for(int i = 0; i < fireBalls.size(); i++)
        {
            fireBalls.get(i).update();
            if(fireBalls.get(i).shouldRemove())
            {
                fireBalls.remove(i);
                i--;
            }
        }
        
        //check done flinching
        if(flinching){
            long elapsed = (System.nanoTime() - flinchTimer)/1000000;
            //if flinching has lasted longer than 1 sec, then stop flinching
            if(elapsed > 1000){
                flinching = false;
            }
        }
        
        //set animation
        if(scratching)
        {
            if(currentAction != SCRATCHING)
            {
                soundEffects.get("scratch").play();
                currentAction = SCRATCHING;
                animation.setFrames(sprites.get(SCRATCHING));
                animation.setDelay(50);
                width = 60;
            }
        }
        
            else if(firing){

            if(currentAction != FIREBALL)
                {
                    soundEffects.get("poop").play();
                    currentAction = FIREBALL;
                    animation.setFrames(sprites.get(FIREBALL));
                    animation.setDelay(100);
                    width = 30;
                }
            }
        
                //if we are falling
                else if(dy > 0){
                    //if we are gliding falling
                    if(gliding){
                        if(currentAction != GLIDING){
                            currentAction = GLIDING;
                            animation.setFrames(sprites.get(GLIDING));
                            animation.setDelay(100);
                            width = 30; 
                        }
                    }

                    //if we are regular falling
                    else if(currentAction != FALLING){
                        currentAction = FALLING;
                        animation.setFrames(sprites.get(FALLING));
                        animation.setDelay(100);
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
        
        //update the animation
        animation.update();
        
        //set direction current player is facing
        //we don't want him moving when he's attacking so make sure he isn't
        //scratching or fireballing
        if(currentAction != SCRATCHING && currentAction != FIREBALL){
            if(right) facingRight = true;
            if(left) facingRight = false;
        }

    }
    
    
    public void draw (Graphics2D g)
    {
        setMapPosition();
        
        //draw fireballs
        for(int i = 0 ; i < fireBalls.size(); i++)
        {
            fireBalls.get(i).draw(g);
        }
        
        //draw player
        if(flinching){
            long elapsed =
                    (System.nanoTime() - flinchTimer)/1000000;
            
            if(elapsed / 100 % 2 == 0){
                return;
            }
        }
        
        /*
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
        */
        
        //The commented section above for drawing right or left has been moved to the super class MapObject.
        //To call that part of the code in the Super class do what I did below.
        super.draw(g);
            
        
    }
    
}
