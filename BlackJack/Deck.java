/*
 *ATLAS KAAN YILMAZ & SERDAR SUNGUN 
 * 
 * BLACKJACK FINAL PROJECT
 * 
 * 12 JANUARY 2019
 * 
 */ 
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

public class Deck {
  
  BufferedImage cards, back;
  
  Random rand;
  
  ArrayList<Card> shoe, discard;
  
  private final int CWIDTH = 73, CHEIGHT = 98; //Card Dimensions
  
  private int deckNo = 4, totalCards = deckNo * 52; //number of deck and cards
  
  //CONSTRUCTOR
  public Deck(BufferedImage i, BufferedImage b) {
    
    cards = i; //cards image
    back = b; //cardback image
    
    shoe = new ArrayList<Card>();
    discard = new ArrayList<Card>();
    rand = new Random();
    
    initDeck(); //init the deck with individual cards
    
  }
  
  public void initDeck()
  {
    //cycle all the number of cards and create Card instances and add to the shoe
    for(int i = 0; i < deckNo; i++){
      for(int k = 0; k < 4; k++) {
        for(int j = 0; j < 13; j++)
          shoe.add(new Card(j + 1, cards.getSubimage( (j * CWIDTH), (k * CHEIGHT), CWIDTH, CHEIGHT)));
      }
    }
  }
  
  //DEAL ONE CARD TO REFERENCED PLAYER'S REFERENCED HAND
  public void hit(Player p, int s)
  {
    if(p.canHit[s]){
      int i = rand.nextInt(shoe.size());
      Card c = shoe.get(i);
      shoe.remove(i);
      p.takeCard(c, s);
    }
  }
  
  //DEAL TWO CARDS TO ALL PLAYERS
  public void distribute(ArrayList<Player> ps)
  {
    for(Player p : ps)
    {
      int i = rand.nextInt(shoe.size());
      Card c = shoe.get(i);
      shoe.remove(i);
      p.takeCard(c, 0);
      
      i = rand.nextInt(shoe.size());
      c = shoe.get(i);
      shoe.remove(i);
      p.takeCard(c, 0);
    }
    
  }
  
  //DEAL ONE CARD TO DEALER
  public void hitDealer(Dealer d)
  {
    int i = rand.nextInt(shoe.size());
    Card c = shoe.get(i);
    discard.add(c);
    shoe.remove(i);
    d.takeCard(c);
  }
  
  //DRAW THE DECK
  public void drawShoe(Graphics g, Graphics2D g2)
  {
    g.setFont(new Font("Times", Font.PLAIN, 30));
    
    g2.drawImage(back, null, 670, 40); 
    g.setColor(Color.WHITE);
    g.drawString(" " + shoe.size() + " ", 670, 90);
  }
}
