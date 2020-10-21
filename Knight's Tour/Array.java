/*
 * ATLAS KAAN YILMAZ
 * 
 * KNIGHT'S TOUR
 * 
 * 17 DEC 2018
 * 
 */ 

//CLASS FOR ARRAY HANDLING METHODS
public class Array
{  
  //EMPTY CONSTRUCTOR
  public Array()
  {}
  
  //COPY A 2-DIMENSIONAL ARRAY
  public int[][] intArrCopy(int arr[][])
  {
    //create an empty copy array with same size
    int[][] copy = new int[arr.length][arr.length];
    
    //double for loop to assign every element from arr to copy
    for(int i = 0; i < arr.length; i++){
      
      for(int j = 0; j < arr[i].length; j++){
        copy[i][j] = arr[i][j];
      }
    }
          
    //return the copied array
    return copy;
  }
  
  //PRINT AN 1-D ARRAY
  public void printArr(int arr[])
  {
    System.out.println(arr.toString()); //print the array
  }
  
  //PRINT A 2-D BOARD ARRAY
  public void printBoard(int brd[][])
  {
    System.out.println(); //empty line
    
    //loop the column
    for(int i = 0; i < brd.length; i++){
      
      //loop the row
      for(int j = 0; j < brd[i].length; j++){
      
        System.out.print(brd[j][i] + " "); //print the element followed by a space
        
      }
      System.out.println(); //end the line
    }
    System.out.println(); //empty line
  }
  
  
  //RETURN THE INDEX OF THE SMALLEST NUMBER IN A 1D ARRAY
  public int smallestIndex(int arr[])
  {
    int index = 0; //initial index 0
    int smallest = arr[0]; //initial smallest is the first number
    
    //loop the numbers, if smaller than current smallest, update number and index
    for(int n = 0; n < arr.length; n++){
      if (arr[n] < smallest) {
        smallest = arr[n];
        index = n;
      }
    }
    
    //return index
    return index;
  }
}