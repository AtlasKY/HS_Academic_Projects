
/*
MIDTERM
Atlas Kaan Yilmaz
Tan Gemicioglu

Wed 7 Nov 2018
*/

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class PolygonComponent extends JComponent
{
  //Declare the ArrayList for the Polygons
  ArrayList<Polygon> polygons;
  
  //Constructor
  public PolygonComponent()
  {
    polygons = new ArrayList<Polygon>(); //Initialise the ArrayList
  }
  
  //Adds aPolygon to the ArrayList containing the polygons
  public void addPolygon(Polygon aPolygon)
  {
    polygons.add(aPolygon);
    repaint(); //Updates the visual
  }
  
  //Paints the Polygons in the ArrayList
  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    
    for(Polygon polygon : polygons) //cycles through the Polygons in the ArrayList
    {
      polygon.draw(g2); //Calls the draw methods of the Polygon instances 
    }
    
    
  }
  
}