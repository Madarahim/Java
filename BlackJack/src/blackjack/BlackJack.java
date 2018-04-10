/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package blackjack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Scanner;
/**
 *
 * @author abdulrahim
 */
public class BlackJack {

    public static JFrame frame;
    public static Deck dude=new Deck();

//fun

   

    public static void main(String[] args) 
    {
      frame = new JFrame ("Choose the Operation");
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      BlackJackPanel panel = new BlackJackPanel();
      frame.getContentPane().add(panel);
      frame.pack();
      frame.setVisible(true);
      
       
    }
    public void PlayBlackJack()
    {
        Hand myHand= new Hand();
        Hand dealersHand= new Hand();
        String yourInfo, dealerInfo;
        char YN, Enter;
        int z=2;
        Scanner in = new Scanner(System.in);
        
        System.out.println("THE GAME HAS BEGUN");
        System.out.println("-------------------------");
        
        dealersHand.dealerDraw(BlackJackPanel.deck.deal());
        myHand.yourDraw(BlackJackPanel.deck.deal());
        dealersHand.dealerDraw(BlackJackPanel.deck.deal());
        myHand.yourDraw(BlackJackPanel.deck.deal());
        
        
        
        boolean w=false, pOver=false,dOver=false, p21=false, d21=false;
        
        while(w==false)
        {
            
            
            System.out.println("Dealer:");
            System.out.println("---------");
        
        for(int i=1;i<dealersHand.numDealerCards();i++)
        {
      
            System.out.println("????????????");
            dealersHand.dealersHand(i).display();
            System.out.println();
        }
        
        
          System.out.println("Player:");
          System.out.println("---------");  
        
        for(int j=0;j<myHand.numUrCards();j++)
        {
        
          myHand.yourHand(j).display();
         
        }
        System.out.println("Total: "+myHand.yourTotal());
        
        if(myHand.yourTotal()>21&&dealersHand.dealersTotal()<21)
            {
                pOver=true;
                w=true;
                break;
            }
            
            else if(myHand.yourTotal()==21)
                    {
                        p21=true;
                        w=true;
                        break;
                    }
                else if(dealersHand.dealersTotal()==21)
                        {
                            d21=true;
                            w=true;
                            break;
                        }
                     
        System.out.println("\nDo you want another card? [Y/N]");
        YN= in.next().charAt(0);
        
        System.out.println("\n\n");
        
        if(YN=='Y'||YN=='y')
            myHand.yourDraw(BlackJackPanel.deck.deal()); 
        if(YN=='N'||YN=='n')
            w=true;
                     
            
        }
        
        
        
        
            if(p21==false && d21==false && dOver==false && pOver==false)
            {
            while(dealersHand.dealersTotal()<17 && dOver==false)
            {
                dealersHand.dealerDraw(BlackJackPanel.deck.deal());   
                System.out.print("Dealer takes a card: ");
                dealersHand.dealersHand(z++).display();
                if(myHand.yourTotal()<21 && dealersHand.dealersTotal()>21)
                {
                    dOver=true;
                    break;
                }
                else if(dealersHand.dealersTotal()==21)
                        {
                            d21=true;
                            w=true;
                            break;
                        }
                
            }
            if(dealersHand.dealersTotal()>=17 && dealersHand.dealersTotal()<=21)
                {
                    System.out.println("Dealer stands...\n\n");
                }
            
            }
        System.out.println("\nThe Game has Finished.");
        System.out.println("Press any letter and enter to see the resuls.....");
        YN= in.next().charAt(0);
        
        System.out.println("\nResults:\n");
        System.out.println("Dealer    :     Total:"+dealersHand.dealersTotal());
        for(int m=0;m<dealersHand.numDealerCards();m++)
        {
            System.out.print("                ");
            dealersHand.dealersHand(m).display();
        }
        
        System.out.println("\n");
        
        System.out.println("Player    :     Total:"+myHand.yourTotal());
        for(int n=0;n<myHand.numUrCards();n++)
        {
            System.out.print("                ");
            myHand.yourHand(n).display();
        }
        
        int d, p;
        d=Math.abs(21-dealersHand.dealersTotal());
        p=Math.abs(21-myHand.yourTotal());
        
        if((dealersHand.dealersTotal()<21 && myHand.yourTotal()<21)||(dealersHand.dealersTotal()>21 && myHand.yourTotal()>21))
        {
            if(d>p)
            {
            System.out.println("\n\nCongratulations! You won. You posses the hand of a King.");
            System.out.println("You have "+" dollars.");
            }
                if(p>d)
                    {
                    System.out.println("\n\nYou lose pal.");
                    System.out.println("You have "+" dollars.");
                    }
     
        }
        
        
        
        
        
        
       if(p21==true)
       {
           System.out.println("\n\nYou got 21. You win!");
           System.out.println("You have "+" dollars.");
       }
       if(d21==true)
       {
           System.out.println("\n\nOH NOOOO!!! The dealer got 21! You lose!");
           System.out.println("You have "+" dollars.");
       }
       if(dOver==true)
       {
           System.out.println("\n\nHahaha. The Dealer went over 21. You win!");
           System.out.println("You have "+" dollars.");
       }
       if(pOver==true)
        {
           System.out.println("\n\nAwwww. Your total went over 21. You lose.");
           System.out.println("You have "+" dollars.");
        }
        if(myHand.yourTotal()==dealersHand.dealersTotal())
        {
           System.out.println("\n\nIt's a tie! I'll be kind enough to grant you the victory");
           System.out.println("You have "+" dollars.");
        }
        
        
        myHand=null;
        dealersHand=null;
           
    }
    
}
