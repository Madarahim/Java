/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package blackjack;

/**
 *
 * @author abdulrahim
 */

public class Hand {
    private double wallet;
    private Card[] yourHand=new Card[15];
    private Card[] dealersHand=new Card[15];
    private int yourCount=0,dealersCount=0;

    
    public void yourDraw(Card C)
    {
        yourHand[yourCount++]=C;

    }
    
    public void dealerDraw(Card C)
    {
        dealersHand[dealersCount++]=C;

    }
    
    public int numUrCards()
    {
        return yourCount;
    }
    
    public int numDealerCards()
    {
        return dealersCount;
    }
    
    public Card yourHand(int i)
    {
        return yourHand[i];
    }
    
    public Card dealersHand(int i)
    {
        return dealersHand[i];
    }
    
    public int yourTotal()
    {
        int yourTotal=0;
        for(int c=0; c<yourCount; c++)
        {
      
                if(yourHand[c].getRank()=='2')
                     yourTotal+=2;
                    else if(yourHand[c].getRank()=='3')
                            yourTotal+=3;
                         else if(yourHand[c].getRank()=='4')
                             yourTotal+=4;
                            else if(yourHand[c].getRank()=='5')
                                 yourTotal+=5;
                                else if(yourHand[c].getRank()=='6')
                                          yourTotal+=6;
                                     else if(yourHand[c].getRank()=='7')
                                                 yourTotal+=7;
                                           else if(yourHand[c].getRank()=='8')
                                                       yourTotal+=8;
                                                  else if(yourHand[c].getRank()=='9')
                                                          yourTotal+=9;
                                                      else if(yourHand[c].getRank()=='T')
                                                              yourTotal+=10;
                                                          else if(yourHand[c].getRank()=='J')
                                                                   yourTotal+=10;
                                                              else if(yourHand[c].getRank()=='Q')
                                                                        yourTotal+=10;
                                                                   else if(yourHand[c].getRank()=='K')
                                                                             yourTotal+=10;       
                                                    
             
        }
        for(int c2=0;c2<yourCount; c2++)
        {
            if(yourHand[c2].getRank()=='A')
            {
                if (yourTotal<=10)
                    yourTotal+=11;
                    else
                        yourTotal+=1;
            }
        }
        
        return yourTotal;
        
    }
    
    public int dealersTotal()
    {
        int dealersTotal=0;
        for(int c=0; c<dealersCount; c++)
        {
      
                if(dealersHand[c].getRank()=='2')
                     dealersTotal+=2;
                    else if(dealersHand[c].getRank()=='3')
                            dealersTotal+=3;
                         else if(dealersHand[c].getRank()=='4')
                             dealersTotal+=4;
                            else if(dealersHand[c].getRank()=='5')
                                 dealersTotal+=5;
                                else if(dealersHand[c].getRank()=='6')
                                          dealersTotal+=6;
                                     else if(dealersHand[c].getRank()=='7')
                                                 dealersTotal+=7;
                                           else if(dealersHand[c].getRank()=='8')
                                                       dealersTotal+=8;
                                                  else if(dealersHand[c].getRank()=='9')
                                                          dealersTotal+=9;
                                                      else if(dealersHand[c].getRank()=='T')
                                                              dealersTotal+=10;
                                                          else if(dealersHand[c].getRank()=='J')
                                                                   dealersTotal+=10;
                                                              else if(dealersHand[c].getRank()=='Q')
                                                                        dealersTotal+=10;
                                                                   else if(dealersHand[c].getRank()=='K')
                                                                             dealersTotal+=10;       
                                                    
             
        }
        for(int c2=0;c2<dealersCount; c2++)
        {
            if(dealersHand[c2].getRank()=='A')
            {
                if (dealersTotal<=10)
                    dealersTotal+=11;
                    else
                        dealersTotal+=1;
            }
        }
        
        return dealersTotal;
        
    }
    
}
