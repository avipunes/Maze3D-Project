package algorithms.Search;

import java.io.Serializable;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
* 
* <h1>Maze3dSearch</h1>
* extends CommonSearchable class and implement his own way to getAllPossibleStates
* 
* this class will provide the option to search on a maze3d problem
*/


import java.util.ArrayList;



import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public class Maze3dSearch extends CommonSearchable implements Serializable{

	private Maze3d maze;
	
	public Maze3dSearch() {
		// TODO Auto-generated constructor stub
	}
	public void setpath(State s)
	{
		Integer[] pos=s.toInt();
		maze.setPath(new Position(pos[0],pos[1],pos[2]));
	}
	public Maze3d getMaze() {
		return maze;
	}

	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}

	public Maze3dSearch(Maze3d maze) {
		this.maze=maze;
	}
	
	@Override
	public State getStartState() {
		return new State(maze.getStart().toString(),0,null);
	}

	@Override
	public State getGoalState() {
		return new State(maze.getGoal().toString());
		//return new State(new Position(2,4,1).toString());
		
	}

	/**
	 * @param s - state that we want to know what are all the possible states from it
	 * @return ArrayList of all the possible states from s state
	 */
	@SuppressWarnings("static-access")
	@Override
	public ArrayList<State> getAllPossibleStates(State s) {
		
		String s1 = new String (s.getState());
		
		
		String [] s2=new String [3];
		s2=s1.split(",");
		int [] position=new int[3];
		for (int i = 0; i < position.length; i++) {
	
			position[i]=new Integer(0).valueOf(s2[i]);
			
		}
		
		Position p = new Position(position[0],position[1],position[2]);
		
		ArrayList<State> stateList=new ArrayList<State>();
		ArrayList<Position> positionList=new ArrayList<Position>();
		
		positionList=maze.getAllNeightbors(p, maze);
		
		for (int j = 0; j < positionList.size(); j++) {
			stateList.add(new State(positionList.get(j).toString()));
			
		}
		
		return stateList;
		
		
		

	}
	 
	 /**	
	 * @param a state 
	 * @param b	anther state
	 * @return this class define for cost to move from one state the a anther 
	 */
	public int CostToState(State a, State b){return 1;}
	@Override
	public void printMaze() {
	maze.printMaze();
	}
}
