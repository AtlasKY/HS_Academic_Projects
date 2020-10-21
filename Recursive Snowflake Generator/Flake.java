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
import java.util.ArrayList;
import java.awt.geom.*;
import java.awt.Color;

public class Flake
{
  //POINT2D ARRAYLIST TO HOLD THE POINTS THAT MAKE UP THE FRACTAL
  ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
    
  //COLOR VARIABLE TO HOLD THE COLOR OF THE OBJECT
  Color color = Color.BLACK;
  
  
  public Flake() //CONSTRUCTOR WITHOUT PARAMETERS, BASIC POLYGON IS A TRIANGLE
  {
    init(3);
  }
  
  public void init(int vertexNo)//INITIALISES THE POLYGON SHAPE WITH GIVEN NUMBER OF VERTICES
  {
    if(vertexNo == 3) //DRAWS A TRIANGLE
    {
      points.clear(); //CLEARS ANY CURRENTLY HELD POINTS
      points.add(new Point2D.Double(150,150));
      points.add(new Point2D.Double(550,150));
      points.add(new Point2D.Double(350, 150 + Math.sin(Math.PI/3)*400));
      points.add(new Point2D.Double(150,150)); //RE-ADDITION OF THE FIRST POINT SO THAT A COMPLETE CYCLE MAY BE COMPLETED IN RECURSION
    }
    else if(vertexNo == 4)//DRAWS SQUARE
    {
      points.clear();
      points.add(new Point2D.Double(150,150));
      points.add(new Point2D.Double(550,150));
      points.add(new Point2D.Double(550,550));
      points.add(new Point2D.Double(150,550));
      points.add(new Point2D.Double(150,150));
    }
    else if(vertexNo == 5) //DRAWS PENTAGON
    {
      points.clear();
      points.add(new Point2D.Double(150,283));
      points.add(new Point2D.Double(350,150));
      points.add(new Point2D.Double(550,283));
      points.add(new Point2D.Double(470,550));
      points.add(new Point2D.Double(230,550));
      points.add(new Point2D.Double(150,283));
    }
  }
  
  public void reset(int vertexNo) //RESETS ALL THE POINTS AND THEN RE-INITIALISES THE GIVEN POLYGON
  {
    points.clear();
    
    init(vertexNo);
  }
  
  public void nextStep(boolean isInverse) //THE PUBLIC CALL FOR NEXT STEP
  { 
    ArrayList<Point2D.Double> tempList = new ArrayList<Point2D.Double>(); //CREATES A TEMPORARY ARRAYLIST FOR NEW ORDER FRACTAL
    
    step(points.size()-1, tempList, isInverse); //CALLS THE RECURSIVE METHOD, SENDS THE NUMBER OF POINTS CURRENTLY PRESENT, THE TEMPORARY ARRAYLIST AND WHETHER IT IS INVERSED OR NOT
    
    points = tempList; //SETS THE POINTS EQUAL TO THE TEMPORARY ARRAYLIST
    
  }
  
  public void prevStep() //GOES TO THE PREVIOUS STEP
  {    
    for(int i = 0; i < points.size()-1; i++) //IN EACH FORWARD STEP THERE IS AN ADDITION OF 3 EXTRA POINTS BETWEEN EACH POINT, BACKWARD METHOD REMOVES THOSE EXTRA ADDITIONS TO GET THE PREVIOUS ORDER FRACTAL
    {
        points.remove(i+1);
        points.remove(i+1);
        points.remove(i+1);
    }
  }
  
  private void step(int i, ArrayList<Point2D.Double> tempList, boolean isInverse) //PRIVATE RECURSIVE METHOD FOR NEXT STEP FRACTAL GENERATION
  {

    
    if(i!=0) //IF THERE ARE SIDES LEFT TO PROCESS, I.E. WHEN 0 IS PASSED IT MEANS THERE IS ONLY A SINGLE POINT LEFT
    { 
      
      step(i-1, tempList, isInverse);//CALLS ITSELF TO START FROM THE BEGINNING, ELSE IF CALLED AT THE END THE ORDER OF THE ARRAYLIST GETS INVERTED
      
      double xIndex = points.get(i-1).getX() - points.get(i).getX(); // X INDEX DENOTES THE CHANGE IN THE X COORDINATE BETWEEN THE TWO POINTS
      double yIndex = points.get(i-1).getY() - points.get(i).getY(); // Y INDEX DENOTES THE CHANGE IN THE Y COORDINATE BETWEEN THE TWO POINTS
      
      double xTip, yTip;//DECLARE DOUBLES THAT ARE THE X AND Y COORDINATES OF THE TRIANGLE TIP THAT FORMS AS KOCH PROCEDURE

      if(isInverse) //IF INVERSE THE TIP GOES INWARDS
      {
        xTip = (points.get(i).getX() + points.get(i-1).getX()) / 2 - ( Math.sqrt(3.0) / 6 * (-1) * yIndex ); //THE SUBSTRACTION MAKES THEM GO INWARDS
        yTip = (points.get(i).getY() + points.get(i-1).getY()) / 2 - ( Math.sqrt(3.0) / 6 * xIndex ); //HELP FROM ERAN OZSARFATI FOR MATH
      }
      else //IF NOT INVERSE THE TIP GOES OUTWARD
      {
        xTip = (points.get(i).getX() + points.get(i-1).getX()) / 2 + ( Math.sqrt(3.0) / 6 * (-1) * yIndex ); //THE ADDITION MAKES THEM GO OUTWARDS
        yTip = (points.get(i).getY() + points.get(i-1).getY()) / 2 + ( Math.sqrt(3.0) / 6 * xIndex ); //HELP FROM ERAN OZSARFATI FOR MATH
      }
      
      //ADDS NEW POINT2DS TO THE TEMPORARY ARRAYLIST
      tempList.add(new Point2D.Double( points.get(i).getX() + xIndex * 2 / 3, points.get(i).getY() + yIndex * 2 / 3)); //THE 1/3 POINT
      tempList.add(new Point2D.Double(xTip, yTip));                                                                    //TRIANGLE TIP POINT
      tempList.add(new Point2D.Double( points.get(i).getX() + xIndex / 3, points.get(i).getY() + yIndex/3));           //THE 2/3 POINT
      tempList.add(points.get(i));                                                                                     //ORIGINAL POINT

    }
    else //IF INDEX IS ZERO, THEN ADD THE LAST POINT TO THE TEMPORARY ARRAYLIST
    {
      tempList.add(points.get(i));
    }

  }
  
  public void setColor(Color c) //SET THE COLOR OF THE OBJECT WITH COLOR C PARAMETER
  {
    color = c; //SET THE COLOR EQUAL TO THE COLOR C
  }
  
  
  public void draw(Graphics g) //DRAW METHOD
  {
    Graphics2D g2 = (Graphics2D) g; //CAST THE GRAPHICS G INTO GRAPHICS2D G2
    
    Path2D.Double path = new Path2D.Double(); //INSTANTIATE A NEW PATH2D OBJECT
    
    path.moveTo(points.get(0).getX(), points.get(0).getY()); //MOVE THE PATH2D CURSORE TO THE INITIAL POINT
    
    for(Point2D.Double p : points) //FOR EACH POINT IN POINTS, TAKE A LINE FROM THE LAST POSITION OF THE PATH2D TO THAT POINT, CREATING A GEOMETRIC OBJECT
    {
      path.lineTo(p.getX(), p.getY());
    }
    
    path.closePath();//CLOSES THE PATH BY GOING TO THE INITIAL POSITION OF THE PATH2D CURSOR
    
    g2.setColor(color); //SET THE COLOR OF THE BRUSH AS COLOR
    g2.draw(path); //DRAWS THE PATH
    g2.fill(path); //FILLS THE PATH
    
  }
  
}
