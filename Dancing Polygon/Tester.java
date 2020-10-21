
/*
MIDTERM
Atlas Kaan Yilmaz
Tan Gemicioglu

Wed 7 Nov 2018
*/

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
import java.awt.event.*;
import java.io.*;

public class Tester {
  final static int DELAY = 50;

  public static void main(String[] args) {

    // NEW FRAME
    JFrame frame = new JFrame("Polygon");
    frame.setSize(800, 800);
    frame.setResizable(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Init Polygon Component Class
    PolygonComponent polyCom = new PolygonComponent();

    // Initialise 2 Polygons: Square and Pentagon
    Polygon square = new Polygon();
    Polygon pentagon = new Polygon();

    // Create and Add Points to form the Square
    square.add(new Point2D.Double(50, 50));
    square.add(new Point2D.Double(200, 50));
    square.add(new Point2D.Double(200, 200));
    square.add(new Point2D.Double(50, 200));
    // Set the color of the square to RED
    square.setColor(Color.RED);

    // Create and Add Points to form the Pentagon
    pentagon.add(new Point2D.Double(300, 300));
    pentagon.add(new Point2D.Double(450, 400));
    pentagon.add(new Point2D.Double(375, 500));
    pentagon.add(new Point2D.Double(225, 500));
    pentagon.add(new Point2D.Double(150, 400));
    // Set the Color of the pentagon to BLUE
    pentagon.setColor(Color.BLUE);

    // Add the Square and the Pentagon to the Polygon Component
    polyCom.addPolygon(square);
    polyCom.addPolygon(pentagon);

    // Initialise a random generator
    Random rand = new Random();

    // Timer Listener for the timer to change the color of the shapes every DELAY ms
    class TimerListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        // init a new PolygonComponent
        PolygonComponent pc = new PolygonComponent();
        // set the colors to random colors
        square.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        pentagon.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));

        square.translate(rand.nextInt(150) - 75, rand.nextInt(150) - 75);
        pentagon.translate(rand.nextInt(150) - 75, rand.nextInt(150) - 75);

        // Add the polygons
        pc.add(square);
        pc.add(pentagon);
        // Add the Polygon Component to the frame and set visible
        frame.add(pc);
        frame.setVisible(true);
      }
    }

    // Timer to change the color regularly
    Timer t = new Timer(DELAY, new TimerListener());
    t.start();// Start the timer

    // Add the Polygon Component to the Frame
    frame.add(polyCom);

    // Set the frame visible
    frame.setVisible(true);
  }

  public static void colorize() {
    Random rand = new Random();

  }

}
