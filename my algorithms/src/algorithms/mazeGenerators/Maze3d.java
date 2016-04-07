package algorithms.mazeGenerators;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
* <h1>Maze3d</h1>
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
* 
* Maze3d Class is defining a maze3d
*/


import java.util.ArrayList;
import java.util.Collections;

public class Maze3d  implements Serializable {
	
	private int level;	//x
	private int column; //y
	private int line;	//z
	private int [][][] maze;	//the maze it self
	private Position start;
	private Position goal;
	
	
	
	
	//CTOR
	/**
	 * 
	 * @param level How muck floors in the maze3d
	 * @param line	How much lines	in the maze3d
	 * @param column How much columns in the maze3d	
	 * creating a 3d array with those parameters  
	 */
	public Maze3d(int level, int line, int column) {
		
		this.maze = new int [level][line][column];
		this.level = level;
		this.column = column;
		this.line = line;
		

	}
	
	//CTOR
	/**
	 * 
	 * @param level How muck floors in the maze3d
	 * @param line	How much lines	in the maze3d
	 * @param column How much columns in the maze3d
	 * @param mymaze 3d array that is already initialized-now need to creat new 3d array
	 */
	public Maze3d(int level, int line, int column,int[][][] mymaze) {
		
		this.level = level;
		this.column = column;
		this.line = line;
		this.maze=mymaze;

	}

	public Maze3d(byte [] b) {
		
		level = byteToInt(b,0);
		line =	byteToInt(b,4);
		column =byteToInt(b,8);
		start = new Position ((byteToInt(b,12)),(byteToInt(b,16)),(byteToInt(b,20)));
		goal=new Position((byteToInt(b,24)),(byteToInt(b,28)),(byteToInt(b,32)));
		
		maze=new int[level][line][column]; 
		
		int k = 36;
		
		for (int i = 0; i < level; i++) {
			for (int j = 0; j < line; j++) {
				for (int j2 = 0; j2 < column; j2++) {
					maze[i][j][j2]=b[k];
					k++;
				}
			}
			
		}
	}
	
	public Maze3d(Maze3d maze){
		
		this.level=maze.getLevel();
		this.line=maze.getLine();
		this.column=maze.getColumn();
		
		this.maze=maze.getMaze();
		setStart(maze.getStart());
		setGoal(maze.getGoal());
	
	}
	
	
	public int getLevel() {
		return level;
	}
	

	public int getColumn() {
		return column;
	}

	

	public int getLine() {
		return line;
	}

	

	public void setMaze(int[][][] maze) {
		this.maze = maze;
	}

	public int[][][] getMaze() {
		return maze;
	}

	public Position getStart() {
		return start;
	}

	public void setStart(Position start) {
		this.start = start;
	}

	public Position getGoal() {
		return goal;
	}

	public void setGoal(Position goal) {
		this.goal = goal;
	}
	
	
	/**
	 * 
	 * @param p a position that we need to remember that we used and worked on in the 3d maze//3d array
	 */
	public void setPath(Position p) {
		this.maze[p.getLevel()][p.getLine()][p.getColumn()] = 3; //3 - Path
	}
/**
 * 
 * @param current x1,y1,z1
 * @param p	x2,y2,z2
 * taking the point that between that two positions and breaking the wall there
 */
	public void breakWall(Position current,Position p)
	{
		maze[((current.getLevel()+p.getLevel())/2)][((current.getLine()+p.getLine())/2)][((current.getColumn()+p.getColumn())/2)]=0;
		
	}
	/**
	* 
 	* @param current x1,y1,z1
 	* @param p	x2,y2,z2
	* @return true or false if there is a wall between two positions
	*/
	public boolean CheckWall(Position current,Position p)
	{
		return maze[((current.getLevel()+p.getLevel())/2)][((current.getLine()+p.getLine())/2)][((current.getColumn()+p.getColumn())/2)]==1;
		
	}
	
	/**
	 * 
	 * @param p x,y,z
	 * @param maze a maze to work on
	 * @return arrayList of all the possible neighbors //neighbor defined to be with one point distance
	 */
	public ArrayList<Position> getAllNeightbors(Position p,Maze3d maze)
	
	{
		ArrayList< Position> List=new ArrayList<Position>();
		if (( maze.getMaze()[p.getLevel()][p.getLine()][p.getColumn()]!=1)&&(p.getColumn()+1<maze.getColumn())&&(maze.getMaze()[p.getLevel()][p.getLine()][p.getColumn()+1]==0))
		List.add(new Position (p.getLevel(),p.getLine(),p.getColumn()+1));
		if (( maze.getMaze()[p.getLevel()][p.getLine()][p.getColumn()]!=1)&&(p.getColumn()-1>=0)&&(maze.getMaze()[p.getLevel()][p.getLine()][p.getColumn()-1]==0))
		List.add(new Position (p.getLevel(),p.getLine(),p.getColumn()-1));		
		if (( maze.getMaze()[p.getLevel()][p.getLine()][p.getColumn()]!=1)&&(p.getLine()+1<maze.getLine())&&(maze.getMaze()[p.getLevel()][p.getLine()+1][p.getColumn()]==0))
		List.add(new Position (p.getLevel(),p.getLine()+1,p.getColumn()));
		if (( maze.getMaze()[p.getLevel()][p.getLine()][p.getColumn()]!=1)&&(p.getLine()-1>=0)&&(maze.getMaze()[p.getLevel()][p.getLine()-1][p.getColumn()]==0))
		List.add(new Position (p.getLevel(),p.getLine()-1,p.getColumn()));
		if (( maze.getMaze()[p.getLevel()][p.getLine()][p.getColumn()]!=1)&&(p.getLevel()+1<maze.getLevel())&&(maze.getMaze()[p.getLevel()+1][p.getLine()][p.getColumn()]==0))
		List.add(new Position (p.getLevel()+1,p.getLine(),p.getColumn()));
		if (( maze.getMaze()[p.getLevel()][p.getLine()][p.getColumn()]!=1)&&(p.getLevel()-1>=0)&&(maze.getMaze()[p.getLevel()-1][p.getLine()][p.getColumn()]==0))
		List.add(new Position (p.getLevel()-1,p.getLine(),p.getColumn()));
		Collections.shuffle(List);
	
		
		return List;
	}

	/**
	 * 
	 * @param p x,y,z
	 * @param maze a maze to work on
	 * @return arrayList of all the possible neighbors for DFS algorithm //neighbor defined to be with two points distance
	 */
	public ArrayList<Position> getAllNeightborsDFS(Position p,Maze3d maze)

	
	{
		ArrayList<Position> List = new ArrayList<Position>();
		if (p.getLevel()+2<maze.getLevel())
			List.add(new Position (p.getLevel()+2,p.getLine(),p.getColumn()));
		if (p.getColumn()+2<maze.getColumn())
			List.add(new Position (p.getLevel(),p.getLine(),p.getColumn()+2));
		if ((p.getLine()-2>=0))
			List.add(new Position (p.getLevel(),p.getLine()-2,p.getColumn()));
		if ((p.getColumn()-2>=0))
			List.add(new Position (p.getLevel(),p.getLine(),p.getColumn()-2));		
		if ((p.getLevel()-2>=0))
			List.add(new Position (p.getLevel()-2,p.getLine(),p.getColumn()));
		if ((p.getLine()+2<maze.getLine()))
			List.add(new Position (p.getLevel(),p.getLine()+2,p.getColumn()));
	Collections.shuffle(List);
	
		
	
		
	return List;
	

	}
	
	/**
	 * 
	 * @param x level
	 * @return crossed 2d maze of the original 3d maze,by the line
	 */
	public int[][] getCrossSectionByx(int x)
	{
		if (x<0 || x>this.level)
			throw new IndexOutOfBoundsException("Index level error");
		
		int [][] newmaze = new int[this.line][this.column];
		
		System.out.println("CrossSectionByX");
		for (int line=0; line<this.line;line++)
		{
			for (int column=0;column<this.column;column++)
			{
				newmaze[line][column]=this.maze[x][line][column];
			}
		}
		return newmaze;
	}
	
	/**
	 * 
	 * @param y line
	 * @return crossed 2d maze of the original 3d maze, by the line
	 */
	public int[][] getCrossSectionByY(int y)
	{
		if (y<0 || y>this.line)
			throw new IndexOutOfBoundsException("Index line error");
		
		int [][] newmaze = new int[this.level][this.column];
		
		System.out.println("CrossSectionByY");
		for (int level=0; level<this.level;level++)
		{
			for (int column=0;column<this.column;column++)
			{
				newmaze[level][column]=this.maze[level][y][column];
			}
		}
		return newmaze;
	}
		
	/**
	 * 
	 * @param z line
	 * @return crossed 2d maze of the original 3d maze, by the column
	 */
	public int[][] getCrossSectionByZ(int z)
	{
			
		if (z<0 || z>this.column)
			throw new IndexOutOfBoundsException("Index column error");
		
		int [][] newmaze = new int[this.level][this.line];
		
		System.out.println("CrossSectionByZ");
		for (int level=0; level<this.level;level++)
		{
			for (int line=0;line<this.line;line++)
			{
				newmaze[level][line]=this.maze[level][line][z];
			}
		}
		return newmaze;
	}
	
	public void printMaze(){

			
			System.out.println("{");
			for (int i = 0; i < level; i++) 
			{
				System.out.println("{");
				for (int j = 0; j < line; j++)
				{
					for (int j2 = 0; j2 < column; j2++)
					{
						System.out.print(maze[i][j][j2]+",");
						
					}
					System.out.println();
				}
				System.out.println("}");
			}
			System.out.println("}");
		}
		
	/**
	 * 
	 * @param a
	 * @param b
	 * @return the cost between to positions that we want to define
	 * this will change some search algorithms calculations
	 */
	public int CostToState(Position a, Position b){return 1;}
		
	public byte[] intToByteArray(int z)
	{
		ByteBuffer bb = ByteBuffer.allocate(4); 
		bb.putInt(z); 
		return bb.array();
	}

	public int byteToInt(byte[] b,int start){

	
	int i=(b[start]<<24)&0xff000000|
		(b[start+1]<<16)&0x00ff0000|
		(b[start+2]<< 8)&0x0000ff00|
		(b[start+3]<< 0)&0x000000ff;
	
	return i;
}

	
	//compress the maze in to a Byte array 
	public byte[] 	toByteArray(){
	
	
	int size=(level*line*column)+36;
	byte []b =new byte [size];
	
	//maze size level line column
	int i=0;
	for (int j = 0; j < 4; j++)
	{
		b[j]=intToByteArray(level)[i];
		i++;
	}
	
	 i=0;
	for (int j = 4; j < 8; j++) {
		b[j]=intToByteArray(line)[i];
		i++;
	}
	i=0;
	for (int j = 8; j < 12; j++) {
		b[j]=intToByteArray(column)[i];
		i++;
	}
	
	//start p
	i=0;
	for (int j = 12; j < 16; j++) {
		b[j]=intToByteArray(start.getLevel())[i];
		i++;
		
	}	
	i=0;
	for (int j = 16; j < 20; j++) {
		b[j]=intToByteArray(start.getLine())[i];
		i++;
	}	
	i=0;
	for (int j = 20; j < 24; j++) {
		b[j]=intToByteArray(start.getColumn())[i];
		i++;
	}	
	
	//goal p
	i=0;
	for (int j = 24; j < 28; j++) {
		b[j]=intToByteArray(goal.getLevel())[i];
		i++;
	}	
		i=0;
	for (int j = 28; j < 32; j++) {
		b[j]=intToByteArray(goal.getLine())[i];
		i++;
	}	
		i=0;
	for (int j = 32; j < 36; j++) {
		b[j]=intToByteArray(goal.getColumn())[i];
		i++;
	}

	

	int j2 = 36;
	for (int i1 = 0; i1 < level; i1++) {
		for (int j = 0; j < line; j++) {
			for (int k = 0; k < column; k++) {
				b[j2]=(byte) maze[i1][j][k];
				j2++;			
			}
			
		}
		
	}
	return b;
}


	public boolean equals1 (Maze3d maze)
{
	
	if ((this.getLevel()!=maze.getLevel())||(this.getLine()!=maze.getLine())||(this.getColumn()!=maze.getColumn()))
	{
		return false;
	}
	
	if (!(this.getStart().getLevel()==maze.getStart().getLevel())&&(this.getStart().getLine()==maze.getStart().getLine())&&(this.getStart().getColumn()==maze.getStart().getColumn()))
	{
		return false;
	}	
	
	if (!(this.getGoal().getLevel()==maze.getGoal().getLevel())&&(this.getGoal().getLine()==maze.getGoal().getLine())&&(this.getGoal().getColumn()==maze.getGoal().getColumn()))
	{
		return false;
	}	
	
	
	for (int i = 0; i < this.getLevel(); i++) {
		for (int j = 0; j < this.getLine(); j++) {
			for (int j2 = 0; j2 < this.getColumn(); j2++) {
				
				if (this.maze[i][j][j2]!=maze.getMaze()[i][j][j2])
				{
					return false;
				}
				
			}
			
		}
		
	}
	
	return true;
	
}
	@Override 
	public boolean equals(Object obj)
	{
		Maze3d maze=(Maze3d) obj;
	
	if ((this.getLevel()!=maze.getLevel())||(this.getLine()!=maze.getLine())||(this.getColumn()!=maze.getColumn()))
	{
		return false;
	}
	
	if (!(this.getStart().getLevel()==maze.getStart().getLevel())&&(this.getStart().getLine()==maze.getStart().getLine())&&(this.getStart().getColumn()==maze.getStart().getColumn()))
	{
		return false;
	}	
	
	if (!(this.getGoal().getLevel()==maze.getGoal().getLevel())&&(this.getGoal().getLine()==maze.getGoal().getLine())&&(this.getGoal().getColumn()==maze.getGoal().getColumn()))
	{
		return false;
	}	
	
	
	for (int i = 0; i < this.getLevel(); i++) {
		for (int j = 0; j < this.getLine(); j++) {
			for (int j2 = 0; j2 < this.getColumn(); j2++) {
				
				if (this.maze[i][j][j2]!=maze.getMaze()[i][j][j2])
				{
					return false;
				}
				
			}
			
		}
		
	}
	
	return true;
	
}
	
	
}
