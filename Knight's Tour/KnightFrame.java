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

//FRAME CLASS INHERITS JFRAME
public class KnightFrame extends JFrame
{
  //instantiate a Knight Component
  KnightComponent kComp = new KnightComponent();
  
  //CONSTRUCTOR
  public KnightFrame()
  {
    this.add(kComp, BorderLayout.CENTER); //add the component onto the frame
    
    init();//call the init method
  }
  
  //METHOD TO INITIALISE CONTROL PANEL
  private void init()
  {    
    JPanel control = new JPanel(); //declare control panel
    
    //CREATE BUTTONS
    JButton tourB = new JButton("Tour the Knight");
    JButton resetB = new JButton("Reset");
    JButton nextB = new JButton("Next Step");
    JButton prevB = new JButton("Previous Step");
    
    //DISABLE MOVEMENT BUTTONS, SINCE THERE IS NO TOUR YET
    nextB.setEnabled(false);
    prevB.setEnabled(false);

    //CREATE TEXTFIELDS
    JTextField xStartTxt = new JTextField("1");
    JTextField yStartTxt = new JTextField("1");
    JTextField messageTxt = new JTextField("Enter starting X and Y, then press Tour.");
    
    messageTxt.setEditable(false); //message field is not editable
                        
    //ACTION LISTENERS FOR BUTTONS AND TEXTFIELDS
    
    //Knight Tour button actionlistener
    tourB.addActionListener(new ActionListener()
                              {
      public void actionPerformed(ActionEvent e)
      {
        messageTxt.setText("Working on solution"); // display on message
        
        kComp.setStart( Integer.parseInt(xStartTxt.getText()), Integer.parseInt(yStartTxt.getText()) );//update the starting x and y position
        
        kComp.tourKnight(); //tour the knight's tour
        
        //Disable changing start position
        xStartTxt.setEnabled(false);
        yStartTxt.setEnabled(false);
        
        //enable movement buttons
        nextB.setEnabled(true);
        prevB.setEnabled(true);
        
        //disable Knight tour buttons
        tourB.setEnabled(false);
        
        //display message
        messageTxt.setText("Solution on Display");
        
      }
    });
    
    //Reset action listener
    resetB.addActionListener(new ActionListener()
                              {
      public void actionPerformed(ActionEvent e)
      {
        kComp.reset(); //call reset method
        
        //set the starting position to 1x1
        xStartTxt.setText("1");
        yStartTxt.setText("1");
        
        //enable the acces to start-pos fields
        xStartTxt.setEnabled(true);
        yStartTxt.setEnabled(true);
        
        //disable movement buttons
        nextB.setEnabled(false);
        prevB.setEnabled(false);
        
        //enable tour button
        tourB.setEnabled(true);
        
        //update the start position
        kComp.setStart( Integer.parseInt(xStartTxt.getText()), Integer.parseInt(yStartTxt.getText()) );
        
        //update the message box
        messageTxt.setText("Enter starting X and Y, then press Tour.");
      }
    });
    
    
    //Next step movement event
    nextB.addActionListener(new ActionListener()
                              {
      public void actionPerformed(ActionEvent e)
      {
        kComp.nextStep(); //call the next step method
      }
    });
    
    //Previous step movement event
    prevB.addActionListener(new ActionListener()
                              {
      public void actionPerformed(ActionEvent e)
      {
        kComp.prevStep(); //call the previous step method
      }
    });
    
    //Action event class for textfields
    class TextListener implements ActionListener
    {
      public void actionPerformed(ActionEvent e)
      {
        kComp.setStart( Integer.parseInt(xStartTxt.getText()), Integer.parseInt(yStartTxt.getText()) );//update the start location
      }
    }
    
    //Add the Textlistener to the texfields
    xStartTxt.addActionListener(new TextListener());
    yStartTxt.addActionListener(new TextListener());
    
    //Add 3-layered layout
    control.setLayout(new GridLayout(3,1));
    
    //Create top, middle, and bottom panels 
    JPanel topP = new JPanel();
    JPanel midP = new JPanel(new GridLayout(1,3)); // 1 by 3 grid layout
    JPanel botP = new JPanel(new GridLayout(1,3)); // 1 by 3 grid layout
    
    //Add Message to Top Panel
    topP.add(messageTxt);
    
    
    //Add x-pos, y-pos, and Tour button to Middle Panel
    midP.add(xStartTxt);
    midP.add(yStartTxt);
    midP.add(tourB);
    
    //Add reset and movement buttons to Bottom Panel
    botP.add(resetB);
    botP.add(prevB);
    botP.add(nextB);
    
    //Add panels to the control panel
    control.add(topP);
    control.add(midP);
    control.add(botP);
    
    //add panel to the south of frame
    this.getContentPane().add(control, BorderLayout.SOUTH);
    
  }
  
}