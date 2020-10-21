/*
 *ATLAS KAAN YILMAZ & SERDAR SUNGUN 
 * 
 * BLACKJACK FINAL PROJECT
 * 
 * 12 JANUARY 2019
 * 
 */ 
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.event.*;

//GAME RUNS HERE
public class BJFrame extends JFrame {
  
  public final int WIDTH = 800, HEIGHT = 550; //frame size
  
  BJComponent comp; //BlackJack Component
  JPanel control;
  BufferedImage table, cards, back, c1, c5, c25, c100, c500; //images declared
  int noPlayer; //number of players
  ArrayList<Player> players; //arraylist for players
  Chip[] chips; //array for individual chip templates
  
  //for local access declaration
  JLabel pDisplay;
  JTextField betInp;
  JButton hitB;
  JButton hitSplitB;
  JButton betB;
  JButton ddB;
  JButton ddSB;
  JButton splitB;
  JButton standB;
  JButton scoreB;
  
  //CONSTRUCTOR//////////////////////////////////////////////////////
  public BJFrame(int pn)
  {
    noPlayer = pn;
    
    this.setName("Black Jack");
    this.setSize(WIDTH, HEIGHT);
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    setup();
    
    this.setVisible(true);
  }
  
  private void setup()
  {
    //IMPORT IMAGES
    try { 
      table = ImageIO.read(BJFrame.class.getResource("bjtable.png")); 
      cards = ImageIO.read(BJFrame.class.getResource("deck.png")); 
      back = ImageIO.read(BJFrame.class.getResource("back.png")); 
      c1 = ImageIO.read(BJFrame.class.getResource("1.png")); 
      c5 = ImageIO.read(BJFrame.class.getResource("5.png")); 
      c25 = ImageIO.read(BJFrame.class.getResource("25.png")); 
      c100 = ImageIO.read(BJFrame.class.getResource("100.png")); 
      c500 = ImageIO.read(BJFrame.class.getResource("500.png")); 
      
    } 
    catch (Exception e){
      e.printStackTrace();
    }
    ///////////////////////////////////////////////////////////
    
    //CHIPS INIT
    chips = new Chip[5];
    chips[0] = new Chip(1,   c1);
    chips[1] = new Chip(5,   c5);
    chips[2] = new Chip(25,  c25);
    chips[3] = new Chip(100, c100);
    chips[4] = new Chip(500, c500);
    //////////////////////////////
    
    ////PLAYERS INIT
    players = new ArrayList<Player>();
    
    //coordinates are pre-mesured
    players.add(new Player("Player 1", 340, 220, 350, 350, chips));
    if(noPlayer > 1)
      players.add(new Player("Player 2", 600, 130, 620, 270, chips));
    if(noPlayer > 2)
      players.add(new Player("Player 3", 70, 130, 80, 270, chips)); 
    ///////////////////////////////////////////////////////////////
    
    //init the BJComponent
    comp = new BJComponent(table, cards, back, chips, players, noPlayer);
    
    cPanel(); //setup control panel
    
    add(comp, BorderLayout.CENTER);
    getContentPane().add(control, BorderLayout.SOUTH);
    
  }
  
  //CONTROL PANEL
  private void cPanel()
  {
    control = new JPanel(new GridLayout(2,1));
    
    betInp = new JTextField();
    pDisplay = new JLabel(comp.getPlayer().getName()); //display player name
    
    BJFrame f = this; //for access from within action listeners
    
    //INITIALISERS///////////////////////////////////////////
    betB = new JButton("Bet"); 
    hitB = new JButton("Hit"); 
    hitB.setEnabled(false); 
    hitSplitB = new JButton("Hit Split"); 
    hitSplitB.setEnabled(false);
    hitSplitB.setVisible(false);
    ddB = new JButton("Double Down");
    ddB.setEnabled(false);
    ddSB = new JButton("Double Down Split");
    ddSB.setEnabled(false);
    ddSB.setVisible(false);
    splitB = new JButton("Split");
    splitB.setEnabled(false);
    standB = new JButton("Stand");
    standB.setEnabled(false);
    scoreB = new JButton("Cash & New Round");
    scoreB.setVisible(false);
    
    //ACTION LISTENERS//////////////////////////////////
    betB.addActionListener( new ActionListener()
                             {
      public void actionPerformed(ActionEvent e)
      {
        int bet = Integer.parseInt(betInp.getText());
        if(bet > 0 && bet < comp.getPlayer().getBalance()){
          comp.bet(bet);
          
          betInp.setEnabled(false);
          betB.setEnabled(false);
          
          nextPlayer();
        }
        else {
          JOptionPane.showMessageDialog(null, "Error: Please enter a proper bet", "Error Massage", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    
    //HIT
    hitB.addActionListener( new ActionListener()
                               {
      public void actionPerformed(ActionEvent e)
      {
        comp.hit(0);
      }
    });
    
    //HIT SPLIT
    hitSplitB.addActionListener( new ActionListener()
                               {
      public void actionPerformed(ActionEvent e)
      {
        comp.hit(1);
      }
    });
    
    //DOUBLE DOWN
    ddB.addActionListener( new ActionListener()
                            {
      public void actionPerformed(ActionEvent e)
      {
        comp.doubleDown(0);
        nextPlayer();
      }
    });
    
    //DOUBLE DOWN SPLIT
    ddSB.addActionListener( new ActionListener()
                            {
      public void actionPerformed(ActionEvent e)
      {
        comp.doubleDown(1);
        nextPlayer();
      }
    });
    
    //SPLIT
    splitB.addActionListener( new ActionListener()
                               {
      public void actionPerformed(ActionEvent e)
      {
        comp.split();
        nextPlayer();
      }
    });
    
    //STAND
    standB.addActionListener( new ActionListener()
                               {
      public void actionPerformed(ActionEvent e)
      {
        comp.stand();
        nextPlayer();
      }
    });
    
    //SCORE
    scoreB.addActionListener( new ActionListener()
                               {
      public void actionPerformed(ActionEvent e)
      {
        comp.scoreRound(f);
        comp.reset(f);
        nextPlayer();
      }
    });
    ////////////////////////////////////////////////////////////
    
    //top and bottom panel
    JPanel pTop = new JPanel(new GridLayout(1,5));
    JPanel pBot = new JPanel(new GridLayout(1,5));
    
    //add to top&bottom panels
    pTop.add(hitB);
    pTop.add(ddB);
    pTop.add(pDisplay);
    pTop.add(hitSplitB);
    pTop.add(ddSB);
    
    pBot.add(betInp);
    pBot.add(betB);
    pBot.add(splitB);
    pBot.add(standB);
    pBot.add(scoreB);
    
    //add top&bot panels to control panel
    control.add(pTop);
    control.add(pBot);
  }
  //END CONSTRUCTION///////////////////////////////////////////////////////////////////////////////////////
  
  //NEXT PLAYER CONTROL PANEL ACCESS UPDATES///////////////////////////////////
  public void nextPlayer()
  {
    comp.nextPlayer(this);
    
    hitSplitB.setVisible(false);
    ddSB.setVisible(false);
    
    if(comp.getPlayer().canBet){
      betB.setEnabled(true);
      betInp.setText("0");
      betInp.setEnabled(true);
    }
    else{
      betB.setEnabled(false);
      betInp.setText("");
      betInp.setEnabled(false);
    }
    
    //SPLIT
    if(comp.getPlayer().canSplit)
      splitB.setEnabled(true);
    else
      splitB.setEnabled(false);
    
    //DOUBLE DOWN
    if(comp.getPlayer().canDD)
      ddB.setEnabled(true);
    else
      ddB.setEnabled(false);
    
    //STAND
    if(comp.getPlayer().canStand)
      standB.setEnabled(true);
    else
      standB.setEnabled(false);
    
    //HIT
    if(comp.getPlayer().canHit[0])
      hitB.setEnabled(true);
    else
      hitB.setEnabled(false);
    
    //SPLIT CASE CHECKS////////////////////
    if(comp.getPlayer().isSplit)
    {
      ddSB.setVisible(true);
      hitSplitB.setVisible(true);
      
      //DOUBLE DOWN SPLIT
      if(comp.getPlayer().canSDD){
        ddSB.setEnabled(true);
      }
      else
        ddSB.setEnabled(false);
      
      //HIT SPLIT
      if(comp.getPlayer().canHit[1])
        hitSplitB.setEnabled(true);
      else
        hitSplitB.setEnabled(false);
    }
    ///////////////////////////////
    
    pDisplay.setText(comp.getPlayer().getName());
  }
  
  
  
  
}
