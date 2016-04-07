package algorithms.dem;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
*/


import algorithms.Search.AirDistance;
import algorithms.Search.Astar;
import algorithms.Search.BFS;
import algorithms.Search.ManhattanDistance;
import algorithms.Search.Maze3dSearch;
import algorithms.Search.Searcher;
import algorithms.Search.State;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;



public class demo {
/**
 * 
 * @param args any kind of string
 * 	Creating a maze with DFS , and searching on it by using A*,BFS Algotithms
 * @throws IOException 
 */
	public static void main(String[] args) throws IOException
	
	{
		
		Maze3d maze;
		MyMaze3dGenerator myMaze=new MyMaze3dGenerator();
		maze=myMaze.generate(3, 10,10);
		
		Maze3dSearch myS= new Maze3dSearch(maze);
	
		State state = new State(maze.getStart().toString());
		
		myS.getAllPossibleStates(state);
				
	  Searcher tester=new BFS();
	  System.out.println("The back track solution for the maze by BFS algoritm is : ");
	  tester.testSearcher(new BFS(), new Maze3dSearch(maze));
	  
	  System.out.println("The back track solution for the maze by Astar Manhattan Distance algoritm is : ");
	  tester.testSearcher(new Astar(new ManhattanDistance()), new Maze3dSearch(maze));
	  
	  System.out.println("The back track solution for the maze by Astar AirDistance algorithm is : ");
	  tester.testSearcher(new Astar(new AirDistance()), new Maze3dSearch(maze));

	  
		
		
		Maze3d maze1;
		MyMaze3dGenerator mymaze=new MyMaze3dGenerator();
		maze1=mymaze.generate(3, 20,30); //... generate it
	// save it to a file
	OutputStream out=new MyCompressorOutputStream(new FileOutputStream("1.maz"));
	
	out.write(maze1.toByteArray());
	out.flush();
	out.close();
	
	InputStream in=new MyDecompressorInputStream(new FileInputStream("1.maz"));
	
	
	byte b[]=new byte[maze1.toByteArray().length];
	in.read(b);
	in.close();
	Maze3d loaded=new Maze3d(b);
	System.out.println(loaded.equals1(maze1));
	  
	}
	
}


