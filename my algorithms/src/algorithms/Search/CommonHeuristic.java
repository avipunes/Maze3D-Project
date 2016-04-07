package algorithms.Search;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
* 
* <h1>CommonHeuristic</h1>
* Abstract Class
* CommonHeuristic implements Heuristic interface- any kind of Heuristic will extends
* this class and will implements his own way to calculate the Heuristic 
* 
*/


public abstract class  CommonHeuristic implements Heuristic {

	public abstract double heuristicCostEstimate(State state1,State state2);
}
