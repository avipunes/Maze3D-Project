package algorithms.mazeGenerators;

import java.io.Serializable;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
* 
*<h1>Position</h1>
*defining a position on the maze (x,y,z)
* 
*/


public class Position implements Serializable{

	
	private Integer level;	//x
	private int line;		//y
	private int column;		//z
	
	//CTOR
	/**
	 * 
	 * @param level -x
	 * @param line  -y
	 * @param column -z
	 */
	public Position(int level, int line, int column) {
		
		this.level = level;
		this.line = line;
		this.column = column;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public int getLine() {
		return line;
	}


	public void setLine(int line) {
		this.line = line;
	}


	public int getColumn() {
		return column;
	}


	public void setColumn(int column) {
		this.column = column;
	}
	
	/**
	 * creating a string that represent the position for example (1,2,3) well return "1,2,3"
	 */
	@Override
	public String toString(){
		String s=new String();
		s=Integer.toString(level)+","+Integer.toString(line)+","+Integer.toString(column);
		

		return s;
		
	}
	
	public boolean equals(Position obj) {
		if ((this.getLevel()==obj.getLevel())&&(this.getLine()==obj.getLine())&&(this.getColumn()==obj.getColumn()))
		{
			return true;
		}
		else
			return false;
	}
	

}	
