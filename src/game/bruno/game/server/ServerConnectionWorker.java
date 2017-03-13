package game.bruno.game.server;


import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnectionWorker implements Runnable
{
	
	private ServerSocket ss;
	private Socket cs;

	public ServerConnectionWorker(ServerSocket ss) 
	{
		this.ss = ss;
	}

	/**
	 * @deprecated use ServerConnectionWorker or do not forget to call addServerSocket
	 */
	public ServerConnectionWorker() 
	{
		
	}

	
	public void addServerSocket(ServerSocket ss)
	{
		this.ss = ss;
	}
	
	@Override
	public void run() 
	{
		try
		{
			cs = ss.accept();
			OpenConnectionHandler.getInstance().addPlayerSocket(cs);
			System.out.println("Added " + this.cs);
			
		} catch (Exception e)
		{
			
		}
		
	}

}
