/*
 * ATLAS KAAN YILMAZ
 * 
 * KNIGHT'S TOUR
 * 
 * 17 DEC 2018
 * 
 * algorithm help from: https://www.geeksforgeeks.org/warnsdorffs-algorithm-knights-tour-problem/
 * 
 */ 
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

public class KnightTour
{
  
  Board board; //declare Board
  
  Array arr; //declare array class
  
  Random rand; //declare random class
  
  // Arrays for Knight Moves
  int[] moveX = {1,1,2,2,-1,-1,-2,-2}; 
  int[] moveY = {2,-2,1,-1,2,-2,1,-1};
 
  
  int bSize = 8; //board size
  int numSqr = 64; // number of squares
  
  // Arrays to store solution, x and y numbers of the solution squares
  double[] solX;
  double[] solY;
  
  boolean toured = false; // boolean to check toured or not
  
  //CONSTRUCTOR
  public KnightTour()
  {
    // Instantiate declared fields
    rand = new Random();
    
    arr = new Array();
    
    board = new Board();
  }
  
  //PUBLIC TOUR KNIGHT METHOD
  public void tourK(int x, int y)
  {

    int moveN = 1; //define the move number
    solX = new double[numSqr]; //solution arrays are instantiates to hold 64 numbers
    solY = new double[numSqr];
    
    int[][] brd = new int[bSize][bSize]; // 2-dimensional board arrays 
    
    toured = tourKnight(x, y, moveN, brd); //calls the recursive method with boolean return and assigns to toured.
  }
  
  //RECURSIVE PRIVATE KNIGHT TOUR METHOD
  // x and y number of the new square, move number and the board array is passed.
  private boolean tourKnight(int x, int y, int moveN, int b[][])
  {
    
    b[x][y] = moveN; //sets the current position the current move number
    
    solX[moveN - 1] = x; // store the x and y positions in the solution array
    solY[moveN - 1] = y;
    
    //if last move, terminate true
    if(moveN == numSqr) 
    {
      return true;
    }
    else {
      
      int[] posMoves = new int[8]; //create empty array of possible moves, this will hold the possiblemoves that can be made on a given tile
      
      int xNew, yNew;//declare int
      int i = 0; //declare an index int
      
      //for loop to cycle the moves possible from the current position
      for(i = 0; i < 8; i++)
      {
        //the new x and y after the ith move has been made
        xNew = x + moveX[i];
        yNew = y + moveY[i];
        
        //store the number of moves that can be made at the new point, call checkMoves method with an int return
        posMoves[i] = checkMoves(xNew, yNew, b, moveN);
        
      }
      
      //select the smallest of the possibleMoves and store the index of the move
      i = arr.smallestIndex(posMoves); 
      

      if(!available(x + moveX[i], y + moveY[i], b) )
      {
        return false;
      }
      
      //call recursive until fail to complete of complete
      return tourKnight(x + moveX[i], y + moveY[i], moveN + 1, b);
      
    }
      
  }
  
  //private boolean method to check available move
  private boolean available(int x, int y, int b[][])
  {
    //true if the point is within the range of the board
    boolean lm = (x < bSize && y < bSize) && ( x >= 0 && y >= 0 );
    
    
    if(lm)
    {
      //true if the point has not been visited
      return b[x][y] == 0;
    } //else false
    else
      return false;
  }
  
  //PRIVATE INT RETURN METHOD TO CHECK THE NUMBER OF MOVES AVAILABLE AT THE GIVEN POINT
  private int checkMoves(int x, int y, int b[][], int moveN)
  {
    //check availability of the point
    if(available(x,y,b)){
      
      int deg = 0; //degree counter
      
      //cycle all the moves a knight can make at the point
      for(int i = 0; i < 8; i++)
      {
        //create new x and y after the given move is made
        int xNew = x + moveX[i];
        int yNew = y + moveY[i];
        
        //check available, if available increase degree
        if(available(xNew, yNew, b))
          deg++;
      }
      
      //if it is not the last move and the degree is zero, return 9 to stop errenous chosing of the tile
      if(moveN != numSqr - 1 && deg == 0)
        return 9;
      //return degree
      return deg;
    }
    
    //if not available return 9 to prevent picking and outOfBoundException
    return 9;
  }
  
  //PUBLIC DRAW METHOD WITH PARAM: graphic, selected x and y, and current step count
  public void draw(Graphics g, int selecX, int selecY, int stepCount)
  {
    Graphics2D g2 = (Graphics2D) g;
    
    //call board draw method from board class
    board.draw(g2, selecX, selecY);
    
    //if Knight's Tour is completed, call the moveknight method
    if(toured)
    {
      moveKnight(g2, stepCount); // moves the knight pointer 
    }
    
  }
  
  // KNIGHT MOVER METHOD TO MOVE THE KNIGHT TO THE GIVEN STEP
  private void moveKnight(Graphics2D g2, int stepCount)
  {
    //offset for drawing GUI
    double offset = board.OFFSET + board.SQR_SIZE/2;
    
    //Path to link the moves
    Path2D.Double path = new Path2D.Double();
    
    //single access ellipse size double
    double ellSize = board.SQR_SIZE * 2 / 3;
    
    //move the path pointer to the inital position
    path.moveTo(offset + board.SQR_SIZE * solX[0], offset + board.SQR_SIZE * solY[0]);
    
    //if the tour is completely drawn, paint a red square at the ending point, else paint a yellow square at the current point
    if(stepCount == numSqr)
    {
      Rectangle2D.Double endS = new Rectangle2D.Double(board.OFFSET + board.SQR_SIZE * solX[stepCount - 1], 
                                                       board.OFFSET + board.SQR_SIZE * solY[stepCount - 1], 
                                                       board.SQR_SIZE, board.SQR_SIZE);
      g2.setColor(Color.RED);
      g2.fill(endS);
    }
    else {
      Rectangle2D.Double curS = new Rectangle2D.Double(board.OFFSET + board.SQR_SIZE * solX[stepCount - 1], 
                                                       board.OFFSET + board.SQR_SIZE * solY[stepCount - 1], 
                                                       board.SQR_SIZE, board.SQR_SIZE);
      g2.setColor(Color.YELLOW);
      g2.fill(curS);
    }
    

    //Loop to add lines to the path and number to the square
    for(int i = 0; i < stepCount; i ++){
      
      path.lineTo( offset + board.SQR_SIZE * solX[i], offset + board.SQR_SIZE * solY[i]); // add a new point to the path
      
      //Double values for top left corner of move number ellipses
      double xC = board.OFFSET + ellSize/6 + board.SQR_SIZE * solX[i]; 
      double yC = board.OFFSET + ellSize/6 + board.SQR_SIZE * solY[i];
        
      //ellipse to paint move number on
      Ellipse2D.Double ell = new Ellipse2D.Double(xC, yC, ellSize, ellSize);
      
      //BLUE color for ellipses
      g2.setColor(Color.BLUE);
      g2.fill(ell);
      
      //Print the number of current move number on the ellipse in WHITE
      g2.setColor(Color.WHITE);
      g2.setFont(new Font("Comic Sans", Font.BOLD, (int) ellSize *2/3));
      g2.drawString(Integer.toString(i + 1), (int) (xC + 5), (int) ( yC + 40) );
      
    }
    
    //DRAW the path in BLUE
    g2.setColor(Color.BLUE);
    g2.draw(path);
    
  }
      
}