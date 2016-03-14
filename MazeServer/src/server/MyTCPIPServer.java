
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import client.ClientHandler;
import client.MazeClientHandler;
import client.MazeClientHandler;
import utils.JaxbUtil;

public class MyTCPIPServer {

	private int port;
	private ExecutorService Threadpool;
	private ClientHandler mazeClientHandler;
	private ServerSocket server;
	private boolean killServer = false;
	Thread MainServerThread;
	int numOfClients;
	int handled = 0;

	public MyTCPIPServer(int port, ClientHandler mazeClientHandler, int numOfClients) {
		this.port = port;
		this.mazeClientHandler = mazeClientHandler;
		this.numOfClients = numOfClients;
	}

	public void startServer() throws IOException {
		server = new ServerSocket(port);
		server.setSoTimeout(10 * 1000);
		Threadpool = Executors.newFixedThreadPool(numOfClients);
		MainServerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!killServer) {
					try {
						final Socket someClient = server.accept();
						if (someClient != null) {
							mazeClientHandler.init(someClient);
							Threadpool.execute(mazeClientHandler);
						}
					}

					catch (SocketTimeoutException e) {
						System.out.println("NO CLIENT CONECTED");
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				System.out.println("done accapting new client : ");
			}

		});
		MainServerThread.start();
	}

	public void colseServer() throws IOException {
		
		killServer = true;
		System.exit(0);
	}

//	public static void main(String[] args) throws IOException {
//		JaxbUtil.readXML();
//		
//		MyTCPIPServer server = new MyTCPIPServer(12345, new MazeClientHandler() ,JaxbUtil.getProperties().getThreadPoolNumber());
//		server.startServer();
//		BufferedReader in2 = new BufferedReader(new InputStreamReader(System.in));
//		while (!in2.readLine().equals("exit"))
//			;
//		server.colseServer();
//
//	}

}