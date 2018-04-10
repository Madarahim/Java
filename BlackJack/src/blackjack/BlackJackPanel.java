/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package blackjack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author abdulrahim
 */
public class BlackJackPanel extends JPanel
{
   private JLabel prompt;
   private JRadioButton one, two, three, four, five, six;
   public static Deck deck=new Deck();
   public static BlackJack blackJack= new BlackJack();

   public BlackJackPanel()
           {
               prompt = new JLabel ("Please choose one of the items:");
               prompt.setFont (new Font ("Helvetica", Font.BOLD, 14));
               
               one = new JRadioButton ("Create Deck");
               one.setBackground (Color.yellow);
               two = new JRadioButton ("Shuffle Deck");
               two.setBackground (Color.yellow);
               three = new JRadioButton ("Show All Cards");
               three.setBackground (Color.yellow);
               four = new JRadioButton ("Play BlackJack");
               four.setBackground(Color.yellow);
               
               
               ButtonGroup group = new ButtonGroup();
               group.add (one);
               group.add(two);
               group.add(three);
               group.add(four);
     

               BlackJackPanelListener listener = new BlackJackPanelListener ();
               one.addActionListener (listener);
               two.addActionListener (listener);
               three.addActionListener (listener);
               four.addActionListener(listener);
               
               add (prompt);
               add (one);
               add (two);
               add (three);
               add (four);
             
               setBackground (Color.green);
               setPreferredSize (new Dimension(225, 170));
           
           }
private class BlackJackPanelListener implements ActionListener
    {
       public void actionPerformed (ActionEvent event)
       {
    
        Object source = event.getSource();
        if (source == one)
        {
            
                deck.createDeck();
            
        }
       
        else
         {
                if (source==two)
                       deck.Shuffle();
                else
                    if(source==three)
                           deck.showCards();
                        else
                            if(source==four)
                            {
                                if ((52-deck.getTop())<10)
                                {
                                 String message="The Amount of Cards left in the deck is less"
                                    + " than 10. Create a new Deck in order to play again.";
                                    JOptionPane.showMessageDialog(null, message);
                                }
                                else if((52-deck.getTop())>=10)
                                      blackJack.PlayBlackJack();
                            }
                
                        
           }
       
        
       
          }
       
       }

    
}
