package Presenter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import Model.Model;
import View.View;
import View.ViewRequestType;

public class Presenter implements Observer {

	View ui;
	Model model;
	
	public Presenter(View ui, Model m) {
		this.ui=ui;
		this.model=m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o,Object args)
	{
		   if(o == ui)
		   {
			   try {
				 model.Request((ViewRequestType<Object>) args);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
		   if(o == model) {
		         ui.displayData( model.getData() );
		   }
 
	}

	
}