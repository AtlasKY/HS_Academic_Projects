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

public class Player {

  ArrayList<ArrayList<Card>> field;
  ArrayList<Card> hand, split;
  Chip[] chips;
  
  //ACCESS VARIABLES
  public boolean canBet = true, canSplit = false, canStand = false, canDD = false, canSDD = false, isStand = false, isSplit = false, showScore = false;
  
  public boolean[] canHit = new boolean[2]; // canhit for both normal and split hand
  
  private int balance = 1000; //initial balance
  
  private int bets[] = new int[2]; //ind. bets for split and normal hands
  
  private int cardX, cardY, chipX, chipY; //CARD AND CHIP SHOW COORDINATES
  
  public int hScore = 0, sScore = 0;
  
  private String playerName;
  
  //CONSTRUCTOR//////////////////////////////////////////////////
  public Player(String pn, int crx, int cry, int chx, int chy, Chip[] c)
  {
    playerName = pn;
    cardX = crx;
    cardY = cry;
    chipX = chx;
    chipY = chy;
    
    canHit[0] = false;
    canHit[1] = false;
    
    chips = c;
    
    field = new ArrayList<ArrayList<Card>>();
    hand = new ArrayList<Card>();
    
    field.add(hand);
    
  }
  //////////////////////////////////////////////////////////
  
  //GAME ACTIONS/////////////////////////////////////////////////
  
  //SPLIT
  public void split()
  {
    canSplit = false;
    isSplit = true;
    canHit[1] = true;
    if(checkSDD(1))
      canSDD = true;
    
    
    balance -= bets[0];
    bets[1] = bets[0];
    
    split = new ArrayList<Card>();
    
    Card c = field.get(0).get(1);
    hScore -= c.getValue();
    sScore += c.getValue();
    split.add(c);
    field.get(0).remove(1);
    field.add(split);
  }
  
  //DOUBLE DOWN
  public void doubleDown(int s)
  {
    //update balance and bets and accesses
    balance -= bets[s];
    bets[s] *= 2;
    canHit[s] = false;
    
    canSplit = false;
    
    if(s == 0)
      canDD = false;
    if(s == 1)
      canSDD = false;
  }
  
  //BET
  public void bet(int bet)
  {
    //update balance and bet
    balance -= bet;
    bets[0] += bet;
    
    canBet = false;
    canStand = true;
    canHit[0] = true;
    if(checkSDD(0))
    {
      canSplit = true;
      canDD = true;
    }

  }
  
  //STAND
  public void stand()
  {
    canSplit = false;
    canDD = false;
    canSDD = false;
    canHit[0] = false;
    canHit[1] = false;
    canStand = false;
    
    isStand = true;
  }
  
  //CHECK IF CAN AFFORD DOUBLE DOWN OR SPLIT
  public boolean checkSDD(int i)
  {
    if(balance >= bets[i])
      return true;
    else
      return false;
  }
  
  //CARD OPERATIONS//////////////////////////////////////////////////////
  public void takeCard(Card c, int i)
  {
    showScore = true; //show hand score
    
    field.get(i).add(c); //add the card to the hand
    
    //update score values
    if(i == 0)
      hScore += c.getValue();
    else if(i == 1)
      sScore += c.getValue();
    
    //update access variables
    if(field.get(0).size() > 2)
    {
      canDD = false;
      canSplit = false;
    }
    if(isSplit){
      if(field.get(1).size() > 2)
        canSDD = false;
    }
    
    //update access variables
    if(hScore == 21){
      canHit[0] = false;
      canDD = false;
      canSplit = false;
    }
    else if(hScore > 21 && !checkAce(0)) {
      canHit[0] = false;
      canDD = false;
      canSplit = false;
    }
    
    if(sScore == 21) {
      canHit[1] = false;
      canSDD = false;
    }
    else if(sScore > 21 && !checkAce(1)) {
      canHit[1] = false;
      canSDD = false;
    }
  }
  
  //Checks A and toggle its value to 1
  private boolean checkAce(int s)
  {
    boolean temp = false;
    for(Card c : field.get(s)) //cycle all the cards in hand
    {
      if(c.getValue() == 11) {
        c.toggleValue();
        if(s == 0) 
          hScore -= 10; //update score
        else
          sScore -= 10;
        temp = true;
      } 
    }
    
    return temp;
  }
  
  //RESET/////////////////////////////////////////////////////////////
  public void reset()
  {
    
    //RESET CARDS IN BOTH HANDS
    int temp = field.get(0).size();
    
    for(int i = 0; i < temp; i++){
      field.get(0).remove(0);
    }
    
    if(isSplit){
      field.remove(1);
      
      temp = split.size();
      for(int i = 0; i < temp; i++){
        split.remove(0);
      }
    }
    
    //RESET ACCESS VARIABLES
    canBet = true;
    canSplit = false;
    canStand = false;
    canDD = false;
    canSDD = false;
    isStand = false;
    isSplit = false;
    canHit[0] = false;
    canHit[1] = false;
    showScore = false;
    
    
    //RESET SCORES AND BET POTS
    sScore = 0;
    hScore = 0;
    
    bets[0] = 0;
    bets[1] = 0;
  }
  
  //GETTERS////////////////////////////////////////////////////////////
  public int getBalance(){ return balance; }
  public int getBets(int i){ return bets[i]; }
  public String getName(){ return playerName; }
  public int getHandSize(int s) { return field.get(s).size(); }
  public int getScore(int s) 
  { if(s == 0) { return hScore; } 
    if(s == 1 && isSplit) { return sScore; }
    else { return 0; } }
  
  //SETTERS////////////////////////////////////////////////////////////
  public void setBalance(int m){ balance += m; }
  
  
  //DRAW//////////////////////////////////////////////////////////////////
  public void draw(Graphics g, Graphics2D g2)
  {
    //DRAW PLAYER NAME
    g.setFont(new Font("Times", Font.PLAIN, 30));
    g.drawString(playerName, chipX + 75, chipY + 10);
    
    
    g.setFont(new Font("Times", Font.PLAIN, 24));
    
    //DRAW CARDS IN THE HAND
    int dx = 0;
    for(ArrayList<Card> a : field)
    {
      for(Card c : a)
      {
        c.draw(g2, cardX + dx, cardY);
        dx+= 15;
      }
      
      dx+= 80;
    }
    
    //DRAW BALANCE
    int temp = balance;
    int dy = 0;
    for(int i = 4; i > -1; i--)
    {
      dy = 0;
      while(temp > chips[i].getValue()) {
        temp -= chips[i].getValue();
        chips[i].draw(g2, chipX + 90 - (i*30), chipY + 80 + dy);
        dy -= 5;
      }
    }
    g.drawString("Balance: " + balance, chipX - 30, chipY + 120);
    
    //DRAW BET POTS
    temp = bets[0] + bets[1];
    dy = 0;
    dx = 0;
    for(int i = 4; i > -1; i--)
    {
      while(temp > chips[i].getValue()) {
        temp -= chips[i].getValue();
        chips[i].draw(g2, chipX + dx, chipY + dy);
        dy -= 5;
      }
      if(i <= 3)
        dy = 40;
      else
        dy = 0;
      
      if(i == 4)
        dx = 40;
      else if(i == 3)
        dx = 0;
      else if(i == 2)
        dx = 25;
      else if(i == 1)
        dx = 50;
    }
    if(showScore){
      g.drawString("Bet: " + bets[0], chipX + 80, chipY + 50);
      g.drawString(" " + hScore + " ", cardX, chipY - 15); //DRAW SCORE
      if(isSplit){
        g.drawString("Split Bet: " + bets[1], chipX + 80, chipY + 70);
        g.drawString(" " + sScore + " ", cardX + 80, chipY - 15); //DRAW SCORE 
      }
    }
  }
}
