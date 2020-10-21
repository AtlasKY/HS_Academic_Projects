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

public class Tester
{
  public static void main(String[] args)
  {
    //MAIN METHOD INITIALISES THE FLAKEFRAME CLASS
    JFrame frame = new FlakeFrame();
    frame.setSize(700, 800);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setVisible(true);
    
  }
}