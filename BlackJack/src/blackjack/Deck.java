/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package blackjack;
import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
/**
 *
 * @author abdulrahim
 */
public class Deck {
    private Card[] data=new Card[52];
    private int top,t=0;
    
    public void createDeck()
    {
        int i=0;
        char[] s_array={'H','D','C','S'};
        char[] r_array={'A', '2','3','4','5','6','7','8','9','T','J','Q','K'};
        
        for(int r=0;r<13;r++)  
            for(int s=0;s<4;s++)
            {
                data[i++]=new Card(s_array[s],r_array[r]);
            }
        top=0;
    }
    public int getTop()
    {
    return top;
    }
    
    public void Shuffle()
    {
        Card temp;
        Random ran= new Random();
        int x,y;
        for(int shuffle=0;shuffle<10000;shuffle++)
        {
            x=ran.nextInt(52-getTop())+getTop();
            y=ran.nextInt(52-getTop())+getTop();
            temp=data[x];
            data[x]=data[y];
            data[y]=temp;
        }
    }
    
    public Card deal()
    {
        return data[top++];
    }
    public void removeFromDeck()
    {
      data[t++]=null;
    }
    
    public void showCards()
    {
        for(int i=top; i<52; i++)
        {
            data[i].display();
        }
        System.out.println("---------------------");
    }
    public void createAnotherDeck()
    {
        BlackJackPanel.deck=null;
        Deck deck2=new Deck();
    }
    
}
