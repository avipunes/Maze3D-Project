package algorithms.Search;

import java.io.Serializable;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
* 
* <h1>BFS</h1>
* extends CommonSearcher and implement its own way of the search method 
* 
* BFS checking of specific state witch possible move has the best path to goal state  
* after all checks it will return the best path from start state to goal state
*/


import java.util.ArrayList;
import java.util.PriorityQueue;


public class BFS extends CommonSearcher implements Serializable{
/**
 * @param s - any kind of searchable 
 * @return a solution for the s using BFS algorithm (Best First Search)
 */
	@Override
public Solution search(Searchable s) {
		  addToOpenList(s.getStartState());//adding to the open list (PriorityQueue) the start state 
		  PriorityQueue <State> closelist = new PriorityQueue<State>(); //PriorityQueue for all the members that has worked on
		  
		  while(!openList.isEmpty()) //as long as the open list is not empty 
		  {
		    State  n=popOpenList();	//pop the first member on the open list 

		    closelist.add(n);
		    

		    if(n.equals(s.getGoalState())) // if we reach the goal state 
		    {
		  
		      return super.backTrace(n, s.getStartState());//return the backTrace of the CommonSearcher class
		    }

		    ArrayList<State> successors=s.getAllPossibleStates(n); 
		    for(State state : successors) //for each possible state from current state
		    {
		    	
		    	double pathPrice = state.getCost()+s.CostToState(n, state);//how much its cost to come to state + the define cost for the specific Searchable
		    	
		      if(!closelist.contains(state) && ! openListContains(state))//not on closeList and not on openList
		      {
		        state.setCameFrom(n);	//set for state that he came from n
		        addToOpenList(state);	// add state to open list
		        state.setCost(n.getCost()+s.CostToState(n, state));	//set the cost fot state (how much its cost to come to state + the define cost for the specific Searchable)
		      } 
			   else if (pathPrice<state.getCost())	//if there is a better path
			   {	  
					if (!openListContains(state)) //if state is not on openList
					{
						closelist.remove(state);	//
						addToOpenList(state);		//move from openList to closeList
					}
				   	  else 
			    	  {
				  		openList.remove(state);	//
				  		state.setCost(n.getCost()+s.CostToState(n, state));	//
						state.setCameFrom(n); 	//
						addToOpenList(state);	// remove state from openList, update his cost and where it came from, and then return it to the openList after the update
			    	  }
		    	  } 
		    
			   }	
		    }
		  
		  System.out.println("There problem cant be solved");	//if the openList is empty and it not got to the goal state, then the problen cant be solved
		return null;
	}

	




}




	
