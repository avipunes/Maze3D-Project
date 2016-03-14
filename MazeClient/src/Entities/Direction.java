package Entities;


public enum Direction{
	Left,
	Right,
	Up,
	Down,
	LevelUp,
	LevelDown;

	public int getIndex(){return ordinal()+10;}
}
