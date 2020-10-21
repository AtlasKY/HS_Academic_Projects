

//CLASS: the main method class that runs several experiments with different parameter queues

public class Experiments {

  //Post: Runs several experiments with different parameters
  public static void main(String[] args) {

    OneQueue oQone = new OneQueue(20, 5, 20, 33);
    MultiQueue mQone = new MultiQueue(20, 5, 20, 33);

    OneQueue oQtwo = new OneQueue(50, 5, 20, 33);
    MultiQueue mQtwo = new MultiQueue(50, 5, 20, 33);

    OneQueue oQthr = new OneQueue(50, 3, 20, 33);
    MultiQueue mQthr = new MultiQueue(50, 3, 20, 33);

    OneQueue oQfor = new OneQueue(20, 2, 20, 33);
    MultiQueue mQfor = new MultiQueue(20, 2, 20, 33);

    OneQueue oQfiv = new OneQueue(20, 3, 20, 33);
    MultiQueue mQfiv = new MultiQueue(20, 3, 20, 33);

    OneQueue oQsix = new OneQueue(20, 3, 20, 25);
    MultiQueue mQsix = new MultiQueue(20, 3, 20, 25);

    System.out.println("Single-Queue");
    simulate(oQone);
    System.out.println("Multi-Queue");
    simulate(mQone);
    System.out.println("Single-Queue");
    simulate(oQtwo);
    System.out.println("Multi-Queue");
    simulate(mQtwo);
    System.out.println("Single-Queue");
    simulate(oQthr);
    System.out.println("Multi-Queue");
    simulate(mQthr);
    System.out.println("Single-Queue");
    simulate(oQfor);
    System.out.println("Multi-Queue");
    simulate(mQfor);
    System.out.println("Single-Queue");
    simulate(oQfiv);
    System.out.println("Multi-Queue");
    simulate(mQfiv);
    System.out.println("Single-Queue");
    simulate(oQsix);
    System.out.println("Multi-Queue");
    simulate(mQsix);
  }

  //Pre: the Parameter b has been initialised
  //Post: runs repeatedly calls the step function
  //      and runs the simulation for the given BusinessSimulation instance
  public static void simulate(BusinessSimulation b)
  {
    System.out.println("--------Simulation Start--------------------------");
    while(!b.step()){}
    System.out.println("Time: " + b.getTime() + "\nCustomers: " + b.getCustomersServed() + "\n#Tellers: " + b.getNumServicePoints());
    System.out.println("--------Simulation End----------------------------\n");
  }
}
