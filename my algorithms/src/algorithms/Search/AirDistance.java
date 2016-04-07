package algorithms.Search;

import java.io.Serializable;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
*  <h1>AirDistance</h1>
* extends CommonHeuristic and
* calculating the air distance between to states 
* 
*/


public class AirDistance extends CommonHeuristic implements Serializable{

	/**
	 *@param state1 
	 *@param state2
	 *@return the air distance
	 */
	@Override
	public double heuristicCostEstimate(State state1, State state2) {
		
		Integer[] position = new Integer[3];
		position=state1.toInt();
		
		Integer[] position2 = new Integer[3];
		position2=state2.toInt();
		
		 double mDistance=Math.sqrt((Math.pow((position[0]-position2[0]), 2))+Math.pow((position[1]-position2[1]), 2)+Math.pow((position[2]-position2[2]), 2));
		
		 return mDistance;
	
	}

}