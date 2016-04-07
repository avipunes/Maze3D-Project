package algorithms.Search;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
* * <h1>Heuristic</h1>
* interface
* CommonHeuristic will implement this interface and any kind of Heuristic will
* extend CommonHeuristic
* 
*/


public interface Heuristic {
	public double heuristicCostEstimate(State state1, State state2);
}
