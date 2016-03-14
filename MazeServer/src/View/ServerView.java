package View;



import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import org.eclipse.swt.widgets.Label;

import org.eclipse.swt.widgets.Shell;

import client.MazeClientHandler;
import server.MyTCPIPServer;
import utils.JaxbUtil;
import utils.MapToZipUtil;


public class ServerView extends BasicWindow {


	    public ServerView(String title, int width, int height) {
		super(title, width, height);
		// TODO Auto-generated constructor stub
	}

		
	   
	    MyTCPIPServer _server;

	void initWidgets() {
			shell.setLayout(new GridLayout(2,false));
	      	Button startServerButton = new Button(shell,SWT.PUSH);
	    	startServerButton.setText("               Start               ");
	    	startServerButton.setSize(200,200);
	    	startServerButton.setEnabled(true);
			
	      	
	     	Button stopServerButton = new Button(shell,SWT.PUSH);
	     	stopServerButton.setText("               Stop               ");
	     	stopServerButton.setSize(200, 200);
	     	stopServerButton.setEnabled(false);
	     	
	     	startServerButton.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					
					try {
						
						JaxbUtil.readXML();
						MapToZipUtil.readMapSolutions();
						
						_server = new MyTCPIPServer(12345, new MazeClientHandler(),JaxbUtil.getProperties().getThreadPoolNumber());
						_server.startServer();
						startServerButton.setEnabled(false);
						stopServerButton.setEnabled(true);
					
					} catch (IOException | ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	     	
	     	stopServerButton.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					
					try {
						_server.colseServer();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					startServerButton.setEnabled(true);
					stopServerButton.setEnabled(false);
				
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	    
	 	   
	    }
	}