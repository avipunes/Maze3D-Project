package algorithms.mazeGenerators;

import java.io.Serializable;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30
* 
*  <h1>SimpleMaze3dGenerator</h1>
*  creating a maze using simple algorithm and creating a artificial path from
*   start position to the goal position
*  
*/


import java.util.Random;

public class SimpleMaze3dGenerator extends CommonAlgorithms implements Serializable {

	/**
	 * @param level creating level floors 
	 * @param line  creating line , lines
	 * @param column creating column, columns
	 * @return maze3d using the simple algo (random walls and free slots and then creating
	 *  a simple path to goal from start position
	 */
	
	@Override
	public Maze3d generate(int level, int line, int column) {
		
		Random rand = new Random();
		int [][][] m=new int[level][line][column];
		
		
		for (int i = 0; i < level; i++)
		{
			
			for (int j = 0; j < line; j++)
			
			{	
				for (int k = 0; k < column; k++) 
				{		
					m[i][j][k]=rand.nextInt(2);
					
				}
			}
			
		}
		
		int startline=rand.nextInt(line);
		int starcolumn=rand.nextInt(column);
			
		for (int i = 0; i < level; i++) 
		{
			
			m[i][startline][starcolumn]=0;
				
		}
		
		
		
		Maze3d maze=new Maze3d(level, line, column, m);
		
		maze.setStart(new Position (0,startline,starcolumn));
		maze.setGoal(new Position (level-1,startline,starcolumn));
		

		
		
		return maze;
	}

	
	

}

