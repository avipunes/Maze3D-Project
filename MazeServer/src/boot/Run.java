package boot;

import java.io.IOException;
import View.BasicWindow;
import View.ServerView;
import utils.JaxbUtil;

public class Run {
	
	public static void main(String[] args) throws IOException {

		JaxbUtil.readXML();
		BasicWindow ui = new ServerView("Server",450,110);
		ui.run();
	}
}