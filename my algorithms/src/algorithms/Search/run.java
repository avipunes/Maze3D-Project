package algorithms.Search;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
*/


import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;

public class run {

	public static void main(String[] args)
	
	{
		
		Maze3d maze=new Maze3d(3,8,8);
		MyMaze3dGenerator mymaze=new MyMaze3dGenerator();
		maze=mymaze.generate(3,10,10);
		
		Maze3dSearch mys= new Maze3dSearch(maze);
	
		State s = new State(maze.getStart().toString());
		
		mys.getAllPossibleStates(s);
				
	  Searcher tester=new BFS();
	  System.out.println("BFS: ");
	  tester.testSearcher(new BFS(), new Maze3dSearch(maze));
	  
	  System.out.println("Astar ManhattanDistance : ");
	  tester.testSearcher(new Astar(new ManhattanDistance()), new Maze3dSearch(maze));
	  
	  System.out.println("Astar AirDistance : ");
	  tester.testSearcher(new Astar(new AirDistance()), new Maze3dSearch(maze));

	}

}
