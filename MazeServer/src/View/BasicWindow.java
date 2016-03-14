package View;




import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;


public abstract class BasicWindow extends Observable implements Runnable{
	
	Display display;
	Shell shell;
	String filename;
 	public BasicWindow(String title, int width,int height) {
 		
 		display=new Display();
 		shell  = new Shell(display);
 		
 		shell.setSize(width,height);
 		shell.setText(title);
 		
 
	}
 	
 	abstract void initWidgets();

	@Override
	public void run() {
		initWidgets();
		shell.open();
		// main event loop
		 while(!shell.isDisposed()){ // while window isn't closed


		    if(!display.readAndDispatch()){ 	// if the queue is empty
		       display.sleep(); 			// sleep until an event occurs 
		    }

		 } // shell is disposed

		 display.dispose(); // dispose OS components
	}
	

}