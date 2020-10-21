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
import java.awt.event.*;

public class GasketFrame extends JFrame
{
  GasketComponent gasketComp;//declare gasket component
  
  JPanel control;//declare control panel
  
  final int DELAY = 50;//delay for party-timer in ms
  
  //CONSTRUCTOR
  public GasketFrame()
  {
    gasketComp = new GasketComponent();//initialise gasket component
    
    control = new JPanel();//initialise control panel
    
    init();//call the init method
    
    //ADD THE GASKETCOMP AND CONTROL PANEL TO THE FRAME
    this.add(gasketComp, BorderLayout.CENTER);
    this.getContentPane().add(control, BorderLayout.SOUTH);
  }
  
  //INITIALISER METHOD
  public void init()
  {
    //BUTTON DECLARATIONS AND INITIALISATIONS
    JButton updateB = new JButton("Draw Gasket");//update button
    JButton resetB = new JButton("Reset");//reset button
    JButton partyB = new JButton("Party!");//party button
    JButton stopB = new JButton("Stop Party");//party stopper button
    
    //TEXTFIELD DECLARATIONS AND INITIALISATIONS
    JTextField orderTxt = new JTextField("Enter the int order of Gasket:");//text for instruction
    JTextField orderInp = new JTextField("0");//input field
    
    orderTxt.setEditable(false);//turn-off editing for the instruction textfield
    
    //TIMER LISTENERS
    class TimerListener implements ActionListener
    {
      public void actionPerformed(ActionEvent e)
      { 
        gasketComp.gasketise(Integer.parseInt(orderInp.getText()));//redraws the gasket, since it draws with random colors everytime quik redrawing creates a party
      }
    }
    
    //TIMER DECLARATION AND INITIALISATION
    Timer t = new Timer(DELAY, new TimerListener());//party timer
    
    
    //BUTTON LISTENERS
    class UpdateListener implements ActionListener //LISTENER FOR UPDATE BUTTON
    {
      public void actionPerformed(ActionEvent e)
      { 
        gasketComp.gasketise(Integer.parseInt(orderInp.getText()));//draw the gasket with the order taken from the orderInp textfield
      }
    }
    
    class ResetListener implements ActionListener //LISTENER FOR RESET BUTTON
    {
      public void actionPerformed(ActionEvent e)
      {
        gasketComp.reset();//calls the reset
        orderInp.setText("0");//sets the order to zero
        
        t.stop();//stops the party timer
        
        //ENABLES UPDATE BUTTON AND ORDER INPUT FIELD
        updateB.setEnabled(true);
        orderInp.setEnabled(true);
      }
    }
    
    class PartyListener implements ActionListener //LISTENER FOR PARTY BUTTON
    {
      public void actionPerformed(ActionEvent e)
      { 
        t.start();//start the party timer
        
        //DISABLES UPDATE BUTTON AND ORDER INPUT FIELD, SO THAT THE ORDER CANNOT BE CHANGED WHILE PARTYING
        updateB.setEnabled(false);
        orderInp.setEnabled(false);
      }
    }
    
    class StopListener implements ActionListener //LISTENER FOR STOP PARTY BUTTON
    {
      public void actionPerformed(ActionEvent e)
      { 
        t.stop();//stops the timer
        
        //ENABLES UPDATE BUTTON AND ORDER INPUT FIELD
        updateB.setEnabled(true);
        orderInp.setEnabled(true);
      }
    }
    
    //ADD THE ACTIONLISTENERS TO THE BUTTONS
    updateB.addActionListener(new UpdateListener());
    resetB.addActionListener(new ResetListener());
    partyB.addActionListener(new PartyListener());
    stopB.addActionListener(new StopListener());
    
    
    //PLACEMENT INTO THE CONTROL PANEL
    
    control.setLayout(new GridLayout(2,1));//separate the control panel to top and bottom panels
    
    //DECLARE AND INIT TOP AND BOTTOM CONTROL PANELS
    JPanel top = new JPanel(new GridLayout(1,4));
    JPanel bottom = new JPanel(new GridLayout(1,2));
    
    //ADD ELEMENTS TO THE TOP PANEL
    top.add(orderTxt);
    top.add(orderInp);
    top.add(updateB);
    top.add(resetB);
    
    //ADD ELEMENTS TO THE BOTTOM PANEL
    bottom.add(partyB);
    bottom.add(stopB);
    
    //ADD TOP AND BOTTOM PANELS TO THE CONTROL PANEL
    control.add(top);
    control.add(bottom);
  }
}