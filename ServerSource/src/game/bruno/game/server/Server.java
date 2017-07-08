package game.bruno.game.server;


import java.io.IOException;
import java.net.ServerSocket;


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
	private Thread connectionWorker;
	public static Thread openConnections;
	
	
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
		System.out.println("Waiting for a connection");
		openConnections = new Thread(OpenConnectionHandler.getInstance());
		openConnections.start();
		try 
		{
			this.ss = new ServerSocket(this.port);
			connectionWorker = new Thread(new ServerConnectionWorker(ss));
			connectionWorker.start();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		//start scanner thread
		new Thread(new ScannerThread()).start();
		
		//start ActionThread
		Thread action = new Thread(new ActionThread());
		action.start();
		
		//World.getWorld().createRandomWorld();
		//DBHandler.createWorldTable();
		//DBHandler.addWorldToDatabase(World.getWorld());
		World.getWorld().loadWorld();
		
		/*UserAccount u = DBHandler.getLogin("brunoUser", "brunoPass");
		u.debugMe();
		
		Action testAction = new Action(u);
		testAction.startTimer(1, 3);
		
		Action testAction2 = new Action(u);
		testAction2.startTimer(2,3);
		
		*/
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


