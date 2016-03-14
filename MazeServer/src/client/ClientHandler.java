package client;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public interface ClientHandler extends Runnable{

	void init(Socket someClient);

}
