import structure5.*;

//CLASS: A class that holds the information of individual students, i.e. their name and classes they're taking
public class Student implements Comparable<Student> {

  private String name; //Name of the student
  private Vector<Association<String, Integer>> classes; //A vector of assosiactions that stores the name of the class
                                                        //associated with the time slot of the class

  //CONSTRUCTOR: initialises an empty name and a vector of length 4, as theres only 4 classes for every student
  public Student(){
    name = "";
    classes = new Vector<Association<String, Integer>>(4);
  }

  //Post: updates the name with given String n
  public void setName(String n){ this.name = n;}

  //Post: adds a String class to the vector of classes with no set time slot that is -1
  public void addClass(String c){
    classes.add(new Association<String, Integer>(c, -1));
  }

  //Post: Updates the slot infromation of a class c to time slot "slot"
  public void setSlot(String c, int slot){
    classes.get(classes.indexOf(new Association<String, Integer>(c, 0))).setValue(slot);
  }

  //Post: returns true if this student is taking the class c
  public boolean hasClass(String c){
    return classes.contains(new Association<String, Integer>(c, 0));
  }

  //Post: returns the name of the student
  public String getName(){ return name;}

  //Post: comapres two student objects by the alphabetical ordering of their names
  public int compareTo(Student o){ return name.compareTo(o.getName());}

  //Post: returns a string representation of this Student object
  public String toString(){
    String txt = "";
    txt = this.name + "\n";
    for (Association<String,Integer> a : classes) {
      txt = txt + a.getKey() + ": Exam Slot " + a.getValue()+ "\n";
    }
    return txt;
  }
}
