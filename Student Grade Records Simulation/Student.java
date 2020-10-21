
import java.util.HashSet;
import java.util.Random;

public class Student implements Comparable<Student>
{
  
  String name;
  String lastName;
  int iD;
  Random rand;
  
  public Student()
  {
    name = "";
    lastName = "";
    iD = 0;
  }
  
  public Student(String n, String l, HashSet s)
  {
    rand = new Random();
    name = n;
    lastName = l;
    
    do{
    iD = rand.nextInt(1000);
    } while(s.contains(iD));
    
    s.add((Integer)iD);
  }
  
  public int getID(){ return iD; }
  public String getName(){ return name; }
  public String getLastName(){ return lastName; }
  
  public void print()
  {
    System.out.println("ID: "+ iD + " Name: " + name + " " + lastName);
  }
  
  public int compareTo(Student s)
  {
    return name.compareTo(s.getName());
  }
  
}
