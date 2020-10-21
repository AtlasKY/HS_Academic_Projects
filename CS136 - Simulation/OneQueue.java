import structure5.*;

public class OneQueue extends BusinessSimulation {

  //Constructor
  //Post: init super instance
  public OneQueue(int numCustomers, int numServicePoints, int maxEventStart, int seed){
    super(numCustomers, numServicePoints, maxEventStart, seed);
    System.out.println("OneQ initialised!");
    System.out.println(toString());
  }

  //Pre: The instances used has been initialised
  //Post: Performs one time step of the simulation and returns true if simulation ends
  public boolean step(){
    while(!eventQueue.isEmpty() && emptyQ() != -1 && eventQueue.getFirst().getEventTime() <= super.time){
      servicePoints.get(emptyQ()).add(eventQueue.remove());
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
  //Post: returns the indx of the empty queue is there's one, -1 if there's none
  private int emptyQ(){
    for (int i = 0; i < servicePoints.size(); i++) {
      if(servicePoints.get(i).isEmpty()){
        return i;
      }
    }
    return -1;
  }

}
