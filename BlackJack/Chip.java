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


public class Chip
{
  
  private int value;
    
  BufferedImage img;
    
  //CONSTRUCTOR
  public Chip(int v, BufferedImage b)
  {
    value = v;
    img = b;
  }
  
  public int getValue() { return value; }
  public BufferedImage getImg() { return img; }
  
  //DRAW CHIP AT GIVEN COORDINATE
  public void draw(Graphics2D g2, int x, int y)
  {
    g2.drawImage(img, null, x, y); 
  }
  
}