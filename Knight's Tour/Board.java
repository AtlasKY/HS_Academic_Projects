/*
 * ATLAS KAAN YILMAZ
 * 
 * KNIGHT'S TOUR
 * 
 * 17 DEC 2018
 * 
 */ 
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

//CLASS FOR BOARD PRINTING
public class Board 
{
  
  final public double OFFSET = 10; //offset of the board from the edge
  final public double SQR_SIZE = 90; //size of the squares on the board
  final public double BRD_SIZE = SQR_SIZE * 8 + OFFSET; //Total board size
  
  //EMPTY CONSTRUCTOR
  public Board()
  {
  }
  
  //PUBLIC DRAW METHOD TO DRAW THE BOARD
  public void draw(Graphics2D g2, int selecX, int selecY)
  {
    //tracker integers
    int a = 0, b = 0;
    
    //Double for loop to cycle the board
    for(double i = OFFSET; i < BRD_SIZE; i += SQR_SIZE)
    {
      a++; //increase the row tracker
      b = 0; //reset the column tracker
      for(double j = OFFSET; j < BRD_SIZE; j += SQR_SIZE)
      {
        
        //new rectangle to draw at current point
        Rectangle2D.Double rect = new Rectangle2D.Double(j, i, SQR_SIZE, SQR_SIZE);
        
        //if the index is selected index, draw green
        if( a == selecY && b == selecX){
          g2.setColor(Color.GREEN);
        }
        else if( (a+b) % 2 == 0 ) // else, if the sum of row and column number is even draw black, makes the board black and white with no bordering same colors
        {
          g2.setColor(Color.BLACK);
        }
        else //else draw white
        {
          g2.setColor(Color.WHITE);
        }
        
        g2.fill(rect); // paint the rectangle
        b++; // increase the column tracker
      }
      
    }
    
    
  }
}