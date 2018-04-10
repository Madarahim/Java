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
public class Card 
{
    private char suit;
    private char rank;
    private boolean visibility;
public Card(char s, char r)
    {
    suit=s;
    rank=r;
    visibility=true;
    }
public char getRank()
{
    return rank;
}
public void display()
{
   String message="";
    if(rank=='A')
       message+="Ace";
    if(rank=='2')
       message+="2";
    if(rank=='3')
       message+="3";
    if(rank=='4')
       message+="4";
    if(rank=='5')
       message+="5";
    if(rank=='6')
       message+="6";
    if(rank=='7')
       message="7";
    if(rank=='8')
       message+="8";
    if(rank=='9')
       message+="9";
    if(rank=='T')
       message+="10";
    if(rank=='J')
       message+="Jack";
    if(rank=='Q')
       message+="Queen";
    if(rank=='K')
       message+="King";
    
   
    message+=" of ";
   
    if(suit=='H')
     message+="Heart";
    if(suit=='D')
     message+="Diamond";
    if(suit=='C')
     message+="Clubs";
    if(suit=='S')
     message+="Spades";
       

   System.out.println(message);
}

public void setVisibility(boolean v)
{
    visibility=v;
}
    


}
