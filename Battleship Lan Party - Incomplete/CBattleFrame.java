/* 
 * ATLAS KAAN YILMAZ & SERDAR SUNGUN
 * 
 * LONG TERM PROJECT
 * 
 * BATTLESHIP LAN
 * 
 * 8 MAY 2019
 * 
 */ 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class CBattleFrame extends JFrame
{
  final int WIDTH = 1000, HEIGHT = 1100;
  
  CBattlePlayer p1, p2;
  
  CBattleClient client;
  
  String args[];
  
  //CONSTRUCTOR
  public CBattleFrame(String a[])
  {
    args = a;
    
    this.setName("BattleShip");
    this.setSize(WIDTH, HEIGHT);
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    client = new CBattleClient(a[0]);
    
    setup();
    
    this.setVisible(true);
  } 
  
  //SETS UP THE FRAME AND PLAYERS
  public void setup()
  {
    p1 = new CBattlePlayer(this, 1, client);
    
    this.getContentPane().add(p1);
    
    p1.setVisible(true);
  }
    
}







