package algorithms.Search;

import java.io.Serializable;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30
* 
*  <h1>Astar</h1>
*  extends CommonSearcher
*  and implements its own way to the search method using Astar algorithm, to solve a problem
*  
*  Astar is checking of a specific state witch possible move will be having the best estimated path and going to that state
*  using strategy pattern
*/


import java.util.ArrayList;
import java.util.HashSet;

public class Astar extends CommonSearcher implements Serializable{

	private Heuristic h; //all kind of Heuristic algorithm
	
	//CTOR
	//strategy pattern, injection the Heuristic that the Astar will use to calculate the estimated best path
	public Astar(Heuristic h) {
		this.h=h;
	}
	
	//implement of Astar algorithm
	/**
	 * @param s any kind of Searchable 
	 * @return solution using A* algorithm
	 */
	
	@Override
	public Solution search(Searchable s) {
		  addToOpenList(s.getStartState()); //adding to the open list (PriorityQueue) the start state 
		  HashSet<State> closedSet=new HashSet<State>(); //creating HashSet working on State
		  State Start = s.getStartState();
		  State Goal = s.getGoalState();

		  State current;
				 
		  while (!openList.isEmpty())	//as long as the open list is not empty 
		  {
			 current=popOpenList();		//pop the first member on the open list 
					
							 
			 if(current.equals(s.getGoalState()))// if we reach the goal state 
			 {
				 return super.backTrace(current, Start);	//return the backTrace of the CommonSearcher class
			 }
			
				 ArrayList<State> successors=s.getAllPossibleStates(current); 
				    for(State state : successors)//for each possible state from current state
				    {
				    	if(closedSet.contains(state))	//if the closeSet contains the state already go to the next state 
				    	{
				    		continue;
				    	}
				    	closedSet.add(state);	//its not on the closeSet already
				    	
				    	//Calculating the heuristic + how much it has cost to arrive to current 
				    	double Gscore =  current.getCost() +s.CostToState(state, current)+ h.heuristicCostEstimate(state, Goal);
				    	if(!openList.contains(state) || Gscore < state.getCost())	//Checking if the possible move is not on the open list Or there is a better path 
				    	{
				    		state.setCameFrom(current);	//set that this possible move came from the current state 
				    		state.setCost(Gscore);		//and set the cost to the Gscore (heuristic+how much cost so far)
				    	}
				    	if(!openList.contains(state))	//Checking if the open list is not contains this possible move
							{
								openList.add(state);	//if not add it
							}
								
				    }
					    
		  }
		 System.out.println("The problem cant be solved");	//if the list got empty then the problem is unsolvable 
		 return null;
				 
	
		 }
}

