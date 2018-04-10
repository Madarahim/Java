/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GameState;

import java.util.ArrayList;

import GameState.MenuState;
/**
 *
 * @author abdulrahim
 */
public class GameStateManager {
    
    //Gamestate Objects
    private GameState[] gameStates;
    private int currentState;
    
    //Total Number of GameStates
    public static final int NUMGAMESTATES = 3;
    
    //State Index
    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;
    public static final int GAMEOVERSTATE = 2;

    
    //constructor
    public GameStateManager()
    {
        gameStates = new GameState[NUMGAMESTATES];
                
        currentState=MENUSTATE;
        loadState(currentState);
    }
    
    
    private void loadState(int state) {
        if(state == MENUSTATE)
            gameStates[state] = new MenuState(this);
        if(state == LEVEL1STATE)
            gameStates[state] = new Level1State(this);
        if(state == GAMEOVERSTATE)
            gameStates[state] = new GameOverState(this);
    }
    
    
    private void unloadState(int state) {
        gameStates[state] = null;
    }
    
    public void setState(int state)
    {
        unloadState(currentState);
        currentState=state;
        loadState(state);
        //gameStates[currentState].init();
    }
    
    
    public void update()
    {
        if(gameStates[currentState] != null) {
            gameStates[currentState].update();
        }
    }
    
    
    public void draw(java.awt.Graphics2D g)
    {
        if(gameStates[currentState] != null) {
            gameStates[currentState].draw(g);
        }
    }
    
    
    public void keyPressed(int k)
    {
        if(gameStates[currentState] != null) {
            gameStates[currentState].keyPressed(k);
        }
    }
    
    
    public void keyReleased(int k)
    {
        if(gameStates[currentState] != null) {
            gameStates[currentState].keyReleased(k);
        }
    }
}
