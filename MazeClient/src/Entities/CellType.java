package Entities;

import java.io.Serializable;

public enum CellType implements Serializable
{
	
	Path,
	PlayerReachGoal,
	Player,
	Start,
	Goal,
	SolutionPath;
	
	
	public int getIndex(){return ordinal()+2;}
}
