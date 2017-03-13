package game.bruno.game.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import game.bruno.game.Command;

public class OpenConnectionHandler implements Runnable
{

	private volatile static ArrayList<PlayerSocket> currentUsers = new ArrayList<>();
	
	
	private static OpenConnectionHandler singleton;
	
	public static OpenConnectionHandler getInstance()
	{
		if (singleton == null)
		{
			singleton = new OpenConnectionHandler();
		}
		
		return singleton;
	}
	
	private OpenConnectionHandler() 
	{
		
	}

	
	public void addPlayerSocket(Socket s)
	{
		PlayerSocket buff = new PlayerSocket(s);
		currentUsers.add(buff);
	}
	
	@Override
	public void run() 
	{
		while (true)
		{
			try 
			{
				System.out.println("Users online " + currentUsers.size());
				for (int i = 0; i < currentUsers.size(); i++)
				{
					
					if (currentUsers.get(i).isReady() != 0)
					{
						System.out.println("Reading command from " + currentUsers.get(i));
						ObjectInputStream ois = new ObjectInputStream(currentUsers.get(i).getSocket().getInputStream());
						Command buffer = (Command) ois.readObject();
						System.out.println(buffer.getCommand());
					}
				}
				
				
				Thread.sleep(1000);
			}
			catch (Exception e)
			{
				
				e.printStackTrace();
			}
		}
		
	}

}
