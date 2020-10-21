
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
import java.awt.Color;

public class Polygon extends JComponent
{
  //Declare the ArrayList for the vertices of the polygon
  ArrayList<Point2D.Double> points;
  
  public final int FRAME_LIMIT = 800;
  
  public Color color;
  
  //Constructor
  public Polygon()
  {
    points = new ArrayList<Point2D.Double>(); //Initialise the ArrayList
    color = Color.BLACK;
  }
  
  //Sets the color of the brush to the specified color
  public void setColor(Color newColor)
  {
    color = newColor;
  }
  
  //Adds aPoint to the ArrayList containing the vertices
  public void add(Point2D.Double aPoint)
  {
    points.add(aPoint);
  }
  
  public void translate(double x, double y)
  {
    
    boolean check = true;
    
    for(int i = 0; i < points.size(); i ++)
    {
      if(points.get(i).getX()+x > FRAME_LIMIT || points.get(i).getX()+x < 0)
      {
        check = false;
        break;
      }
      else if(points.get(i).getY()+y > FRAME_LIMIT || points.get(i).getY()+y < 0)
      {
        check = false;
        break;
      }
    }
    
    if(check)
    {
      for(Point2D.Double point : points)
      {
        point.setLocation(point.getX()+x, point.getY()+y);
      }
    }
    
  }
  
 
  
  
  //Draws the Polygon
  public void draw(Graphics2D g2)
  {
    //Initialise a path to connect the points
    Path2D.Double path = new Path2D.Double();
    
    //Sets the starting point of the path to the first point in the ArrayList
    Point2D.Double startPoint = points.get(0);
    
    //Moves the pointer to the start position
    path.moveTo(startPoint.getX(), startPoint.getY());
    
    //Cycle all the points in the ArrayList, drawing a path between them
    for(Point2D.Double point : points)
    {
      path.lineTo(point.getX(),point.getY());
    }
    
    //Closes the path by drawing a line from the last point to the starting position
    path.closePath();
    
    //Set Color
    g2.setColor(color);
    
    //Draws the path
    g2.draw(path);
    g2.fill(path);
    
  }
  
 
  
}