
import java.util.Random;
import structure5.*;

/**
 * An abstract class for different implementations of BusinessSimulation, creates the customers, service points
 * and provides an abstract class for the simulation step
 */
public abstract class BusinessSimulation {

	/* sequence of customers, prioritized by randomly-generated event time */
	protected VectorHeap<Customer> eventQueue;

	/* series of service points where customers queue and are served */
	protected Vector<QueueVector<Customer>> servicePoints;

	/* current time step in the simulation */
	protected int time;

	/* seed for Random() so that simulation is repeatable */
	protected int seed;

	/* Used to bound the range of service times that Customers require */
	public static final int MIN_SERVICE_TIME = 1;
	public static final int MAX_SERVICE_TIME = 10;

 //Used to track the number of customers served
	protected int customersServed = 0;

	/**
	 * Creates a BusinessSimulation.
	 * @post the step() function may be called.
	 *
	 * @param numCustomers number of random customers that appear in the simulation
	 * @param numSerivicePoints number of tellers in this simulation
	 * @param maxEventStart latest timeStep that a Customer may appear in the simulation
	 * @param seed used to seed a Random() so that simulation is repeatable.
	 */
	public BusinessSimulation(int numCustomers, int numServicePoints, int maxEventStart, int seed) {

		servicePoints = new Vector<QueueVector<Customer>>();
		this.seed = seed;
		time = 0;
		eventQueue = generateCustomerSequence(numCustomers, maxEventStart, seed);

		for (int i = 0; i < numServicePoints; i++) {
			servicePoints.add(new QueueVector<Customer>());
		}
	}

	/**
	 * Generates a sequence of Customer objects, stored in a PriorityQueue.
	 * Customer priority is determined by arrival time
	 * @param numCustomers number of customers to simulate
	 * @param maxEventStart maximum timeStep that a customer arrives
	 *      in @eventQueue
	 * @param seed use Random(seed) to make customer sequence repeatable
	 * @pre
	 * @post
	 * @return A PriorityQueue that represents Customer arrivals,
	 *         ordered by Customer arrival time
	 */
	public static VectorHeap<Customer> generateCustomerSequence(int numCustomers, int maxEventStart, int seed) {
		VectorHeap<Customer> que = new VectorHeap<Customer>();
		Random r = new Random(seed);

		while(numCustomers > 0) {
			que.add(new Customer(r.nextInt(maxEventStart + 1), r.nextInt(MAX_SERVICE_TIME-MIN_SERVICE_TIME) + MIN_SERVICE_TIME));
			numCustomers--;
		}
		return que;
	}

	/**
	 * Advances 1 time step through the simulation.
	 *
	 * @post the simulation has advanced 1 time step
	 * @return true if the simulation is over, false otherwise
	 */
	abstract public boolean step();

	//Pre: eventQueue and servicePoints are initialised
	//Post: returns true if all the customers has been processed, i.e. all queue are empty
	protected boolean checkEnd(){
    if(!eventQueue.isEmpty()){
      return false;
    }
    else{
      boolean temp = true;
      for (QueueVector<Customer> q : servicePoints) {
        if(!q.isEmpty()){
          temp = false;
          break;
        }
      }
      return temp;
    }
  }

	//Post: return time
	public int getTime(){ return time;}

	//Post: return Customers Served
	public int getCustomersServed(){ return customersServed;}

	//Post: Retun number of servicePoints
	public int getNumServicePoints(){ return servicePoints.size();}

	/**
	 * @return a string representation of the simulation
	 */
	public String toString() {
		// TODO: modify if needed.
		String str = "Time: " + time + " Cutomers Served: " + customersServed + "\n";
		str = str + "Event Queue: ";
		if (eventQueue != null) {
			str = str + eventQueue.toString();
		}
		str = str + "\n";

		if (servicePoints != null)  {
			for (Queue<Customer> sp : servicePoints) {
				str = str + "---Service Point: " + sp.toString() + "\n";
			}
		}

		return str;
	}
}
