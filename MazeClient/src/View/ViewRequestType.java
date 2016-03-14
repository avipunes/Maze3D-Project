package View;

import java.io.Serializable;
import java.util.Observable;

public class ViewRequestType<T> extends Observable implements Serializable {
	private String _type;
	private T _args;
	
	public ViewRequestType(String type) {
		this(type, null);
	}
	
	public ViewRequestType(String type, T args) {
		this._type = type;
		this._args = args;	
	}
	public ViewRequestType(T args) {
		this(null,args );
	}
	
	public String GetType(){
		return _type;
	}
	
	public T GetData(){
		return _args;
	}
	
}