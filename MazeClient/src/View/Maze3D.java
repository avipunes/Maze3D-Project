package View;

import javax.swing.JOptionPane;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

import Entities.CellType;
import Entities.Direction;
import algorithms.mazeGenerators.Position;

public class Maze3D extends MazeDisplayer {

	public int characterX=0;
	public int characterY=0;
	public int characterZ=0;
	private Boolean play=false;
	
	
	private void paintCube(double[] p,double h,PaintEvent e){
        int[] f=new int[p.length];
        for(int k=0;k<f.length;f[k]=(int)Math.round(p[k]),k++);
        
        e.gc.drawPolygon(f);
        
        int[] r=f.clone();
        for(int k=1;k<r.length;r[k]=f[k]-(int)(h),k+=2);
        
        
        int[] b={r[0],r[1],r[2],r[3],f[2],f[3],f[0],f[1]};
        e.gc.drawPolygon(b);
        int[] fr={r[6],r[7],r[4],r[5],f[4],f[5],f[6],f[7]};
        e.gc.drawPolygon(fr);
        
        e.gc.fillPolygon(r);
		
	}
	
	public int getCharacterX() {
		return characterX;
	}

	public int getCharacterY() {
		return characterY;
	}
	
	public int getCharacterZ() {
		return characterZ;
	}
	
	
	public Maze3D(Composite parent, int style) {
		super(parent, style);

		movedCharacter();
		
		final Color white=new Color(null, 255, 255, 255);
		final Color black=new Color(null, 150,150,150);
		setBackground(white);
		addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				   e.gc.setForeground(new Color(null,0,0,0));
				   e.gc.setBackground(new Color(null,0,0,0));
				   
				   int width=getSize().x;
				   int height=getSize().y;
				   
				   int mx=width/2;

				   double w=(double)width/mazeData[0][0].length;
				   double h=(double)height/mazeData[0].length;

				   for(int i=0;i<mazeData[characterX].length;i++){   
					   double w0=0.7*w +0.3*w*i/mazeData[characterX].length;
					   double w1=0.7*w +0.3*w*(i+1)/mazeData[characterX].length;
					   double start=mx-w0*mazeData[characterX][i].length/2;
					   double start1=mx-w1*mazeData[characterX][i].length/2;
				      for(int j=0;j<mazeData[characterX][i].length;j++){
				          double []dpoints={start+j*w0,i*h,start+j*w0+w0,i*h,start1+j*w1+w1,i*h+h,start1+j*w1,i*h+h};
				          double cheight=h/2;
				          if(mazeData[characterX][i][j]==1)
				        	  paintCube(dpoints, cheight,e);
				          else if (mazeData[characterX][i][j]==CellType.SolutionPath.getIndex())
				          		paintPath(dpoints, cheight,e);
				          else if (mazeData[characterX][i][j]==CellType.Goal.getIndex())
				        	  paintGoal(dpoints, cheight,e);
				          
				          if(play && i==characterY && j==characterZ){
							   e.gc.setBackground(new Color(null,200,0,0));
							   e.gc.fillOval((int)Math.round(dpoints[0]), (int)Math.round(dpoints[1]-cheight/2), (int)Math.round((w0+w1)/2), (int)Math.round(h));
							   e.gc.setBackground(new Color(null,255,0,0));
							   e.gc.fillOval((int)Math.round(dpoints[0]+2), (int)Math.round(dpoints[1]-cheight/2+2), (int)Math.round((w0+w1)/2/1.5), (int)Math.round(h/1.5));
							   e.gc.setBackground(new Color(null,0,0,0));				        	  
				          }
				      }
				   }
				
			}

	
		});
	}
	
	public void setPlay(boolean b){play=b;}
	
	private void paintGoal(double[] p, double h, PaintEvent e) {
		
		int[] f=new int[p.length];
		for(int k=0;k<f.length;f[k]=(int)Math.round(p[k]),k++);
		e.gc.setBackground(new Color(null,0,100,230));
	 
        int[] r=f.clone();
        for(int k=1;k<r.length;r[k]=f[k]-(int)(h),k+=2);
        
        e.gc.fillPolygon(f);
	
        e.gc.setBackground(new Color(null,0,0,0));
		
	}
	private void paintPath(double[] p, double h, PaintEvent e) {
       
		int[] f=new int[p.length];
		for(int k=0;k<f.length;f[k]=(int)Math.round(p[k]),k++);
		e.gc.setBackground(new Color(null,0,230,0));
	 
        int[] r=f.clone();
        for(int k=1;k<r.length;r[k]=f[k]-(int)(h),k+=2);
        
        e.gc.fillPolygon(f);
	
        e.gc.setBackground(new Color(null,0,0,0));
		
	}
	
	public void movedCharacter()
	{
		for (int i = 0; i < mazeData.length; i++)
		{
			for (int j = 0; j < mazeData[i].length; j++) 
			{
				for (int k = 0; k < mazeData[i][j].length; k++) 
				{
					if (mazeData[i][j][k]==CellType.Player.getIndex()||mazeData[i][j][k]==CellType.PlayerReachGoal.getIndex())
					{
						characterX=i;
						characterY=j;
						characterZ=k;
					}
				}
			}	
		}
		
	}
	
	private void moveCharacter(int x,int y,int z){
		if(x>=0 && x<mazeData.length && y>=0 && y<mazeData[x].length &&z>=0 && z<mazeData[x][y].length &&mazeData[x][y][z]!=1 ){
			characterX=x;
			characterY=y;
			characterZ=z;
		}
	}
	
	@Override
	public void setCharacterPosition(int level,int row, int col) {
		characterX=level;
		characterY=row;
		characterZ=col;
		
		moveCharacter(level,col,row);
	}

	public void setCharacterStartPosition(){
	
		for (int k = 0; k < mazeData.length; k++)
		{
			for (int i = 0; i < mazeData[k].length; i++)
			{
				for (int j = 0; j < mazeData[k][i].length; j++) 
				{
					if (mazeData[k][i][j]==CellType.Player.getIndex())
					{
						characterX=k;
						characterY=i;
						characterZ=j;
					}
					
				}
			}
	
		}
	}
	
	public boolean moveLevelUp(){
		
		if (characterX+2<mazeData.length && mazeData[characterX+2][characterY][characterZ]!=1)
		{
			return true;
		}
		return false;
	}
	
	public boolean moveLevelDown(){
		
		if (characterX-2>=0 && mazeData[characterX-2][characterY][characterZ]!=1)
		{
			return true;
		}
		return false;
	}
	
	public boolean reachGoal() {
		if (mazeData[characterX][characterY][characterZ]==CellType.PlayerReachGoal.getIndex()) 
		{
			int [][][]m={
			   		{
					      {0, 0, 0},
					      {0, 0, 0},
					      {0, 0, 0},
				   		}
					};
			setMazeData(m);
			characterX=0;
			characterY=0;
			characterZ=0;
			play=false;
			return true;

		}
		return false;
		
	}

	@Override
	public void mazeInit() {
		for (int i = 0; i < mazeData.length; i++) {
			for (int j = 0; j < mazeData[0].length; j++) {
				for (int j2 = 0; j2 < mazeData[0][0].length; j2++) {
					mazeData[i][j][j2]=CellType.Path.ordinal();
				}
			}
		}
		
	}
	
//	public void moveToSolution() {
//		if (characterX+2<=mazeData.length && mazeData[characterX+2][characterY][characterZ]==mazeData[characterX][characterY][characterZ]-1) 
//		{
//			characterX=characterX+2;
//			mazeData[characterX+2][characterY][characterZ]=CellType.Player.getIndex();
//		}
//		
//		if (characterY+1<=mazeData[0].length && mazeData[characterX][characterY+1][characterZ]==mazeData[characterX][characterY][characterZ]-1) 
//		{
//			characterY++;
//			mazeData[characterX][characterY+1][characterZ]=CellType.Player.getIndex();
//		}
//		
//		if (characterZ+1<=mazeData[0][0].length && mazeData[characterX][characterY][characterZ+1]==mazeData[characterX][characterY][characterZ]-1) 
//		{
//			characterZ++;
//			mazeData[characterX][characterY][characterZ+1]=CellType.Player.getIndex();
//		}
//		
//		if (characterX-2>=0 && mazeData[characterX-2][characterY][characterZ]==mazeData[characterX][characterY][characterZ]-1) 
//		{
//			characterX=characterX-2;
//			mazeData[characterX-2][characterY][characterZ]=CellType.Player.getIndex();
//		}
//		
//		if (characterY-1>=0 && mazeData[characterX][characterY-1][characterZ]==mazeData[characterX][characterY][characterZ]-1) 
//		{
//			characterY--;
//			mazeData[characterX][characterY-1][characterZ]=CellType.Player.getIndex();
//		}
//		
//		if (characterZ-1>=0 && mazeData[characterX][characterY][characterZ-1]==mazeData[characterX][characterY][characterZ]-1) 
//		{
//			characterZ--;
//			mazeData[characterX][characterY][characterZ-1]=CellType.Player.getIndex();
//		}
//		
//	}
}
