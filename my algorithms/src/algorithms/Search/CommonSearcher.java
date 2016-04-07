package algorithms.Search;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
* 
* <h1>CommonSearcher</h1>
* implements Searcher interface - any kind of searcher will implement his own way for search 
* method, but will use the other methods that this class is defining such as: popOpenList,openListContains
* ,backTrace and testSearcher will test the algorithm
* the class will manage the PriorityQueue -openList and how much Nodes for the specific algorithms  
* 
*/


import java.util.ArrayList;
import java.util.PriorityQueue;


public  class  CommonSearcher implements Searcher {
	

	 protected PriorityQueue<State> openList;
	 private int evaluatedNodes;

	 public CommonSearcher() {
	  openList=new PriorityQueue<State>();
	  evaluatedNodes=0;
	 }

	 protected State popOpenList() {
	  evaluatedNodes++;
	  return openList.poll();
	 }
	
	public  Solution search(Searchable s){
		return null;
		}

	public int getNumberOfNodesEvaluated() {
	  return evaluatedNodes;
	 }
	 
	public void addToOpenList(State s)
	 {
		 openList.add(s);
	 }
	
	public boolean openListContains(State s)
{
		return openList.contains(s);
}

	
	/**
	 * @param searcher any kind of searcher
	 * @param searchable any kind of searchable
	 * searching on any kind of Searchable using any kind of Searcher
	 */
	//testing the algorithm using any kind of Searcher to search on any kind of se
	@SuppressWarnings("unused")
	public void testSearcher(Searcher searcher, Searchable searchable){
		
		   Solution sol=searcher.search(searchable);
		   int n = searcher.getNumberOfNodesEvaluated();
		   System.out.println("The number of nodes that the algorithm is evaluated is : " + n);

		}

	/**
	 * 
	 * @param goalState
	 * @param startState
	 * @return reconstructed the path from the goal state to start state
	 */
	public Solution backTrace(State goalState, State startState)
	
	{
		
		State s=new State(goalState.getState());
		s.setCameFrom(goalState.getCameFrom());
		
		ArrayList<String> backList=new ArrayList<String>();
		backList.add(s.getState());
		
		while(!s.equals(startState))
		{
//			System.out.println(s.getState());
		
			
			backList.add(s.getCameFrom().getState());
			s=s.getCameFrom();
		
			
		}
//		System.out.println(startState.getState());
		backList.add(startState.getState());
		
		return new Solution(backList);
	}
	
	
}


