package algorithms.Search;

import java.io.Serializable;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
* 
*<h1>CommonSearchable</h1>
*implements Searchable interface, any kind of Searchable will extends this class and will
*implements all this abstract methods
*
* 
*/


import java.util.ArrayList;



public  abstract class CommonSearchable implements Searchable ,Serializable {

	@Override
	public abstract State getStartState();
		

	@Override
	public abstract State getGoalState();

	@Override
	public abstract ArrayList<State> getAllPossibleStates(State s);
	
	public abstract int CostToState(State a, State b);
	
	
}
