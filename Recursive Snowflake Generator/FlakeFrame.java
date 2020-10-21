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
import java.awt.event.*;

//CLASS INHERITS THE JFRAME CLASS
public class FlakeFrame extends JFrame
{
  

  FlakeComponent flakeComp;  //DECLARE FLAKECOMPONENT INSTANCE
  
  JPanel control; //DECLARE CONTROL JPANEL
  int counter = 0; //COUNTER FOR THE ORDER OF THE FRACTAL
  boolean isInverse = false; //BOOLEAN TO PASS WHETHER THE FRACTAL GOES OUTWARD OR INWARD
  
  //CLASS CONSTRUCTOR
  public FlakeFrame()
  {
    //INITIALISES THE CONTROL JPANEL AND FLAKECOMPONENT
    flakeComp = new FlakeComponent();
    control = new JPanel();
    
    //CALLS THE SETUP METHOD INIT
    init();
  }
  
  public void init() //NO PARAMETER VOID METHOD
  {
    //BUTTON DECLARATIONS
    JButton resetB = new JButton("Reset"); //RESET BUTTON
    JButton nextStepB = new JButton("Forward Step"); //FORWARD STEP BUTTON
    JButton prevStepB = new JButton("Backward Step"); //BACKWARD STEP BUTTON
    prevStepB.setEnabled(false); //SET THE BACKWARD STEP DISABLED AT THE START SINCE THERE IS NO BACKWARD TO GO TO
    
    //LISTS FOR COMBOBOXES
    String[] polygons = { "3", "4", "5" }; //POLYGON LIST
    String[] invStr = { "Normal", "Inverse"}; //INVERSE STATE LIST
    String[] colors = { "Black", "Blue", "Red", "Yellow", "Green"}; //COLOR SELECTION LIST
    
    //COMBOBOX DECLARATIONS
    JComboBox polygonBox = new JComboBox(polygons); //POLYGON SELECTION
    JComboBox inverseBox = new JComboBox(invStr); //INVERSE STATE SELECTION
    JComboBox colorBox = new JComboBox(colors); //COLOR SELECTION
    
    //ACTION LISTENER DECLARATIONS
    
    class ResetListener implements ActionListener //RESET BUTTON ACTIONLISTENER
    {
      public void actionPerformed(ActionEvent e)
      {
        //CALL THE RESET METHOD IN FLAKECOMPONENT
        flakeComp.reset(Integer.parseInt(polygonBox.getSelectedItem().toString()));//SENDS THE SELECTED POLYGON NUMBER TO RESET TO THE INITIAL STEP OF THAT POLYGON
        
        counter = 0;//RESETS THE ORDER COUNTER
        
        nextStepB.setEnabled(true);//ENABLES FORWARD STEP 
        nextStepB.setSelected(false);
        
        prevStepB.setEnabled(false);//DISABLE PREVIOUS STEP BUTTON
        prevStepB.setSelected(true);
        polygonBox.setEnabled(true);//ENABLES POLYGON SELECTION
      }
    }
    
    class NextStepListener implements ActionListener //FORWARD STEP BUTTON LISTENER
    {
      public void actionPerformed(ActionEvent e)
      {
        if(counter < 5) //IF THE ORDER IS SMALLER THAN 5, GOES TO THE NEXT ORDER
        {
          flakeComp.nextStep(isInverse); // CALLS THE FORWARD METHOD TO INCREASE ORDER, ISINVERSE STATE PARAMETER
          polygonBox.setEnabled(false); //CANNOT CHANGE POLYGON AND INVERSE SELECTIONS AFTER THE FIRST STEP
          
          prevStepB.setEnabled(true); //ENABLES BACKWARD MOTION SINCE THERE HAS BEEN A FORWARD MOTION
          prevStepB.setSelected(false);
        }
        
        counter++; //INCREASE ORDER COUNTER
        
        if(counter == 5) //IF ORDER IS MORE THAN FIVE, SYSTEM GIVES STACKOVERFLOW ERROR, PREVENT THAT FROM HAPPENNING
        {
          nextStepB.setEnabled(false);
          nextStepB.setSelected(true);
        }
      }
    }
    
    class PrevStepListener implements ActionListener //BACKWARD STEP BUTTON LISTENER
    {
      public void actionPerformed(ActionEvent e)
      {
        if(counter > 0) //IF ORDER IS MORE THAN 0, GO BACKWARDS ONE STEP
        {
          flakeComp.prevStep(); //CALLS THE PREVIOUS STEP METHOD
          
          nextStepB.setEnabled(true);//ENABLES THE NEXTSTEP BUTTON IN CASE IT WAS DISABLED WHEN ORDER WAS 5
          nextStepB.setSelected(false);
        }
        
        counter--; //DECREASE THE ORDER COUNTER
        
        if(counter == 0) //IF ORDER IS 0 DISABLE BACKWARD MOTION, SINCE THERE CANNOT BE ORDER -1
        {
          polygonBox.setEnabled(true);//ENABLES POLYGON SELECTION SINCE THIS IS THE FIRST STEP ESENTIALLY
          
          prevStepB.setEnabled(false);//DISABLE THE BACKWARD BUTTON
          prevStepB.setSelected(true);
        }
        
      }
    }
    
    class PolygonListener implements ActionListener //POLYGON SELECTION LISTENER
    {
      public void actionPerformed(ActionEvent e)
      {
        flakeComp.flakeInit(Integer.parseInt(polygonBox.getSelectedItem().toString()));//INITIALISES THE FLAKE WITH GIVEN NUMBER OF VERTICES
      }
    }
    
    class InverseListener implements ActionListener //INVERSE SELECTION LISTENER
    {
      public void actionPerformed(ActionEvent e)
      {
        if(inverseBox.getSelectedItem().toString() == "Normal") //IF SELECTED STRING EQUALS NORMAL, THEN IT IS NOT INVERSED
        {
          isInverse = false;
        }
        else //IF ELSE, THEN ISINVERSED IS TRUE
        {
          isInverse = true;
        }
      }
    }
    
    class ColorListener implements ActionListener //COLOR SELECTION LISTENER, SETS THE COLOR OF THE FRACTAL ACCORDING TO THE SELECTED COLOR
    {
      public void actionPerformed(ActionEvent e)
      {
        Color c = Color.BLACK;

        
        //CASE CHECKS
        if(colorBox.getSelectedItem().toString() == "Black")
        {
          c = Color.BLACK;
        }
        else if(colorBox.getSelectedItem().toString() == "Blue")
        {
          c = Color.BLUE;
        }
        else if(colorBox.getSelectedItem().toString() == "Red")
        {
          c = Color.RED;
        }
        else if(colorBox.getSelectedItem().toString() == "Yellow")
        {
          c = Color.YELLOW;
        }
        else if(colorBox.getSelectedItem().toString() == "Green")
        {
          c = Color.GREEN;
        }
        
        flakeComp.setColor(c);//CALLS THE SET COLOR METHOD WITH C PARAMETER
      }
    }
    
    
    //ACTION LISTENER ATTACHMENTS TO THE BUTTONS
    resetB.addActionListener(new ResetListener());
    nextStepB.addActionListener(new NextStepListener());
    prevStepB.addActionListener(new PrevStepListener());
    polygonBox.addActionListener(new PolygonListener());
    inverseBox.addActionListener(new InverseListener());
    colorBox.addActionListener(new ColorListener());
    
    //ADDITION OF THE BUTTON OBJECTS TO THE CONTROL PANEL IN ORDER
    control.add(resetB);
    control.add(polygonBox);
    control.add(inverseBox);
    control.add(colorBox);
    control.add(prevStepB);
    control.add(nextStepB);
    
    
    this.getContentPane().add(control, BorderLayout.SOUTH); //ADD CONTROL PANEL TO THE FRAME
    this.add(flakeComp, BorderLayout.CENTER); //ADD FLAKE COMPONENT TO THE FRAME
    
  }
  
  
}