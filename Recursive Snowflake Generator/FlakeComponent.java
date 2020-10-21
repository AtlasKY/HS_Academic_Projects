/*
 * ATLAS KAAN YILMAZ
 * 
 * 5 DECEMBRE 2018
 * 
 * SNOWFLAKE PROJECT
 * 
 */
import javax.swing.*;
import java.awt.*;
import java.awt.Color;

//FLAKE COMPONENT INHERITS JCOMPONENT CLASS
public class FlakeComponent extends JComponent
{
  
  Flake flake; //DECLARE A FLAKE TYPE FLAKE
  
  public FlakeComponent() //FLAKE COMPONENT CONSTRUCTOR
  {
    flake = new Flake(); //INITALISES THE FLAKE
  }
  
  public void flakeInit(int vertexNo) //INITIALISES THE FLAKE WITH GIVEN NUMBER OF VERTICES
  {
    flake.init(vertexNo); //CALLS THE INIT METHOD IN FLAKE
    repaint();//REPAINTS THE OBJECT
  }
  
  public void nextStep(boolean isInverse)
  {
    flake.nextStep(isInverse); //CALLS THE NEXT STEP METHOD WITH INVERSE STATE PARAMETER
    repaint();//REPAINTS THE OBJECT
  }
  
  public void prevStep()
  {
    flake.prevStep(); //CALLS THE PREVIOUS STEP METHOD
    repaint();//REPAINTS THE OBJECT
  }
  
  public void reset(int vertexNo)
  {
    flake.reset(vertexNo); //CALLS THE RESET METHOD
    repaint();//REPAINTS THE OBJECT
  }
  
  public void setColor(Color c)
  {
    flake.setColor(c); //CALLS THE SETCOLOR METHOD
    repaint();//REPAINTS THE OBJECT
  }
  
  public void paintComponent(Graphics g)
  {
    
    flake.draw(g); //CALLS THE DRAW METHOD IN FLAKE WITH GRAPHICS PARAMETER
    
  }
  
}
  