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
import java.awt.event.*;

//CLASS FOR KNIGHT COMPONENT
public class KnightComponent extends JComponent
{
  //declare knighttour object
  KnightTour tour;
  
  //declare selected x and y
  int selecX, selecY;
  
  //declare int to track step count
  int stepCount;
  
  public KnightComponent()
  {
    reset(); //Reset works the same way as initial setup
  }
  
  //Public eset method
  public void reset()
  {
    tour = new KnightTour(); //instantiates a new object
    
    //sets the selected indexes to 0
    selecX = 0;
    selecY = 0;
    
    //sets the stepCount to 1
    stepCount = 1;
    
    //repaints the component
    repaint();
  }
  
  //Public tour the knight method
  public void tourKnight()
  {
    tour.tourK(selecX, selecY - 1); //calls the tour method inside the tour object
  }
  
  //Next step Knight movement method
  public void nextStep()
  {
    //if it is not the last step, increase the stepCounter
    if(stepCount < 64)
      stepCount++;
    
    repaint();
  }
  
  //Previous steo Knight Movement Method
  public void prevStep()
  {
    if(stepCount > 1) //if it is not the first step, decrease the stepCounter
      stepCount--;
    
    repaint();
  }
  
  //Method to update the selected X and Y values
  public void setStart(int x, int y)
  {
    selecX = x - 1; //x-1 to adjust error, I have no idea why this works or doesn't otherwise
    selecY = y; 
    
    repaint();
  }
  
  public void paintComponent(Graphics g)
  {
    
    tour.draw(g, selecX, selecY, stepCount); //calls the draw method, with selected indexes and the current step
   
  }
  
}