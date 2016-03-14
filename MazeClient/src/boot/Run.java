package boot;

import Model.Maze3dModel;
import Presenter.Presenter;
import View.MazeWindow;
import utils.JaxbUtil;

public class Run {

	public static void main(String[] args) {

		
//		JaxbUtil.writeXML();
		JaxbUtil.readXML();
		
		MazeWindow ui = new MazeWindow("AVI MAZE 3D",1500,800);
		Maze3dModel m = new Maze3dModel();
		Presenter p=new Presenter(ui, m);
		
		ui.addObserver(p);
		m.addObserver(p);	
		
		ui.run();
	}
}
