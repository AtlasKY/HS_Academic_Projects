/*
 * ATLAS KAAN YILMAZ
 * 
 * SIERPINSKI GASKET
 * 
 * 12 DECEMBRE 2018
 * 
 */
import javax.swing.*;
import java.awt.*;


public class GasketComponent extends JComponent
{
  Gasket gasket;//declare Ggsket
  
  int gasketOrder = 0;//declare and init gasketOrder integer
  
  //GASKETCOMPONENT CONSTRUCTOR
  public GasketComponent()
  {
    gasket = new Gasket();//initialise a new gasket instance
  }
  
  //GASKET ORDER UPDATE
  public void gasketise(int order)
  {
    this.gasketOrder = order;//sets the gasket order to the int referenced
    
    repaint();//repaints the component
  }
  
  //RESET METHOD, SETS THE ORDER TO ZERO
  public void reset()
  {
    gasketOrder = 0;//sets the order to zero
    
    repaint();//repaints the component
  }
  
  //PAINT COMPONENT
  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g; //type cast the graphics component
    
    gasket.draw(g2, gasketOrder);//call the draw method in the gasket instance
  }
}