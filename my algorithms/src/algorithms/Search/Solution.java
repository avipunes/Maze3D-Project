package algorithms.Search;

import java.io.Serializable;

/**
* @author  Avi Punes
* @version 1.0
* @since   2015-08-30 
* 
* <h1>Solution</h1>
* this class is going to contain a solution, any kind of algo will return an object of solution
* 
*/

import java.util.ArrayList;

public class Solution implements Serializable{

	public ArrayList<String> mySolution;	//arrayList of string that all the strings in the array represent the solution for some problem 

	//CTOR
	public Solution(ArrayList<String> mySolution) 
	{
		this.mySolution = mySolution;
	}

	public ArrayList<String> getMySolution() {
		return mySolution;
	}

	
	
	
	
}

