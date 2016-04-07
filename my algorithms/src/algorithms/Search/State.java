package algorithms.Search;

import java.io.Serializable;

/**
* <h1>State</h1>
* 
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
* 
* * <h1>State</h1>
* implements Comparable working on state
* this class will help us solved any problem with any kind of positioning
* , string is general enough  to represent any kind of variable
* cost will define who much cost to come to this state
* cameFrom will define from witch state this state came from 
*/


public class State implements Comparable<State> , Serializable{
    private String state;    // the state represented by a string
    private double cost;     // cost to reach this state
    private State cameFrom;  // the state we came from to this state

    public State(String state){    // CTOR    
        this.state = state;
    }
    
    public State(String state,double cost1,State camefrome1){    // CTOR    
        this.state = state;
        this.cost=cost1;
        this.cameFrom=camefrome1;
    }


    @Override
    public boolean equals(Object obj){ // we override Object's equals method and using string's equals method
        return state.equals(((State)obj).state);
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public State getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State cameFrom) {
		this.cameFrom = cameFrom;
	}
	
	//The PriorityQueue will use this method and will manage the Priority based on this method
	public int compareTo(State s) 

	{
		if(this.cost < s.cost)
			return -1;
		if (this.cost > s.cost)
			return 1;
		return 0;
	}
    
	/**
	 * 
	 * @return an array of integers that represent the position of the state
	 */
	@SuppressWarnings("static-access")
	public Integer[] toInt(){
    
		String[] s1 = new String [3];
		//String pos=new String(state);
		s1=state.split(",");
		
		Integer [] xyz=new Integer[3];
		for (int i = 0; i < xyz.length; i++) {
			xyz[i]=xyz[i].valueOf(s1[i]);
		}
		return xyz;
		
	}
	
	
	
}

