package algorithms.Search;

import java.io.Serializable;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
*
* <h1>ManhattanDistance</h1>
* calculating the distance between to states using manhattan distance algo
* (distance between two points - (x1-x2)+(y1-y2)+(z1-z2) //absolute value
* 
* 
*/

public class ManhattanDistance extends CommonHeuristic implements Serializable{

	/**
	 * @param state1
	 * @param state2
	 * 
	 * @return between two states (x1-x2)+(y1-y2)+(z1-z2) //absolute value
	 */
	@Override
	public double heuristicCostEstimate(State state1, State state2) {
		
		Integer[] position = new Integer[3];
		position=state1.toInt();
		
		Integer[] position2 = new Integer[3];
		position2=state2.toInt();
		
		double mDistance=0;
		
		for (int i = 0; i < position2.length; i++) {
			mDistance=Math.abs(position[i]-position2[i]);
		}
		
	
		
		return mDistance;
	}

}
