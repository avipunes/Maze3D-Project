package Model;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import Controller.MyController;
import Entities.CellType;

import java.util.zip.GZIPOutputStream;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import utils.JaxbUtil;
import algorithms.Search.Astar;
import algorithms.Search.BFS;
import algorithms.Search.CommonSearcher;
import algorithms.Search.Maze3dSearch;
import algorithms.Search.Solution;
import algorithms.Search.State;
import View.CLI;
import View.MyView;
import View.ViewRequestType;

public class Maze3dModel extends Observable implements Model {
	
	int characterX;
	int characterY;
	int [][][] mazeData;
	Position currentpos;
	Maze3d maze=null;
	ExecutorService executor= Executors.newFixedThreadPool(JaxbUtil.getProperties().getThreadPoolNumber());
	HashMap<Maze3d, Solution> Solutions=new HashMap<Maze3d, Solution>();
	
	
	
	private Maze3d callServerAndGenerateMaze(ViewRequestType<Object> request) {

		Maze3d maze3dFromServer = null;

		InetAddress localaddr;
		try {
			localaddr = InetAddress.getLocalHost();
			System.out.println(localaddr.getHostAddress());
			Socket myServer = new Socket(localaddr.getHostAddress(), 12345);
			ObjectOutputStream output = new ObjectOutputStream(myServer.getOutputStream());

			output.writeObject(request);
			output.flush();
			ObjectInputStream input = new ObjectInputStream(myServer.getInputStream());

			maze3dFromServer = (Maze3d) input.readObject();
			System.out.println("message from the Server: " + maze3dFromServer.toString());
			output.close();

			input.close();
			myServer.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maze3dFromServer;
	}
	
	private Solution callServerAndSolveMaze(ViewRequestType<Object> request) {
		
		Solution solution = null;

		InetAddress localaddr;
		try {
			localaddr = InetAddress.getLocalHost();
			System.out.println(localaddr.getHostAddress());
			Socket myServer = new Socket(localaddr.getHostAddress(), 12345);
			ObjectOutputStream output = new ObjectOutputStream(myServer.getOutputStream());

			output.writeObject(request);
			output.flush();
			output.writeObject(maze);
			output.flush();
			
			ObjectInputStream input = new ObjectInputStream(myServer.getInputStream());

			solution = (Solution) input.readObject();
			System.out.println("message from the Server: " + solution.toString());
			output.close();

			input.close();
			myServer.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return solution;
	}

	
	public void Request(ViewRequestType<Object> request) throws IOException
	{
		
		switch (request.GetType()) 
		{
		case "generate":
			maze = callServerAndGenerateMaze(request);
			mazeData = maze.getMaze();

			mazeData[maze.getStart().getLevel()][maze.getStart().getLine()][maze.getStart().getColumn()] = CellType.Player
					.getIndex();

			currentpos = new Position(maze.getStart().getLevel(), maze.getStart().getLine(),
					maze.getStart().getColumn());

			setGoal();

			break;
			
		case "solve by bfs":	
			
			resetMaze();


			Future<Solution> solve = executor.submit(new Callable<Solution>() {
				@Override
				public Solution call() throws Exception {
					Position tempbfs = maze.getStart();
					maze.setStart(currentpos);
					
					Solution mySolutionBfs = callServerAndSolveMaze(request);

					for (String t : mySolutionBfs.mySolution) {
						State state = new State(t);
						Integer[] pos = state.toInt();
						mazeData[pos[0]][pos[1]][pos[2]] = CellType.SolutionPath.getIndex();
					}
					maze.setStart(tempbfs);
					mazeData[maze.getGoal().getLevel()][maze.getGoal().getLine()][maze.getGoal()
							.getColumn()] = CellType.Goal.getIndex();

					return mySolutionBfs;
				}

				
			});

			try {
				solve.get();
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
			
		case "solve by astar":

			resetMaze();

			Future<Solution> solveAstar = executor.submit(new Callable<Solution>() {

				@Override
				public Solution call() throws Exception {
					Position tempastar = maze.getStart();
					maze.setStart(currentpos);
					Solution mySolution = callServerAndSolveMaze(request);
					for (String t : mySolution.mySolution) {
						State state = new State(t);
						Integer[] pos = state.toInt();
						mazeData[pos[0]][pos[1]][pos[2]] = CellType.SolutionPath.getIndex(); 
					}
					maze.setStart(tempastar);

					setGoal();
					return mySolution;
				}
			});

			try {
				solveAstar.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;

		
		case "save":
			resetMaze();
			maze.setStart(currentpos);
			OutputStream out=(OutputStream) request.GetData();
			int size1=maze.toByteArray().length;
			out.write(size1);
			out.write(maze.toByteArray());
			out.flush();
			out.close();
			break;
				
		case "load":	
			InputStream in = (InputStream) request.GetData();

			int size = in.read();
			byte b[] = new byte[size];

			in = (InputStream) request.GetData();
			in.read(b);
			maze = (new Maze3d(b));
			mazeData = maze.getMaze();
			currentpos = new Position(maze.getStart().getLevel(), maze.getStart().getLine(),
					maze.getStart().getColumn());
			mazeData[maze.getStart().getLevel()][maze.getStart().getLine()][maze.getStart().getColumn()] = CellType.Player
					.getIndex();
			setGoal();
			in.close();

			break;


		case "hint":
					
			resetMaze();
					
			Future<Solution> solveHint= executor.submit(new Callable<Solution>() {

						@Override
					public Solution call() throws Exception {
						Position temphint=maze.getStart();
						maze.setStart(currentpos);
						Solution mySolution=callServerAndSolveMaze(request);
						int size2 =mySolution.getMySolution().size()-2;
							
						for (int i = 4; i > 0; i--) 
						{
							State state=new State(mySolution.getMySolution().get(size2-i));
							Integer[] pos=state.toInt();
							mazeData[pos[0]][pos[1]][pos[2]]=CellType.SolutionPath.getIndex();
						}
							maze.setStart(temphint);
							setGoal();
							return mySolution;
						}
					});
					
					try {
						solveHint.get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
			break;
			
		case "cli":
			
			
			MyView view=new MyView(((CLI)request.GetData()).getIn(),((CLI)request.GetData()).getOut());
			MyModel model=new MyModel();
			
			MyController controller=new MyController(model,view,((CLI)request.GetData()).getMap());
			
			model.setController(controller);
			view.setController(controller);
			
			CLI cli =(CLI)request.GetData();
			view.setView(cli);
			cli.start();
			
			return;

			
		case "move right":
			moveRight();
			break;
			
		case "move left":
			moveLeft();
			break;
		case "move up":
			moveUp();
			break;
		case "move down":
			moveDown();
			break;
	
		case "move level up":	
			moveUpLevel();
			break;
		
		case "move level down":	
			moveDownLevel();
			break;
			
		}	
		setChanged();
		notifyObservers(mazeData);
	}

	@Override
	public int[][][] getData() {
		return mazeData;
		
	}

	@Override
	public void moveUp() {
		moveCharacter(currentpos.getLevel(),currentpos.getLine()-1,currentpos.getColumn());	
	}

	@Override
	public void moveDown() {

		moveCharacter(currentpos.getLevel(),currentpos.getLine()+1,currentpos.getColumn());
	}

	@Override
	public void moveLeft() {

		moveCharacter(currentpos.getLevel(),currentpos.getLine(),currentpos.getColumn()-1);
	}

	@Override
	public void moveRight() {
		moveCharacter(currentpos.getLevel(),currentpos.getLine(),currentpos.getColumn()+1);	
	}

	@Override
	public void moveUpLevel() {
		if ((currentpos.getLevel()+2<maze.getLevel())&&(maze.getMaze()[currentpos.getLevel()+2][currentpos.getLine()][currentpos.getColumn()]!=1))
		{
			moveCharacter(currentpos.getLevel()+2,currentpos.getLine(),currentpos.getColumn());
		}
		if (!currentpos.equals(maze.getGoal())) {
			setGoal();
		}

	}

	@Override
	public void moveDownLevel() {
		if ((currentpos.getLevel()-2>=0)&&(maze.getMaze()[currentpos.getLevel()-2][currentpos.getLine()][currentpos.getColumn()]!=1))
		{
			moveCharacter(currentpos.getLevel()-2,currentpos.getLine(),currentpos.getColumn());
		}
		
		if (!currentpos.equals(maze.getGoal())) {
			setGoal();
		}
		
	}
	
	private void resetMaze()
	{
		for (int i = 0; i < maze.getLevel(); i++) 
		{
			for (int j = 0; j < maze.getLine(); j++) 
			{
				for (int j2 = 0; j2 < maze.getColumn(); j2++)
				{
					if (mazeData[i][j][j2]==CellType.SolutionPath.getIndex()||mazeData[i][j][j2]==CellType.Goal.getIndex()||mazeData[i][j][j2]==CellType.Player.getIndex()) 
					{
						mazeData[i][j][j2]=CellType.Path.ordinal();
					}	
				}
			}
		}
		
		setChanged();
		notifyObservers(mazeData[currentpos.getLevel()]);
	}
	
	public int toInt(byte[] b,int start)
	{
		int Int=0;
		Int=b[start++]<<24&0xff000000|
		(b[start++]<<16)&0x00ff0000|
		(b[start++]<< 8)&0x0000ff00|
		(b[start++]<< 0)&0x000000ff;
		
		return Int;
	}
	
	public void moveCharacter(int x, int y,int z)
	{

		if(x>=0 && x<mazeData.length && y>=0 && y<mazeData[x].length && z>=0 && z<mazeData[x][y].length && mazeData[x][y][z]!=1 )
		{
			mazeData[currentpos.getLevel()][currentpos.getLine()][currentpos.getColumn()]=CellType.Path.ordinal();
			currentpos=new Position(x, y, z);
			if (currentpos.equals(maze.getGoal())) 
			{
				mazeData[currentpos.getLevel()][currentpos.getLine()][currentpos.getColumn()]=CellType.PlayerReachGoal.getIndex();
			}
			else
				mazeData[currentpos.getLevel()][currentpos.getLine()][currentpos.getColumn()]=CellType.Player.getIndex();
		}
		
	}
	
	public void setGoal() 
	{
		if (currentpos.getLevel()==maze.getGoal().getLevel()) 
		{
			mazeData[maze.getGoal().getLevel()][maze.getGoal().getLine()][maze.getGoal().getColumn()]=CellType.Goal.getIndex();
		}
	}
	
	public void saveSolutionMap() {

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("solutionMap.zip")));
            oos.writeObject(Solutions);
        } catch (IOException e) {
            setChanged();
            notifyObservers("Error saving solutions");
        } finally {
            try {
                oos.flush();
                oos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }   
        }
    }
	
	public void loadSolutionMap() throws FileNotFoundException, IOException, ClassNotFoundException {
      
	}
	
	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(b);
		o.writeObject(obj);
		return b.toByteArray();
	}

	public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream b = new ByteArrayInputStream(bytes);
		ObjectInputStream o = new ObjectInputStream(b);
		return o.readObject();
	}
	
}
	