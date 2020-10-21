# Lab 8: Thought Questions

 1. Run several simulations of both types of queues. Which queue strategy seems to process all the customers fastest? (_Note: For your simulations to be meaningful, they should represent a realistic situation. Customers should not overwhelm the tellers, and tellers should not be completely idle. When answering this thought question, give and justify the parameters you chose for your simulation._)
  * |----Single----|-----Multi----|
    |C: 20  SP: 5  |C: 20  SP: 5  |
    |C: 20  SP: 4  |C: 20  SP: 4  |
    |C: 20  SP: 3  |C: 20  SP: 3  |
    |C: 20  SP: 3  |C: 20  SP: 3  | -Different Seed
    |C: 20  SP: 2  |C: 20  SP: 2  |
    |C: 50  SP: 5  |C: 50  SP: 5  |
    |C: 50  SP: 5  |C: 50  SP: 5  | -Different Seed
    |C: 50  SP: 3  |C: 50  SP: 3  |
    |C: 100 SP: 20 |C: 100 SP: 20 |
    |C: 100 SP: 20 |C: 100 SP: 20 | -Different max event start time
    |C: 100 SP: 20 |C: 100 SP: 20 | -Different max event start time
    I have kept the Min&Max service times constant for each simulation as using the same seed for simulations of single vs multi queues they do not affect the relative outcome of time.
    Changing max event start times likewise does not change the end result of which queue is faster, but changes the run-time as we increase/decrease the max arrival time directly.
    The parameter I chose differ from at least one other case in at most a single parameter except the case of 100/20. This allowed me to control and see the changes a change in each parameter cause to the runtime. The 100/20 case was a check case for 20/4 case, in which I had suspected the ratio might be playing a role in getting equal runtimes for both, which was proven to be incorrect when simulated 100/20 case where the ratio was kept the same.
    The end result was that _SINGLE QUEUE_ gave the fastest results overall. In some cases runtimes were equal where the number of customers were low hence the effective difference in service speed were too small to be observed in time steps.

 2. Is their a difference between the average wait time for customers between the two techniques? (_A Customer's wait time is the difference between the time that a Customer appears in the simulation and the time that the Customer first begins receiving service from a teller. The wait time should not include the time that a Customer is actively receiving service._) Give the intuition for your results.
  * Single queue has shorter wait time for customers. As the customer who arrives first will get service when any one of the tellers are empty. However, for multi-queue, the customer who arrives will go to the shortest queue and depending on the service times of the customers before them, someone who arrived later might get service earlier hence a longer wait time for the customer who arrived earlier.

 3. Suppose you simulated the ability to jump between lines in a multiple line simulation. When a line has two or more customers than another line, customers move from the end of one line to another until the lines are fairly even. You see this behavior frequently at grocery stores. Does this change the type of underlying structure you use to keep customers in line?
  * We cannot implement this mechanic with queue data structures as we can only remove/manipulate the item on the top and not in the middle or at the end. we would need to change our data structure to something with more flexible manipulation like a vector.
  
 4. Suppose lines were dedicated to serving customers of varying lengths of service times. Would this improve the average wait time for a customer? If yes, how? If not, why not?
  * It would be a case by case change, affecting each customer differently based on their service times. The ones who has longer service times would end up waiting longer as every other customer in the queue they're in also has long service times, but the ones who has shorter service times would wait shorter as every other customer in their line also has short service times. But the average would be improved as longer wait times would ideally only be affecting customers in a single queue hence decreasing their impact on general customer population's wait time. If they were to be sent throughout, they would add to the waiting time of multiple queue's worth of customers.
