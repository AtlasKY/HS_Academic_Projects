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
import java.net.*;
import java.io.*;

public class BattleServer
{
  
  Socket socket;
  ServerSocket server;
  PrintWriter out;
  BufferedReader in;
  
  final int PORT = 8901;
  
  boolean stp = false;
  
  public BattleServer()
  {
    //INITIALISE SERVER AND INPUT OUTPUT SETUP
    try {
      server = new ServerSocket(PORT);
      System.out.println("Waiting client..");
      
      socket = server.accept();
      System.out.println("Connected");
      
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      
      out = new PrintWriter(socket.getOutputStream(), true);
    }
    catch(IOException i)
    {
      System.out.println(i);
    }
  }
  
  //CONFIRMS THAT BOTH CLIENT AND SERVER IS DONE WITH SETUP
  public void chkSTP()
  {
    try {
      System.out.println("Waiting for opponent setup...");
      while(in.readLine() != "STP")
      {
        out.print("STP");
      }
      stp = true;
    }
    catch(IOException i)
    {
      System.out.println(i);
    }
  }
  
  //SENDS DATA TO CLIENT
  public void sendData(String d)
  {
    if(stp) { out.print(d); }
  }
  
  //WAITS FOR INPUT AND WHEN READY READS AND RETURNS IT
  public String read()
  {
    String s = "";
    
    try{
      while(!in.ready())
      {
        //do nothing and wait for input
      }
      s = in.readLine();
    }
    catch(IOException i)
    {
      System.out.println(i);
    }
    return s;
    
  }
  
}