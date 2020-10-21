import structure5.*;
import java.util.Scanner;
import java.util.Iterator;

//CLASS: A class that schedules exam slots given a list of students with courses
//Extensions: 1st and 2nd extensions have been implemented,
//            i.e. Alphabetical slotting and student by student printing
public class ExamSchedule {

  Graph<String, Integer> scheduleMap; //Graph of classes and conflicts
  OrderedVector<Student> students; //a vector to store the students aplhabetically
  Scanner sc; //A scanner object
  Vector<OrderedVector<String>> schedule; //A vector of ordered vectors to store the normal schedule
  Vector<OrderedVector<String>> scheduleAlp; //"""" to store alphabetical schedule
  OrderedVector<String> courses; //A vector to store courses in alphabetical order

  //Constructor: Initialises the instance variables
  public ExamSchedule(){
    scheduleMap = new GraphListUndirected<String, Integer>();
    students = new OrderedVector<Student>();
    schedule = new Vector<OrderedVector<String>>();
    scheduleAlp = new Vector<OrderedVector<String>>();
    courses = new OrderedVector<String>();
  }

  public static void main(String[] args) {
    ExamSchedule ex = new ExamSchedule();
    ex.populate();
    ex.scheduleAlpha();
    ex.printStudents();
    System.out.println(ex.toString(ex.getSchAlp()));
    ex.schedule();
    ex.printStudents();
    System.out.println(ex.toString());
  }

  //Post: Reads the given file of students with their classes,
  //      creates new students objects with the information,
  //      and populates the graph with courses as vertices and conflicts as edges
  private void populate(){
    sc = new Scanner(System.in);
    String[] label = new String[4];
    int lc = 0;
    String name;
    Student st = new Student();

    while(sc.hasNext()){
//      System.out.print(lc + " ");
      if(lc == 0) {
        st = new Student();
        name = sc.nextLine();
        st.setName(name);
//        System.out.println(name);
      }
      else {
        label[lc-1] = sc.nextLine();
//        System.out.println(label[lc-1]);
        st.addClass(label[lc-1]);
        if(!scheduleMap.contains(label[lc-1])){
          scheduleMap.add(label[lc-1]);
          courses.add(label[lc-1]);
        }
      }
      //If a student has been processed and stored
      if(lc == 4){
        students.add(st);
        for (int i = 0; i < 3; i++) {
          for (int j = i+1; j < 4 ; j++ ) {
            if(!scheduleMap.containsEdge(label[i], label[j])){
              scheduleMap.addEdge(label[i], label[j], 1);
//              System.out.println("Added Edge "+ label[i] +" to " + label[j]);
            }
            else{
              int w = scheduleMap.getEdge(label[i], label[j]).label();
              scheduleMap.getEdge(label[i], label[j]).setLabel(w+1);
//            System.out.println("Added Weight on Edge "+ label[i] +" to " + label[j]);
            }
          }
        }
      }
      lc = (lc+1) % 5;
    }
  }

  //Pre: schedule object is initialised and graph is populated
  //Post: Iterates over the graph, taking the first vertex as a reference
  //      and putting all the classes that does not conflict with the class
  //      into the same time slot and further checks for any conflict within the time slot,
  //      when no more classes can be added, removes the added courses from the graph,
  //      and loops until there are no more vertices in the graph
  private void schedule(){
    Iterator it;
    int i = 0;
    while(!scheduleMap.isEmpty()){
      System.out.println();
      it = scheduleMap.iterator();
      String v1 = (String) it.next();
//      System.out.println("Vertex: " + v1 + " Slotted " + i);
      schedule.add(new OrderedVector<String>());
      schedule.get(i).add(v1);
      while(it.hasNext()){
        String v2 = (String) it.next();
        if(conCheck(i, v2, schedule)){
          schedule.get(i).add(v2);
        }
      }
      for (String v: schedule.get(i)) {
        scheduleMap.remove(v);
        studentSlotter(v,i);
      }
      i++;
    }
  }

  //Pre: ScheduleMap and courses instances are populated and scheduleAlp is initialised
  //Post: Has the same procedure as schedule() but instead of iterating over the graph,
  //      iterates over the courses vector which lists courses in alphabetical order
  private void scheduleAlpha(){
    Iterator it = courses.iterator();
    int i = 0;
    while(!courses.isEmpty()){
      it = courses.iterator();
      if(it.hasNext()){
        String v1 = (String) it.next();
        scheduleAlp.add(new OrderedVector<String>());
        scheduleAlp.get(i).add(v1);
        if(it.hasNext()){
          String v2 = (String) it.next();
          if(conCheck(i, v2, scheduleAlp)){
            System.out.println(v1 + " null p test " + v2);
            scheduleAlp.get(i).add(v2);
          }
        }
        for (String v: scheduleAlp.get(i)) {
          courses.remove(v);
          studentSlotter(v,i);
        }
        i++;
      }
    }
  }

  //Post: returns true if given vertex does not conflict with any of the classes present in the ith exam slot
  private boolean conCheck(int i, String v, Vector<OrderedVector<String>> sch){
    boolean b = true;
    for (String s : sch.get(i)) {
      if(scheduleMap.containsEdge(s, v)){
        b = false;
        return b;
      }
    }
    return b;
  }

  //Pre: students object is initialised and populated
  //Post: cycles through all the student objects in students vector and if that student is taking class c,
  //      updates the exam slot information with i
  private void studentSlotter(String c, int i){
    for (Student s : students) {
      if(s.hasClass(c)){
        s.setSlot(c,i);
      }
    }
  }

  //Post: Prints all the students with their names and courses and exam slots
  public void printStudents(){
    for (Student s : students) {
      System.out.println(s.toString());
    }
  }

  //Post: returns schedule
  public Vector<OrderedVector<String>> getSch(){ return schedule;}
  //Post: returns alphabetical schedule
  public Vector<OrderedVector<String>> getSchAlp(){ return scheduleAlp;}

  //No parameter toString method, returns toString with schedule as parameter
  public String toString(){ return toString(schedule);}

  //Post: returns a string representation of the given vector of orderedvectors, i.e. Schedule
  public String toString(Vector<OrderedVector<String>> sch){
    String txt = "";
    System.out.println("Schedule Size: " + sch.size());
    for (int i = 0; i < sch.size(); i++) {
      txt = txt + "Exam Slot " + i + ": ";
      for (String s : sch.get(i)) {
        txt = txt + s + " ";
      }
      txt = txt + "\n";
    }
    return txt;
  }

}
