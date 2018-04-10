/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameState;

/**
 *
 * @author abdulrahim
 */
import Audio.AudioPlayer;


import Entity.*;
import Entity.Enemies.*;
import Entity.EnemyDeath.*;
import Entity.Enemies.Ducks.*;

import TileMap.*;


import sultan.lost.found.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

public class Level1State extends GameState{
    
    private TileMap tileMap;
    private Background bg;
    
    private Player player;
    
    private ArrayList<Enemy> enemies;
    private ArrayList<Death> deaths;
    
    private HUD hud;
    
    //private AudioPlayer bgMusic;
    
    public Level1State(GameStateManager gsm)
    {
        this.gsm=gsm;
        init();
    }
    
    public void init() {
        tileMap=new TileMap(30);
        tileMap.loadTiles("/Tilesets/grasstileset.gif");
        tileMap.loadMap("/Maps/level1-1.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(0.07);
        
        bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
        
        player = new Player(tileMap);
        player.setPosition(100, 100);
        
        //populate level with enemies
        populateEnemies();
      
        //initialize deaths
        deaths = new ArrayList<Death>();
        
        hud = new HUD(player);
        
        bgMusic = new AudioPlayer("/Music/Don't Lose Your Way 8-bit.mp3");
        bgMusic.loop();
    }
    
    
    private void populateEnemies(){
        enemies = new ArrayList<Enemy>();
        
        //add a slugger enemy
        Slugger s;
        s =  new Slugger(tileMap);
        s.setPosition(200, 100);
        enemies.add(s);
        
        //add a blue duck enemy
        BlueDuck b;
        b = new BlueDuck(tileMap);
        b.setPosition(40, 40);
        enemies.add(b);
        
        //add a red duck enemy
        RedDuck r;
        r = new RedDuck(tileMap);
        r.setPosition(60, 60);
        enemies.add(r);
        
        //add a green duck enemy
        GreenDuck g;
        g = new GreenDuck(tileMap);
        g.setPosition(80, 80);
        enemies.add(g);
        
        //add a frog enemy
        Frog f;
        f = new Frog(tileMap);
        f.setPosition(110, 100);
        enemies.add(f);
        
    }
    
    
    public void update() {
        //update player
        player.update();
        
        //check if player is dead
        if(player.notOnScreen() || player.getHealth() <= 0)
        {
            //stop backgroundMusic
            bgMusic.stop();
            
            gsm.setState(GameStateManager.GAMEOVERSTATE);
            //reset the character so we don't enter this if statement when update() is called.
            player = new Player(tileMap);
            player.setPosition(100, 0);
        }
        //scroll through map as player moves
        tileMap.setPosition(
                GamePanel.WIDTH / 2 - player.getX(), 
                GamePanel.HEIGHT / 2 - player.getY());
        
        //set background (make scrolling)
        bg.setPosition(tileMap.getX(), tileMap.getY());
        
        //attack enemies
        player.checkAttack(enemies);
        
        //update ALL enemies
        for(int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            //check if enemies are dead so we can remove them
            if(e.isDead()){
                enemies.remove(i);
                i--;
                
                //if the enemy is a slugger then play the explosion animation when killed
                if(e instanceof Slugger) { 
                    deaths.add(new Explosion(e.getX(), e.getY()));
                }
                
                else if(e instanceof BlueDuck){
                    deaths.add(new FallingDuck(e.getX(), e.getY(), "BLUE"));
                }
                
                else if(e instanceof GreenDuck){
                    deaths.add(new FallingDuck(e.getX(), e.getY(), "GREEN"));
                }
                
                else if(e instanceof RedDuck){
                    deaths.add(new FallingDuck(e.getX(), e.getY(), "RED"));
                }
            }
        }
     
        
        //update deaths
        for(int i = 0; i < deaths.size(); i++){
            deaths.get(i).update();
            
            //check if death animation can be removed
            if(deaths.get(i).shouldRemove()){
                deaths.remove(i);
                i--;
            }
        }
        
    }
    public void draw(Graphics2D g) {
    
        //draw background
        bg.draw(g);
        
        //draw tilemap
        tileMap.draw(g);
        
        //draw player
        player.draw(g);
        
        //draw enemies
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }
        
        //draw dead enemies
        for(int i = 0; i < deaths.size(); i++){
            deaths.get(i).setMapPosition((int) tileMap.getX(),(int) tileMap.getY());
            deaths.get(i).draw(g);
        }
        //draw HUD
        hud.draw(g);
    }
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(true);
        if(k == KeyEvent.VK_RIGHT) player.setRight(true);
        if(k == KeyEvent.VK_UP) player.setUp(true);
        if(k == KeyEvent.VK_DOWN) player.setDown(true);
        if(k == KeyEvent.VK_SPACE) player.setJumping(true);
        if(k == KeyEvent.VK_G) player.setGliding(true);
        if(k == KeyEvent.VK_D) player.setScratching();
        if(k == KeyEvent.VK_F) player.setFire();
        


    
    }
    public void keyReleased(int k) {
        if(k == KeyEvent.VK_LEFT) player.setLeft(false);
        if(k == KeyEvent.VK_RIGHT) player.setRight(false);
        if(k == KeyEvent.VK_UP) player.setUp(false);
        if(k == KeyEvent.VK_DOWN) player.setDown(false);
        if(k == KeyEvent.VK_SPACE) player.setJumping(false);
        if(k == KeyEvent.VK_G) player.setGliding(false);

    }
    
}
