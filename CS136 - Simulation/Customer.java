import structure5.*;

/**
 * A class that stores event times and service times of an individual Customer, ad provides a method to compare one Customer to another
 */
public class Customer implements Comparable<Customer> {

	private int eventTime;
	private int serviceTime;

	/**
	 * Creates a Customer that arrives at time step @eventTime and
	 * requires @serviceTime time steps to be satisfied after
	 * arriving at a service point.
	 * @param eventTime time step that this Customer appears in the
	 *        simulation
	 * @param serviceTime number of time steps required to service this
	 *        Customer.
	 */
	public Customer(int eTime, int sTime) {
		eventTime = eTime;
		serviceTime = sTime;
	}


  //Post: decrements the service time left for the customer by one
	//      and returns true if customer is done with their service
	public boolean workDone(){
		serviceTime--;
		return serviceTime == 0;
	}

	//Post: returns EventTime integer
	public int getEventTime(){ return eventTime;}
	//Post: returns serviceTime integer
	public int getServiceTime() { return serviceTime;}
	/**
	 * Compares Customers by arrival time
	 * @param other Customer to compare against this.
	 */
	public int compareTo(Customer other) {
		return eventTime - other.getEventTime();
	}
	//Post returns a string representation of this instance of the object
	public String toString() {
		return "\nArrival Time: " + eventTime + " Service Time: " + serviceTime;
	}
}
