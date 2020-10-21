/* 
 * ATLAS KAAN YILMAZ
 * 
 * COMMON EXAM MAKE-UP
 * 
 * BATTLESHIPS
 * 
 * 7 APRIL 2019
 * 
 */ 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class BattleFrame extends JFrame
{
  final int WIDTH = 1000, HEIGHT = 1100;
  
  BattlePlayer p1, p2;
  
  //CONSTRUCTOR
  public BattleFrame()
  {
    this.setName("BattleShip");
    this.setSize(WIDTH, HEIGHT);
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    setup();
    
    this.setVisible(true);
  } 
  
  //SETS UP THE FRAME AND PLAYERS
  public void setup()
  {
    
    p1 = new BattlePlayer(this, 1);
    p2 = new BattlePlayer(this, 2);
    
    this.getContentPane().add(p1);
    
    p1.setVisible(true);
  }
  
  //INTERFACE TO CHANGE THE CURRENT PLAYER
  public void changePlayer(int n)
  {
    if(n == 1)
    {
      this.getContentPane().remove(p1);
      this.getContentPane().add(p2);
      
      p1.setVisible(false);
      p2.setVisible(true);
    }
    else if(n == 2)
    {
      this.getContentPane().remove(p2);
      this.getContentPane().add(p1);
      
      p2.setVisible(false);
      p1.setVisible(true);
    }
  }
  
  //INTERFACE BETWEEN THE PLAYERS WHEN A SHOT IS FIRED
  public boolean shotFired(int pNo, int index)
  {
    if(pNo == 1)
    {
      return p2.receiveShot(index);
    }
    else 
    {
      return p1.receiveShot(index);
    }
    
  }
  
    
}







