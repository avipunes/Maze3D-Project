package algorithms.mazeGenerators;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
*/


import java.util.ArrayList;

public class Run {

	
	private static void testMazeGenerator(Maze3dGenerator mg){
		
		
		mg.measureAlgorithmTime(3,5,9);
		// generate another 3d maze
		Maze3d maze=mg.generate(3,10,10);
		// get the maze entrance
		Position p=maze.getStart();
		// print the position
		System.out.println("the start position is" +" " +"{"+p.getLevel()+","+p.getLine()+","+p.getColumn()+"}"); // format "{x,y,z}"
		// get all the possible moves from a position
		
		ArrayList<Position> movesList=new ArrayList<Position>( maze.getAllNeightbors(p,maze));
		
		String[] moves=new String [movesList.size()];
		System.out.println("all the possible moves from a start position ");
		for (int i = 0; i < moves.length; i++) {
			
			moves[i]="{"+movesList.get(i).getLevel()+","+movesList.get(i).getLine()+","+movesList.get(i).getColumn()+"}";
			
		}
		
		// print the moves
		for(String move : moves)
		System.out.println(move);
		// prints the maze exit position
		System.out.println("the  goal position is" +" " +"{"+maze.getGoal().getLevel()+","+maze.getGoal().getLine()+","+maze.getGoal().getColumn()+"}");
		
		try{

		int[][] maze2dx=maze.getCrossSectionByx(2);
		
			for (int i = 0; i < maze.getLine(); i++) 
			{
				System.out.println();
				for (int j = 0; j < maze.getColumn(); j++) 
				{
				
					System.out.print(maze2dx[i][j]);
				}
				
			}
			System.out.println();

		int[][] maze2dy=maze.getCrossSectionByY(5);
		for (int i = 0; i < maze.getLevel(); i++) 
		{
			System.out.println();
			for (int j = 0; j < maze.getColumn(); j++) 
			{
			
				System.out.print(maze2dy[i][j]);
			}
		}
		System.out.println();


		int[][] maze2dz=maze.getCrossSectionByZ(0);
		
		for (int i = 0; i < maze.getLevel(); i++) 
		{
			System.out.println();
			for (int j = 0; j < maze.getLine(); j++) 
			{
			
				System.out.print(maze2dz[i][j]);
			}
		}
		System.out.println();

	maze.getCrossSectionByZ(-1);
	} catch (IndexOutOfBoundsException e){
		System.out.println(e.getMessage());
		
	}
		}
	
	
	
	public static void main(String[] args)
	
	{
		

		testMazeGenerator(new SimpleMaze3dGenerator());
		System.out.println();
		testMazeGenerator(new MyMaze3dGenerator());

		
		
	}
	

}


