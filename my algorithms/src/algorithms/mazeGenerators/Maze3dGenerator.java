package algorithms.mazeGenerators;

import java.io.Serializable;

/**
* 
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
* 
* <h1>Maze3dGenerator</h1>
*
*Maze3dGenerator defining interface to create a maze3d
*/


public interface Maze3dGenerator  {
	
	Maze3d generate(int level,int line,int column);
	
	String measureAlgorithmTime(int x,int y,int z);
	

	

}
