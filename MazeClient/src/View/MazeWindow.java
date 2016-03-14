package View;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;

import Controller.Command;
import algorithms.Search.AirDistance;
import algorithms.Search.Astar;
import algorithms.Search.BFS;
import algorithms.Search.ManhattanDistance;
import algorithms.mazeGenerators.Maze3d;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import utils.JaxbUtil;

public class MazeWindow extends BasicWindow{

	Timer timer;
	TimerTask task;
	
	String fileName;
	Combo _createByComboBox;
	int level;
	int line;
	int column;
	MazeDisplayer maze;
	Maze3d maze1=null;
	

	public MazeWindow(String title, int width, int height) {
	
		super(title, width, height);
//		maze1=new Maze3d(3, 10, 11);
	}
	
	@Override
	void initWidgets() {
	
		shell.setLayout(new GridLayout(1,false));
    	Composite userOptionsMenu = new Composite(shell, SWT.BORDER);
    	RowLayout layout = new RowLayout(SWT.HORIZONTAL);
    	userOptionsMenu.setLayout(layout);
    	
    	Button generateButton=new Button(userOptionsMenu, SWT.PUSH);
		generateButton.setText("Generate");
    	
       	Label sizeText1 = new Label(userOptionsMenu,SWT.PUSH);
        Text t1 = new Text(userOptionsMenu, SWT.SINGLE | SWT.BORDER);
        t1.setText(String.valueOf(JaxbUtil.getProperties().getLevel()));
        sizeText1.setText("   level: ");

    	
       	Label sizeText = new Label(userOptionsMenu,SWT.PUSH);
        Text t2 = new Text(userOptionsMenu, SWT.SINGLE | SWT.BORDER);
        t2.setText(String.valueOf(JaxbUtil.getProperties().getLine()));
        sizeText.setText("   line: ");
    
    	Label createByText = new Label(userOptionsMenu,SWT.PUSH);
        Text t3 = new Text(userOptionsMenu, SWT.SINGLE | SWT.BORDER);
        t3.setText(String.valueOf(JaxbUtil.getProperties().getColumn()));
      	createByText.setText("   column: ");
      

		// maze=new Maze2D(shell, SWT.BORDER);		
		maze=new Maze3D(shell, SWT.BORDER);
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,2));		

		Label sizeText2 = new Label(userOptionsMenu,SWT.PUSH);
    	Combo c=new Combo(userOptionsMenu, 0);
    	sizeText2.setText("   Chosse Algorithm to solve by: ");
    	
    	String[] s=new String[3];
    	s[0]="BFS";
    	s[1]="Astar Man";
    	s[2]="Astar Air";
    	c.setItems(s);
    	c.select(0);
		
		Button solve=new Button(userOptionsMenu, SWT.PUSH);
		solve.setText("Solve Maze");
		solve.setEnabled(false);

		Button hintButton=new Button(userOptionsMenu, SWT.PUSH);
		hintButton.setText("Hint");
		hintButton.setEnabled(false);
		
		Button cli=new Button(userOptionsMenu, SWT.PUSH);
		cli.setText("cli");
		
	   	Button moveUp = new Button(userOptionsMenu,SWT.PUSH);
	   	moveUp.setText("Level Up");
	   	moveUp.setImage(new Image(display, "images/green_arrow_up.png"));
	   	moveUp.setEnabled(false);
        
     	Button moveDown = new Button(userOptionsMenu,SWT.PUSH);
     	moveDown.setText("Level Down");
     	moveDown.setImage(new Image(display, "images/green_arrow_down.png"));
     	moveDown.setEnabled(false);
      
     	Label floor = new Label(userOptionsMenu,SWT.PUSH);
     	floor.setText("  You Are in "+((Maze3D) maze).characterX+" Floor ");
        
     	
     	Menu menuBar = new Menu(shell, SWT.BAR);
        MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
        cascadeFileMenu.setText("&File");
        
        Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
        cascadeFileMenu.setMenu(fileMenu);
        MenuItem saveItem = new MenuItem(fileMenu, SWT.PUSH);
        saveItem.setText("&Save");
        saveItem.setEnabled(false);
        shell.setMenuBar(menuBar);
        
        MenuItem loadItem = new MenuItem(fileMenu, SWT.PUSH);
        loadItem.setText("&Load");
        shell.setMenuBar(menuBar);
        
        MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
        exitItem.setText("&Exit");
        shell.setMenuBar(menuBar);
   
     	
        
        loadItem.addListener(SWT.Selection, event-> {
            FileDialog fd=new FileDialog(shell,SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.maz"};
				fd.setFilterExtensions(filterExt);
				filename = fd.open();				
				
				setChanged();
			if (filename!=null) {
				
				((Maze3D) maze).setPlay(true);
				try {
					
					notifyObservers(new ViewRequestType<MyDecompressorInputStream>("load",new MyDecompressorInputStream(new FileInputStream(filename))));
					((Maze3D) maze).movedCharacter();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				maze1=new Maze3d(maze.mazeData.length,maze.mazeData[0].length,maze.mazeData[0][0].length);
				solve.setEnabled(true);
				maze.forceFocus();
			}	
			
        });

        saveItem.addListener(SWT.Selection, event-> {
        
          FileDialog fd=new FileDialog(shell,SWT.SAVE);
				fd.setText("save");
				fd.setFilterPath("");
				String[] filterExt = { "*.maz"};
				fd.setFilterExtensions(filterExt);
				filename = fd.open();	
				
				setChanged();
						
			if (filename!=null) {
				try {
					notifyObservers(new ViewRequestType<MyCompressorOutputStream>("save",new MyCompressorOutputStream(new FileOutputStream(filename))));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				}	
			
      });
		
	        exitItem.addListener(SWT.Selection, event-> {
	        shell.getDisplay().dispose();
        	System.exit(0);
	       });
		
		
		generateButton.addSelectionListener(new SelectionListener() {
			
	 
			@Override
			public void widgetSelected(SelectionEvent arg0) {	
				
				
				
				try {
					maze.setVisible(true);
					level=new Integer(0).parseInt(t1.getText());
					level=(level*2)-1;
					line=new Integer(0).parseInt(t2.getText());
					column=new Integer(0).parseInt(t3.getText());
					maze1=new Maze3d(level, line, column);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,"Iligal input"+ " :enter integer for each paramater: min 3 for each paramater","Error", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if (3<=level&&level<11&&3<=line&&line<1000&&3<=column&&column<1000) 
				{
					setChanged();
					
					notifyObservers(new ViewRequestType<Maze3d>("generate",maze1));
					((Maze3D) maze).setPlay(true);
					((Maze3D) maze).setCharacterStartPosition();
					
					if(((Maze3D) maze).moveLevelUp()) 
					{
						moveUp.setEnabled(true);
					}
					else 
					{
						moveUp.setEnabled(false);
					}
					
					floor.setText(" You Are in "+(((Maze3D) maze).characterX+1)+" Floor");
					solve.setEnabled(true);
					hintButton.setEnabled(true);
			        saveItem.setEnabled(true);
			       maze.forceFocus();
				}
				else {
					JOptionPane.showMessageDialog(null,"Iligal input"+ " :enter level line and column in range: min 3 for each paramater","Error", JOptionPane.WARNING_MESSAGE);
				}
				
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		display.addFilter(SWT.KeyDown,new Listener() {
			
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event arg0) {
				
				if (maze1!=null) {
					
				
					switch (arg0.keyCode) {
					case SWT.ARROW_RIGHT:
						setChanged();
						notifyObservers(new ViewRequestType<Maze3d>("move right",maze1));
						((Maze3D) maze).movedCharacter();
						break;
					case SWT.ARROW_LEFT:
						setChanged();
						notifyObservers(new ViewRequestType<Maze3d>("move left",maze1));
						((Maze3D) maze).movedCharacter();
						break;
					case SWT.ARROW_DOWN:
						setChanged();
						notifyObservers(new ViewRequestType<Maze3d>("move down",maze1));
						((Maze3D) maze).movedCharacter();
						break;
					
					case SWT.ARROW_UP:
						setChanged();
						notifyObservers(new ViewRequestType<Maze3d>("move up",maze1));
						((Maze3D) maze).movedCharacter();
						break;
					
					case SWT.PAGE_UP:
						setChanged();
						notifyObservers(new ViewRequestType<Maze3d>("move level up",maze1));
						((Maze3D) maze).movedCharacter();
						break;
					
					case SWT.PAGE_DOWN:
						setChanged();
						notifyObservers(new ViewRequestType<Maze3d>("move level down",maze1));
						((Maze3D) maze).movedCharacter();
						break;
					case SWT.ESC:
						System.exit(0);
					
					default:
						return;
						
					}
					
					floor.setText(" You Are in "+((((Maze3D) maze).characterX/2)+1)+" Floor");
					
					if(((Maze3D) maze).moveLevelUp()) 
						moveUp.setEnabled(true);
					else 
						moveUp.setEnabled(false);
					
					
					if (((Maze3D) maze).moveLevelDown()) 
						moveDown.setEnabled(true);
					else
						moveDown.setEnabled(false);
					
					if (((Maze3D) maze).reachGoal()){
						JOptionPane.showMessageDialog(null, "You Won!","Congratulations", JOptionPane.INFORMATION_MESSAGE);
							//maze.mazeInit();
						maze.setVisible(false);	
					}
				}
			}
		});
	
		
		solve.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				if (c.getText().equals("BFS")) 
				{
					notifyObservers(new ViewRequestType<BFS>("solve by bfs",new BFS()));
				}
				else if (c.getText().equals("Astar Air")) {
					notifyObservers(new ViewRequestType<Astar>("solve by astar",new Astar(new AirDistance())));
	
				}
				else {
					notifyObservers(new ViewRequestType<Astar>("solve by astar",new Astar(new ManhattanDistance())));					
				}
				
				maze.forceFocus();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	
		
		hintButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {

				
				setChanged();
				if (c.getText().equals("BFS")) 
				{
					notifyObservers(new ViewRequestType<BFS>("hint",new BFS()));
				}
				else if (c.getText().equals("Astar Air")) {
					notifyObservers(new ViewRequestType<Astar>("hint",new Astar(new AirDistance())));
	
				}
				else {
					notifyObservers(new ViewRequestType<Astar>("hint",new Astar(new ManhattanDistance())));					
				}
				
				maze.forceFocus();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		cli.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {

		
				
						setChanged();

						BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
						PrintWriter out =new PrintWriter (System.out);
						HashMap<String,Command> map= new HashMap<String,Command> ();
						notifyObservers(new ViewRequestType<CLI>("cli",new CLI(in,out,map)));
						
						
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		}
		

		
	
				
		
//	public static void main(String[] args) {
//		MazeWindow win=new MazeWindow("maze example", 500, 300);
//		win.run();
//	}




	@Override
	public void displayData(int[][][] data) {
	maze.setMazeData(data);
	maze.redraw();
		
	}

}
