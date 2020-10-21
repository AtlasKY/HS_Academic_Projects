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
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;

public class Gasket
{
  ArrayList<Point2D.Double> pointList; //declare arraylist to hold the initial points
  
  ArrayList<Color> colors; //declarearraylist to hold the colors
  
  Random rand; //declare a random generator
  
  public Gasket()
  {
    pointList = new ArrayList<Point2D.Double>(); //initialise the points arraylist
    
    rand = new Random(); //initialise the random generator
    
    init(); //call the init method for initial points
  }
  
  //POINT INIT METHOD
  private void init()
  {    
    //ADDS THE INITIAL POINTS TO THE ARRAYLIST
    pointList.add(new Point2D.Double(400,50));
    pointList.add(new Point2D.Double(750,750));
    pointList.add(new Point2D.Double(50,750));
  }
  
  //RECURSIVE METHOD FOR GASKET DRAWING
  /* Input References:
   *    g2 - Graphics to draw and fill
   *    order - current order of the gasket
   *    p1 - first point of the triangle
   *    p2 - second point of the triangle
   *    p3 - third point of the triangle
   *    c - arraylist holding the specific colors for specific orders of the gasket
   *    cIndex - int to keep track of which color order the current triangle belongs to
   */ 
  private void recursiveDraw(Graphics2D g2, int order, Point2D.Double p1, Point2D.Double p2, Point2D.Double p3, ArrayList<Color> c, int cIndex)
  {
    //BASE CASE ORDER IS ZERO, DRAW THE TRIANGLE
    if(order==0)
    {
      Path2D.Double path = new Path2D.Double(); //create a path2d object
      
      path.moveTo(p1.getX(), p1.getY()); //start from the first point
      
      path.lineTo(p2.getX(), p2.getY()); //connect to second point
      path.lineTo(p3.getX(), p3.getY()); //connect to third point
      
      path.closePath(); //close the path object and create the triangle shape
      
      g2.setColor(c.get(cIndex)); //set the color of the brush to the color of the current order
      
      g2.draw(path); //draw and fill the path object
      g2.fill(path);
      
    }
    else if(order > 0) //IF ORDER IS NOT ZERO, RECURSE
    {
      
      /*METHOD CALL FOR MIDDLE TRIANGLE
       * order is zero, no more recursion for middle part
       * draws a big triangle at the back of the smaller triangles
       * referense the color index directly for the current color order */
      recursiveDraw(g2, 0, p1, p2, p3, c, cIndex); 
      
      /*METHOD CALLS FOR OUTER TRIANGLES
       * decrease orders by one to recurse
       * references the midpoints by calling the midpoint method
       * increase the color index by one to the next color order */
      recursiveDraw(g2, order-1, midPoint(p1,p2), p2, midPoint(p3,p2), c, cIndex + 1); 
      recursiveDraw(g2, order-1, midPoint(p2,p3), p3, midPoint(p3,p1), c, cIndex + 1);
      recursiveDraw(g2, order-1, midPoint(p3,p1), p1, midPoint(p1,p2), c, cIndex + 1);
    }
  }
  
  //METHOD FOR MIDPOINT CALCULATION
  private Point2D.Double midPoint(Point2D.Double p1, Point2D.Double p2)
  {
    return new Point2D.Double( (p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2); //returns a new Point2D that is the midpoint between p1 and p2
  }
  
  //METHOD FOR GENERATING RANDOM COLORS
  private Color randColor()
  {
    //Alternative color generation
    int i = 255 - rand.nextInt(255);
    int j = i - rand.nextInt(i);
    int l = j - rand.nextInt(j);
    //Color c = new Color( i, j, l);
    
    Color c = new Color( rand.nextInt(255) , rand.nextInt(255), rand.nextInt(255)); //creates a new color with random R, G, and B values
    
    return c; //returns the new color
  }
  
  //PUBLIC METHOD FOR DRAWING THE GASKET
  public void draw(Graphics2D g2, int order)
  {
    
    colors = new ArrayList<Color>(); //initialises the colors arraylist
    
    int cIndex = 0; //creates a color index integer
    
    //ADDS SOME NUMBER OF COLORS EQUAL TO THE ORDER OF THE GASKET TO THE COLORS ARRAYLIST
    for(int i = 0; i <= order; i++)
    {
      colors.add(randColor()); //add a random color to the list
    }
    
    recursiveDraw(g2, order, pointList.get(0), pointList.get(1), pointList.get(2), colors, cIndex); //calls the private method for recursive drawing
    
  }
  
  
}