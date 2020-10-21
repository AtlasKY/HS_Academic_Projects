/*
 *ATLAS KAAN YILMAZ & SERDAR SUNGUN 
 * 
 * BLACKJACK FINAL PROJECT
 * 
 * 12 JANUARY 2019
 * 
 */ 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//FRAME TO GET THE NUMBER OF PLAYERS
public class InitFrame extends JFrame
{
  JPanel panel;
  int plNo;
  BJFrame game;
  
  //CONSTRUCTOR
  public InitFrame()
  {
    this.setName("Black Jack");
    this.setSize(400, 400);
    this.setResizable(false);
    
    setup(); //setup method
    
    this.setVisible(true);
    
  }
  
  //SETS THE FRAME UP
  public void setup()
  {
    
    panel = new JPanel(new GridLayout(3,1)); //panel with grid layout
    
    String[] plr = {"1","2","3"}; //number of available players
    JComboBox pNoBox = new JComboBox(plr); //choice box
    
    JLabel insTxt = new JLabel("Select the Number of Players"); //label for instructions
    
    JButton goB = new JButton("Go"); //button to start playing
    goB.addActionListener(new ActionListener()
                            {
      public void actionPerformed(ActionEvent e)
      {
        setVisible(false); //when go make the frame disappear
        plNo = Integer.parseInt(pNoBox.getSelectedItem().toString()); //get the number of players in int format
        game = new BJFrame(plNo); //Initialise the game frame with player no
      }
    });
    
    //add elements to panel
    panel.add(insTxt);
    panel.add(pNoBox);
    panel.add(goB);
    
    //add panel to frame
    getContentPane().add(panel);
    
  }
  
  
  
}