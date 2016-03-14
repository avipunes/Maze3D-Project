package View;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;


// this is (1) the common type, and (2) a type of widget
// (1) we can switch among different MazeDisplayers
// (2) other programmers can use it naturally
public abstract class MazeDisplayer extends Canvas{
	
	// just as a stub...
	public int[][][] mazeData={
			   		{
				      {0, 0, 0},
				      {0, 0, 0},
				      {0, 0, 0},
			   		}
				};
	
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
//		
//		MyMaze3dGenerator maze = new MyMaze3dGenerator();
//		Maze3d m=maze.generate(3, 20, 20);
//		mazeData=m.getCrossSectionByx(0);		
		
	}

	public void setMazeData(int[][][] mazeData){
		this.mazeData=mazeData;
	}
	
	public abstract  void setCharacterPosition(int level,int row,int col);

	public int[][][] getmazeData() {
		return mazeData;
	}

	public abstract int getCharacterX();
	public abstract int getCharacterY();
	public abstract int getCharacterZ();
	public abstract void mazeInit();
	

}