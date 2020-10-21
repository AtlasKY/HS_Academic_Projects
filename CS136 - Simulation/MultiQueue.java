import structure5.*;

public class MultiQueue extends BusinessSimulation{

  public MultiQueue(int numCustomers, int numServicePoints,int maxEventStart, int seed){
    super(numCustomers, numServicePoints, maxEventStart, seed);
    System.out.println("MultiQ initialised!");
    System.out.println(toString());
  }

  //Pre: The instances used has been initialised
  //Post: Performs one time step of the simulation and returns true if simulation ends
  public boolean step(){
    while(!eventQueue.isEmpty() && eventQueue.getFirst().getEventTime() == super.time){
      servicePoints.get(shortestQ()).add(eventQueue.remove());
    }
    for(QueueVector<Customer> q : servicePoints) {
      if(!q.isEmpty()){
        if(q.get().workDone()){
          q.remove();
          customersServed++;
        }
      }
    }
    super.time++;
    return checkEnd();
  }

  //Pre: servicePoints has initialised queue isntances stored in it
  //Post: returns the shortest queue for customer placement
  private int shortestQ(){
    int s = servicePoints.get(0).size();
    int index = 0;
    for (int i = 1; i < servicePoints.size(); i++) {
      if(servicePoints.get(i).size() < s){
        s = servicePoints.get(i).size();
        index = i;
      }
    }
    return index;
  }
}
