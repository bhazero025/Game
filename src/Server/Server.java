package Server;
import java.io.IOException;
import java.net.ServerSocket;

import Account.UserAccount;

/**
 * 
 * @author bruno
 * Initialize the server
 *
 */
public class Server
{
	
	private int port;
	private ServerSocket ss;
	
	
	public static void main(String[] args)
	{
		new Server(25801).start();
	}
	
	public Server(int port)
	{
		this.port = port;
	}
	
	public void start()
	{
		try 
		{
			this.ss = new ServerSocket(this.port);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		//start ActionThread
		Thread action = new Thread(new ActionThread());
		action.start();
		
		//World.getWorld().createRandomWorld();
		//DBHandler.createWorldTable();
		//DBHandler.addWorldToDatabase(World.getWorld());
		World.getWorld().loadWorld();
		
		UserAccount u = DBHandler.getLogin("brunoUser", "brunoPass");
		u.debugMe();
		
		Action testAction = new Action(u);
		testAction.startTimer(1, 3);
		
		Action testAction2 = new Action(u);
		testAction2.startTimer(2,3);
		
		
		//TODO CONTINUE

		//TerrainPlayer p = new TerrainPlayer(6, 3);
		//World.getWorld().overwriteTerrain(p);
		/*for (int i = 0; i < World.size; i++)
		{
			for (int j = 0; j < World.size; j++)
			{
				System.out.println(World.getWorld().getTerrain(i, j));
			}
		}*/
		//DBHandler.addWorldToDatabase(World.getWorld());
	}

}


