package algorithms.Search;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
* 
* <h1>Searchable</h1>
* 
* defining the functionality of any kind of Searchable
* the class CommonSearchable will implements this interface, and any kind of Searchable
* will extends CommonSearchable and will implements his own functionality
*/


import java.util.ArrayList;


public interface Searchable {
	State getStartState();
	State getGoalState();
	ArrayList<State> getAllPossibleStates(State s);
	int CostToState(State a, State b);
	void setpath(State s);
	public void printMaze();
}
