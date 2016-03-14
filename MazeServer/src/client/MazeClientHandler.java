package client;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import Entities.CellType;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.Search.Astar;
import algorithms.Search.BFS;
import algorithms.Search.CommonSearcher;
import algorithms.Search.Maze3dSearch;
import algorithms.Search.Solution;
import algorithms.Search.State;
import utils.MapToZipUtil;
import View.ViewRequestType;

public class MazeClientHandler implements ClientHandler {

	private Socket someClient;
	@Override
	public void run() {
		
		ObjectInputStream input = null;
		try {
			input = new ObjectInputStream(someClient.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ViewRequestType<Object> request  = readClientRequest(input);
		switch (request.GetType()) {
			case "generate":{
				try {
					sendGeneratedMaze((Maze3d)request.GetData());						
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			case "solve by bfs":{
				solveMaze(request, input);
				break;
			}
			case "solve by astar":{
				solveMaze(request, input);
				break;
			}
			case "hint":
			{
				solveMaze(request, input);
				break;
			}
		}
	}

	private void solveMaze(ViewRequestType<Object> request, ObjectInputStream input) {
		Maze3d maze = readMaze(input);
		 
			Maze3d maze1=checkMazeAlreadyExist(maze);
			if (maze1!=null)
			{
			sendSolution(MapToZipUtil.getSolutionsMap().get(maze1));
			return;
			}
		else
		{
		Solution mySolution = ((CommonSearcher) request.GetData()).search(new Maze3dSearch(maze));
		MapToZipUtil.getSolutionsMap().put(maze, mySolution);
		MapToZipUtil.saveMapSolutions();
		sendSolution(mySolution);
		}
	}


	private void sendSolution(Solution mySolution) {
		try {
			ObjectOutputStream output = new ObjectOutputStream(someClient.getOutputStream());
			output.writeObject(mySolution);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Maze3d readMaze(ObjectInputStream input) {
		Maze3d maze3d= null;
		try {
			maze3d = (Maze3d)input.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return maze3d;
	}

	private ViewRequestType<Object> readClientRequest(ObjectInputStream input) {
		ViewRequestType<Object> requestType= null;
		try {
			requestType = (ViewRequestType<Object>)input.readObject();
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return requestType;
	}

	/**
	 * 
	 * 
	 * @param maze3d 
	 * @return
	 * @throws IOException
	 */
	private void sendGeneratedMaze(Maze3d maze3d) throws IOException {
		ObjectOutputStream output = new ObjectOutputStream(someClient.getOutputStream());
		maze3d = new MyMaze3dGenerator().generate(maze3d.getLevel(), maze3d.getLine(), maze3d.getColumn());
		output.writeObject(maze3d);
		output.flush();		
	}

	@Override
	public void init(Socket someClient) {
		this.someClient = someClient;		
	}
	
	private Maze3d checkMazeAlreadyExist(Maze3d maze)
	{
		Maze3d maze1 = null;
		for (Maze3d currentmaze:MapToZipUtil.getSolutionsMap().keySet())
		{
			if(currentmaze.equals1(maze))
			return currentmaze;
			
			
		}
		return maze1;
		
			
	}

}
