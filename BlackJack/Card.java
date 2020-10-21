/*
 *ATLAS KAAN YILMAZ & SERDAR SUNGUN 
 * 
 * BLACKJACK FINAL PROJECT
 * 
 * 12 JANUARY 2019
 * 
 */ 
import java.awt.image.*;
import javax.swing.*;
import java.awt.*;

//CLASS FOR INDIVIDUAL CARDS
public class Card
{
  private int value, number; //value and number of the card
    
  BufferedImage img;
    
  public Card(int n, BufferedImage b)
  {
    number = n;
    img = b;
    
    if(number > 10) { //if J Q K value is 10
      value = 10;
    } else if(number == 1) { //if A initial value is 11
      value = 11;
    }
    else {
      value = number;
    }
    
  }
  
  public int getValue() { return value; } 
  public BufferedImage getImg() { return img; }
  
  //Change A value from 11 to 1
  public void toggleValue() { if (value == 11) { value = 1; } }
  
  //DRAW THE CARD AT GIVEN COORDINATE
  public void draw(Graphics2D g2, int x, int y)
  {
    g2.drawImage(img, null, x, y); 
  }
  
}