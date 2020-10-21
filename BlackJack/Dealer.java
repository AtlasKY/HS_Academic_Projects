/*
 *ATLAS KAAN YILMAZ & SERDAR SUNGUN 
 * 
 * BLACKJACK FINAL PROJECT
 * 
 * 12 JANUARY 2019
 * 
 */ 
import java.util.ArrayList;
import java.awt.*;
import java.awt.image.*;

public class Dealer
{
  
  //Dealer field coordinates and score
  private int cardX = 350, cardY = 60, score = 0;
  
  public boolean isGame = true; //if the bets keep going
  
  ArrayList<Card> hand;
  
  //CONSTRUCTOR
  public Dealer()
  {    
    hand = new ArrayList<Card>();
  }
  
  //DEALER TAKE ONE CARD
  public void takeCard(Card c)
  {
    hand.add(c);
    score += c.getValue();  
  }
  
  //DEALER PLAYS
  public void play(Deck d)
  {
    isGame = false;
    
    while(canTake()) //if can take, deck deals one card to dealer
    {
      d.hitDealer(this);
    }
  }
  
  //CHECK FOR DEALER CARD TAKING
  private boolean canTake() 
  {
    if(score < 17)
      return true;
    else if(score > 21 && checkAce()){ //if higher than 21 but there is an A
     if(score < 17) 
       return true;
     else
       return false;
    }
    else
      return false;
  }
  
  //Checks A and toggle its value to 1
  private boolean checkAce()
  {
    boolean temp = false;
    for(Card c : hand) //check all cards
    {
      if(c.getValue() == 11) {
        c.toggleValue();
        score -= 10; //update score
        temp = true;
      } 
    }
    
    return temp;
  }
  
  //RESET
  public void reset()
  {
    score = 0;
    int temp = hand.size();
    
    isGame = true;
    
    for(int i = 0; i < temp; i++) //remove all cards from hand
    {
      hand.remove(0);
    }
  }
  
  //GETTERS////////////////////////////////////////////////////////
  public int getScore(){ return score; }
  public int getHandSize() { return hand.size(); }
  
  //DRAW
  public void draw(Graphics2D g2, BufferedImage back)
  {
    //DRAW CARDS 
    int dx = 0;
    
    for(int i = 0; i < hand.size(); i++)
    {
      if(isGame && i == 0) //if isGame hide the first card
        g2.drawImage(back, null, cardX, cardY); 
      else
        hand.get(i).draw(g2, cardX + dx, cardY);
      
      dx+= 15;
    }
    
    
  }
  
  
  
  
}