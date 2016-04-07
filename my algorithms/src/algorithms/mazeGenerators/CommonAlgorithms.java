package algorithms.mazeGenerators;

/**
* <h1>CommonAlgorithms</h1>
* This abstract class implements the interface Maze3dGenerator.
* This class has the most common things for creating a problem like a Maze, each algorithm that will
* extend this class will add new methods.
* 
* The method "generate" is asking for an x,y and z to create a maze3d
* The method "measureAlgorithmTime" is asking again for an x,y and z parameters and it will use
* the first method "generate" and will print how much time it takes to the algorithm to create 
* the problem.
* 
* EACH ALGORITM WILL IMPLEMENTS HIW OWN WAY TO GENERATE THE PROBLEM BUT MEASURE THE TIME IS
* COMMON TO ALL ALGORITMS.
*
*IF THERE WILL BE A NEW PROBLEM, YOU HAVE TO ADD NEW GENERATE METHOD THAT RETURNS YOUR KYNDE OF PROBLEM
* 
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
*/




public abstract class CommonAlgorithms implements Maze3dGenerator {

	
	public abstract Maze3d generate(int level,int line,int column);

	
	
	public String measureAlgorithmTime(int x,int y,int z) {

		//the starting time 
		Long starttime = System.currentTimeMillis();
		
		generate(x,y,z);
		
		Long endtime = System.currentTimeMillis();
		Long finaltime = (endtime-starttime);
		
		System.out.println("Time to create the problem: " + finaltime);
		
		return finaltime.toString();		
		/**@return a string that represent how much time it's take to the algo to create the problem*/

	}

}
