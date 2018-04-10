/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameState;

import Audio.AudioPlayer;
import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;
/**
 *
 * @author abdulrahim
 */
public class MenuState extends GameState{
    
    private Background bg;
    
    private int currentChoice=0;
    private String[] choices = {"Start", "Help", "Quit"};
    
    private Color titleColor;
    private Font titleFont;
    
    private Font font;
    
    //private AudioPlayer bgMusic;
    
    public MenuState(GameStateManager gsm)
    {
        this.gsm = gsm;
        
        try{
            
            bg = new Background("/Backgrounds/nature.gif", 1);
            bg.setVector(-0.1, 0);
            
            titleColor = new Color(128,0,0);
            titleFont = new Font("Ninja Naruto", Font.PLAIN, 25);
            
            font = new Font("Arial", Font.PLAIN, 12);
            
            bgMusic = new AudioPlayer("/Music/Gurren Lagann OP 8-bit.mp3");
            bgMusic.loop();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void init(){}
    public void update(){
        bg.update();
    }
    public void draw(Graphics2D g){
        
        //draw background
        bg.draw(g);
        
        //draw title
        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("Sultan : Lost & Found", 17, 70);
        
        //draw menu options
        g.setFont(font);
        
        //set colors for selected and not selected choices
        for(int i=0; i<choices.length; i++){
            
            if(i == currentChoice)
            {
                g.setColor(Color.BLACK);
            }
            
                else
                {
                    g.setColor(Color.RED);
                }
        
            g.drawString(choices[i], 145, 140 + i * 15);
        }
        
    }
    
    private void select()
    {
        if(currentChoice == 0)
        {
            //stop backgroundMusic
            bgMusic.stop();
            
            //start
            gsm.setState(GameStateManager.LEVEL1STATE);
        }
        
        if(currentChoice == 1)
        {
            //help
        }
        
        if(currentChoice == 2)
        {
            //exit
            System.exit(0);
        }
    }
    
    public void keyPressed(int k){
        if(k == KeyEvent.VK_ENTER)
        {
            select();
        }
        
        if(k == KeyEvent.VK_UP)
        {
            currentChoice--;
            
            if(currentChoice == -1)
            {
                currentChoice = choices.length - 1;
            }
        }
        
        if(k == KeyEvent.VK_DOWN)
        {
            currentChoice++;
            
            if(currentChoice == choices.length)
            {
                currentChoice = 0;
            }
        }
            
    }
    public void keyReleased(int k){}
    
}