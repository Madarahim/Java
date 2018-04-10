/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameState;

import Audio.AudioPlayer;

import TileMap.Background;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author abdulrahim
 */
public class GameOverState extends GameState{
    private Background bg;
    
    private int currentChoice;
    private String[] choices = {"Continue", "Quit"};
    
    private Color titleColor;
    private Font titleFont;
    
    private Font font;
    
    //private AudioPlayer bgMusic;
    
    public GameOverState(GameStateManager gsm)
    {
        this.gsm = gsm;
        
        currentChoice = 0;
        
        try{
            
            bg = new Background("/Backgrounds/menubg.gif", 1);
            bg.setVector(-0.1, 0);
            
            titleColor = Color.darkGray;
            titleFont = new Font("Ninja Naruto", Font.PLAIN, 55);
            
            font = new Font("Arial", Font.PLAIN, 12);
            
            bgMusic = new AudioPlayer("/Music/Game Over.mp3");
            bgMusic.play();
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
        g.drawString("Game Over", 17, 90);
        
        //draw menu options
        g.setFont(font);
        
        //set colors for selected and not selected choices
        for(int i=0; i<choices.length; i++){
            
            if(i == currentChoice)
            {
                g.setColor(Color.BLUE);
            }
            
                else
                {
                    g.setColor(Color.LIGHT_GRAY);
                }
        
            g.drawString(choices[i], 145, 140 + i * 15);
        }
        
    }
    
    
    private void select()
    {
        if(currentChoice == 0)
        {
            //stop background music
            bgMusic.stop();
            
            //continue
            gsm.setState(GameStateManager.LEVEL1STATE);
        }
        
        if(currentChoice == 1)
        {
            //stop background music
            bgMusic.stop();
            
            //exit
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }
    
    
    public void setCurrentChoice(int i){ currentChoice = i;}
    
    
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
