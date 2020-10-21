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
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JComboBox;

public class CBattlePlayer extends JPanel
{
  
  final int BOARD_SIZE = 100;
  
  CBattleFrame sup;
  
  CBattleClient client;
  
  int playerNo, sCount = 0;
  
  int shotAcc = 0;
  
  boolean shot = true, shipSet = false;
  
  ArrayList<JButton> enemySea;
  ArrayList<JLabel> friendSea;
  
  JPanel fSea, eSea, board, shipPos, control;
  JButton nextP, conf;
  
  public CBattlePlayer(CBattleFrame s, int n, CBattleClient c)
  {
    client = c;
    sup = s;
    playerNo = n;
    
    System.out.println("Player");
    
    setup();
  }
  
  //////////////////////////////////////////////////////PLAYER SETUP
  public void setup()
  {
    enemySea = new ArrayList<JButton>();
    friendSea = new ArrayList<JLabel>();
    
    fSea = new JPanel();
    eSea = new JPanel();
    board = new JPanel();
    control = new JPanel();
    
    fSea.setLayout(new GridLayout(10, 10));
    eSea.setLayout(new GridLayout(10, 10));
    board.setLayout(new GridLayout(2,1));
    control.setLayout(new GridLayout(1,2));
    
    seaSetUp();
    shipSetUp();
    
    nextP = new JButton("End Turn");
    conf = new JButton("Confirm P Change");
    
    nextP.setEnabled(false);
    conf.setEnabled(true);
    
    nextP.addActionListener(new ActionListener()
                            {
        public void actionPerformed(ActionEvent e)
        {
          endT();
        }
      });
    
    conf.addActionListener(new ActionListener()
                            {
        public void actionPerformed(ActionEvent e)
        {
          //confirmP();
        }
      });
    
    String n = "Player " + playerNo;
    JLabel pName = new JLabel(n); 
    
    control.add(conf);
    control.add(nextP);
    
    board.add(eSea);
    board.add(fSea);
    
    this.add(pName);
    this.add(board);
    this.add(control, BorderLayout.SOUTH);
    
    eSea.setVisible(false);
    fSea.setVisible(true);
    
    client.chkSTP();
  }
  
  //////////////////////////////////////////////////////////////////FRIENDLY AND ENEMY SEA SET UP
  public void seaSetUp()
  {
    System.out.println("SeaSetUp");
    
    for(int i = 0; i < BOARD_SIZE; i++)
    {
      JButton b = new JButton(Integer.toString(i));
      JLabel l = new JLabel(Integer.toString(i));
      
      b.setForeground(Color.BLUE);
      l.setForeground(Color.BLUE);
      
      b.addActionListener(new ActionListener()
                            {
        public void actionPerformed(ActionEvent e)
        {
          if(!shot)
          {
            fireShot(b.getText());
            nextP.setEnabled(true);
            shot = true;
          }
        }
      });
      
      enemySea.add(b);
      eSea.add(b);
      
      friendSea.add(l);
      fSea.add(l);
    } 
  }
  
  ////////////////////////////////////////////////////////////FRIENDLY SHIP POSITIONING
  private void shipSetUp()
  {
    shipPos = new JPanel();
    
    shipPos.setLayout(new GridLayout(5,1));
    
    boolean temp = true;
    
    for(int i = 2; i < 6; i++)
    {
      JPanel p = new JPanel(new GridLayout(1,6));
      
      JLabel l = new JLabel("Ship Length " + i);
      JLabel l2 = new JLabel("Enter Starting Coordinate");
      JLabel l3 = new JLabel("Select Facing Direction");
      
      JTextField t = new JTextField();
      
      String[] directions = {"Right", "Down"};  
        
      JComboBox dirx = new JComboBox(directions);
      
      JButton b = new JButton("Confirm");
      
      int j = i;
      
      b.addActionListener(new ActionListener()
                            {
        public void actionPerformed(ActionEvent e)
        {
          String s = (String) dirx.getSelectedItem();
          int c = Integer.parseInt(t.getText());
          
          if(checkPlacement(c, j, s))
          {
            t.setEnabled(false);
            dirx.setEnabled(false);
            setEnabled(false);
            sCount++;
            if(sCount == 5)
            {
              nextP.setEnabled(true);
            }
          }
        }
      });
      
      p.add(l);
      p.add(l2);
      p.add(t);
      p.add(l3);
      p.add(dirx);
      p.add(b);
      
      shipPos.add(p);
      
      if(i == 3 && temp)
      {
        i--;
        temp = false;
      }
    }
    
    shipPos.setVisible(false);
    this.add(shipPos, BorderLayout.NORTH);
  }
  
  //////////////////////////////////////CHECK WHETHER THE PLACE IS OK FOR SHIP PLACEMENT
  public boolean checkPlacement(int index, int shipL, String dirx)
  {
    if(friendSea.get(index).getForeground() == Color.GREEN)
      return false;
        
    for(int i = 1; i < shipL; i++)
    {
      if(dirx == "Right")
      {
        if(index + i > 99 || ((index + i - 1) % 10 == 9))
          return false;
        else if(friendSea.get(index + i).getForeground() == Color.GREEN)
          return false;
      }
      else if(dirx == "Down")
      {
        if(index + i * 10 > 99)
          return false;
        else if(friendSea.get(index + i * 10).getForeground() == Color.GREEN)
          return false;
      }
    }
    
    for(int i = 0; i < shipL; i++)
    {
      if(dirx == "Right")
      {
        friendSea.get(index + i).setForeground(Color.GREEN);
      }
      else if(dirx == "Down")
      {
        friendSea.get(index + i * 10).setForeground(Color.GREEN);
      }
    }
    
    return true;
    
  }
  
  //////////////////////////////////////////////////////////////////PLAYER AND TURN COMMANDS
  
  //END TURN AND WAIT FOR SHOT
  public void endT()
  {
    receiveShot(Integer.parseInt(client.read()));
  }
  
  
  /////////////////////////////////////////////////////////////////SHOOTING GAME MECHANICS
  
  //FIRES A SHOT TO ENEMY SEA
  public void fireShot(String index)
  {
    int i = Integer.parseInt(index);
    
    client.sendData(index);
    
    String s = client.read();
    
    if(s == "T")
    {
      enemySea.get(i).setForeground(Color.RED);
      enemySea.get(i).removeActionListener(enemySea.get(i).getActionListeners()[0]);
      shotAcc++;
      if(shotAcc == 17)
      {
        int result = JOptionPane.showConfirmDialog(null, "Player " + playerNo + " has won the Game!", "Quit", JOptionPane.DEFAULT_OPTION); 
        if (result == 0) 
          System.exit(0);
      }
    }
    else if(s == "F")
    {
      enemySea.get(i).setForeground(Color.WHITE);
      enemySea.get(i).removeActionListener(enemySea.get(i).getActionListeners()[0]);
    }
      
  }
  
  //RECEIVES A SHOT FROM THE ENEMY
  public void receiveShot(int index)
  {
    if(friendSea.get(index).getForeground() == Color.GREEN)
    {
      friendSea.get(index).setForeground(Color.RED);
      friendSea.get(index).setBackground(Color.RED);
      client.sendData("T");
    }
    else
    {
      friendSea.get(index).setForeground(Color.MAGENTA);
      client.sendData("F");
    }
  }
}