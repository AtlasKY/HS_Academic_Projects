
import java.util.Scanner;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Set;

public class GradesPlus
{
  
  public static HashSet<Integer> iDs;
  
  public static void main(String args[])
  {
    
    TreeMap<Student, String> map = new TreeMap<Student, String>();
    
    iDs = new HashSet<Integer>();
    
    Scanner in = new Scanner(System.in);
    
    boolean run = true;
    
    while(run)
    {
      System.out.println("Enter a to add student, r to remove, m to modify grades, p to print all grades, and q to quit.");
      
      String s = in.nextLine();
      
      switch(s) {
        case "a":
          add(in, map);
          break;
        case "r":
          remove(in, map);
          break;
        case "m":
          modify(in, map);
          break;
        case "p":
          print(map);
          break;
        case "q":
          run = false;
          break;
        default:
          System.out.println("Wrong input! Try again!");
          break;
      }
    }
    
  }
  
  public static void add(Scanner in, TreeMap m)
  {
    System.out.println("Enter the first name of the student you want to add.");
    String n = in.nextLine();
    System.out.println("Enter the last name of the student you want to add.");
    String l = in.nextLine();
    
    System.out.println("Enter " + n + "'s grade.");
    String g = in.nextLine();
    
    m.put(new Student(n, l, iDs), g);
    
    
  }
  
  public static void remove(Scanner in, TreeMap m)
  {
    print(m);
    System.out.println("Enter the ID of the student you want to remove.");
    int id = in.nextInt();
    in.nextLine();
    
    Student std = new Student();
    
    for(Student s : (Set<Student>) m.keySet())
    {
      if(s.getID() == id){ std = s; break; }
    }
    
    m.remove(std);

  }
  
  public static void modify(Scanner in, TreeMap m)
  {
    print(m);
    System.out.println("Enter the ID of the student you want to modify.");
    int id = in.nextInt();
    in.nextLine();
    System.out.println("Enter " + id + "'s new grade.");
    String g = in.nextLine();
    
    Student std = new Student();
    
    for(Student s : (Set<Student>) m.keySet())
    {
      if(s.getID() == id){ std = s; break; }
    }
    
    m.put(std, g);
    
  }
  
  public static void print(TreeMap m)
  {
    for(Student s : (Set<Student>) m.keySet())
    {
      System.out.println(s.getName() + " " + s.getLastName() + "(ID: " + s.getID() + "): " + m.get(s));
    }
  }
}