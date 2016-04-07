package algorithms.mazeGenerators;

import java.io.Serializable;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30
* 
* <h1>MyMaze3dGenerator</h1>
* Creating a maze by using DFS algorithm
* implements the method generate and the DFS algorithm it self
* 
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class MyMaze3dGenerator extends CommonAlgorithms implements Serializable{
	
	Maze3d maze;
	HashMap<String,String> visited =  new HashMap<String,String>();

	@Override
	public Maze3d generate(int level, int line, int column) 
	
	{
		
		
		maze= new Maze3d(level, line, column);

			for (int i = 0; i < level; i++)
			{
				
				for (int j = 0; j < line; j++)
				
				{	
					
					for (int k = 0; k < column; k++) 
					{		
						
							
							maze.getMaze()[i][j][k]=1;
							visited.put(new Position(i, j, k).toString(), "unvisited");
							
					}
					
				}
			}
				
		Random rand = new Random();
		Position start = new Position(0, rand.nextInt(line), 0);
		maze.setStart(start);
		DFS(start);
		
		
		return maze;
	}
	
	
	
	void DFS(Position start)
	{
		
	
		
			
			String s =visited.get(start.toString());
			if (s.equals("unvisited"))
			{
				visited.remove(start.toString());
				visited.put(start.toString(),"visited");
				
				maze.getMaze()[start.getLevel()][start.getLine()][start.getColumn()]=0;
			}
				
			
			
			ArrayList<Position> neibores = maze.getAllNeightborsDFS(start, maze);
			Collections.shuffle(neibores);
			for (Position neibor : neibores)
			{
				String s1 =visited.get(neibor.toString());
				if (s1.equals("unvisited"))
				{
					maze.getMaze()[neibor.getLevel()][neibor.getLine()][neibor.getColumn()]=0;
					maze.breakWall(start, neibor);
					visited.remove(neibor.toString());
					
					visited.put(neibor.toString(), "visited");
					
					maze.setGoal(neibor);
					DFS(neibor);

				}
				
    	
			}
		
		
	}
}
	
